package au.org.intersect.bdcp

import eu.medsea.mimeutil.detector.MagicMimeMimeDetector
import grails.plugins.springsecurity.Secured

import java.io.File

class StudyDeviceController {

    static allowedMethods = [update: "POST", delete: "POST"]
	
	def roleCheckService

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def list = {
        def studyInstance = Study.get(params.studyId)
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
        def deviceGroupList = DeviceGroup.list()
        def sortedDeviceGroupList = deviceGroupList.sort {x,y -> y.groupingName <=> x.groupingName}
        def deviceGroupsMapping = sortedDeviceGroupList.collect {deviceGroup ->
            def devices = studyInstance.studyDevices.findAll { it.device.deviceGroup.id == deviceGroup.id }
            devices = devices.collect { it.device }
            [deviceGroup:deviceGroup, devices:devices.sort {x,y -> x.name <=> y.name}]
        }
        deviceGroupsMapping = deviceGroupsMapping.findAll { it.devices.size() > 0 }
        [deviceGroupsMapping: deviceGroupsMapping, studyInstance: studyInstance]
    }
	
	/**
	* Display project only to research owner
	* @param _projectInstance
	*/
   private void redirectNonAuthorizedResearcherAccessStudy(Study _studyInstance)
   {
	   def userStore = UserStore.findByUsername(principal.username)
	   def studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(_studyInstance,userStore)

	   if(!_studyInstance.project.owner.username.equals(principal.username) && !studyCollaborator){
		   redirect controller:'login', action: 'denied'
	   }
   }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def create = {
        def studyDeviceInstance = new StudyDevice()
        def studyInstance = Study.findById(params.studyId)
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
        studyDeviceInstance.properties = params
        def deviceGroupList = DeviceGroup.list()
        def sortedDeviceGroupList = deviceGroupList.sort {x,y -> y.groupingName <=> x.groupingName}
        return [studyDeviceInstance: studyDeviceInstance, studyInstance: studyInstance, deviceGroupList: sortedDeviceGroupList]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def save = {
        def studyDeviceInstance = new StudyDevice(params)
        if (studyDeviceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'studyDevice.label', default: 'Device'), studyDeviceInstance.device])}"
            redirect(mapping:"studyDeviceDetails",controller: "studyDevice", action: "list", id: studyDeviceInstance.id, params:[studyId: params.studyId])
        }
        else {
            render(view: "create", model: [studyDeviceInstance: studyDeviceInstance])
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def show = {
        def studyDeviceInstance = StudyDevice.get(params.id)
        if (!studyDeviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
            redirect(action: "list")
        }
        else {
            [studyDeviceInstance: studyDeviceInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def edit = {
        def studyDeviceInstance = StudyDevice.get(params.id)
        if (!studyDeviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [studyDeviceInstance: studyDeviceInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def update = {
        def studyDeviceInstance = StudyDevice.get(params.id)
        if (studyDeviceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (studyDeviceInstance.version > version) {
                    
                    studyDeviceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'studyDevice.label', default: 'StudyDevice')] as Object[], "Another user has updated this StudyDevice while you were editing")
                    render(view: "edit", model: [studyDeviceInstance: studyDeviceInstance])
                    return
                }
            }
            studyDeviceInstance.properties = params
            if (!studyDeviceInstance.hasErrors() && studyDeviceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), studyDeviceInstance.id])}"
                redirect(action: "show", id: studyDeviceInstance.id)
            }
            else {
                render(view: "edit", model: [studyDeviceInstance: studyDeviceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def delete = {
        def studyDeviceInstance = StudyDevice.get(params.id)
        if (studyDeviceInstance) {
            try {
                studyDeviceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'studyDevice.label', default: 'StudyDevice'), params.id])}"
            redirect(action: "list")
        }
    }
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def listStudyDeviceManuals = 
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		def deviceInstance = Device.get(params.deviceId)
		def deviceManualFormInstanceList = []
		def f = new File( getRealPath() + params.deviceId.toString() )
		
		log.info("FILE PATH:" + f.getAbsolutePath())
		if( f.exists() )
		{
			f.eachFile()
			{ file->
				if( !file.isDirectory() )
				{
					def deviceManualForm = DeviceManualForm.findWhere(storedFileName: file.getName(), device: deviceInstance)
					if(deviceManualForm != null)
					{
						deviceManualFormInstanceList.add(deviceManualForm)
					}
				}
			}
		}
		
		[studyId: params.studyId, deviceId: params.deviceId, deviceInstance: deviceInstance, deviceManualFormInstanceList: deviceManualFormInstanceList]
    }
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def downloadFile =
	{
		cache false
		
		def studyInstance = Study.get(params.studyId)
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		def fileDoc = new File( getRealPath() +  File.separatorChar + params.deviceId.toString() + File.separatorChar + deviceManualFormInstance.fileName)
		
		if(fileDoc.exists())
		{
			def fileName = deviceManualFormInstance.fileName

			if (!deviceManualFormInstance.contentType)
			{
				response.setContentType(getMimeType(fileDoc))
			}
			else
			{
				response.setContentType deviceManualFormInstance.contentType
			}
						
			response.setHeader "Content-disposition", "attachment; filename=\"${fileName}\"" ;
			response.outputStream << fileDoc.newInputStream();
			response.outputStream.flush();

			return false
		}
		else
		{
			flash.message = "Device Manual Form ${deviceManualFormInstance.formName} could not be found"
			redirect url: createLink(controller: 'studyDevice', action:'listStudyDeviceManuals',
				mapping:'studyDeviceManuals', params:[studyId: params.studyId, deviceId: params.deviceId, id: params.id])
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def deleteDeviceManual =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		
		if (deviceManualFormInstance)
		{
			try
			{
				def filename = deviceManualFormInstance.fileName
				deviceManualFormInstance.delete(flush: true)
				def file =  new File( getRealPath() + File.separatorChar + params.deviceId + File.separatorChar + filename)

				if (file.exists())
				{
					file.delete()
				}
				flash.message = "Device Manual Form ${deviceManualFormInstance.formName} deleted"
				redirect url: createLink(controller: 'deviceManualForm', action:'listStudyDeviceManuals',
					mapping:'studyDeviceManuals', params:[studyId: params.studyId, deviceId: params.deviceId, id: params.id])
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "Device Manual Form ${deviceManualFormInstance.formName} could not be deleted"
				redirect url: createLink(controller: 'deviceManualForm', action:'listStudyDeviceManuals',
					mapping:'studyDeviceManuals', params:[studyId: params.studyId, deviceId: params.deviceId, id: params.id])
			}
		}
		else
		{
			flash.message = "Device Manual Form ${deviceManualFormInstance.formName} could not be found"
			redirect url: createLink(controller: 'deviceManualForm', action:'listStudyDeviceManuals',
				mapping:'studyDeviceManuals', params:[studyId: params.studyId, deviceId: params.deviceId, id: params.id])
		}
	}
	
	private String getRealPath()
	{
		return ( grailsApplication.config.forms.deviceManuals.location.toString())
	}
	
	private String getMimeType(File file)
	{
		// use mime magic
		MagicMimeMimeDetector detector = new MagicMimeMimeDetector();
		Collection mimeTypes = detector.getMimeTypesFile(file);

		if (mimeTypes) return mimeTypes.iterator().getAt(0).toString()

		return "application/octet-stream"
	}
	
}
