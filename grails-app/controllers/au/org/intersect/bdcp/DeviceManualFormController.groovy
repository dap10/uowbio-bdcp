package au.org.intersect.bdcp

import java.io.File

import eu.medsea.mimeutil.detector.MagicMimeMimeDetector
import grails.plugins.springsecurity.Secured



/**
 * This class has been adapted from the ParticipantsFormController class. Essentially it is used to accept and process requests that save and upload device manuals from a 
 * form with multiple entries possible for a device manual. Complex validation takes place where duplicates on the form and the form relative to the file system are reported
 * to the user as an unsuccessful save.
 * 
 * @author kherrman
 */
class DeviceManualFormController
{
	static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]

	private DeviceManualForm extractDeviceManualForm(i)
	{
        
		def dmfc = new DeviceManualFormCommand()
		bindData( dmfc, params )
		DeviceManualForm deviceManualFormInstance = dmfc.forms[i];

		return deviceManualFormInstance
	}

	private String normalizeValue(value)
	{
		value = value.replaceAll(/[^A-Za-z0-9-_\s]/, '')
		return value
	}
	
	/**
	 * Checks if all of the entries in the device manual form are valid for a DeviceManualForm domain object and that there are no conflicts with same manual names on the 
	 * form or same file name on the form or the file system itself, for a given device id.
	 * @param deviceManualForms the deviceManualForm objects that correspond to the form entries.
	 * @param usedFormRowNumbers the row indexes that have an entry on the form.
	 * @return true if valid form entries for manuals.
	 */
	private boolean validateDeviceManualForms(deviceManualForms, usedFormRowNumbers)
	{
		def allValid = true
		
		int deviceManualFormIndex = 0
		for (rowNumber in usedFormRowNumbers)
		{			
			if (request.getFile("form.${rowNumber}")?.isEmpty() && (session["fileName[${rowNumber}]"] == null))
			{
				deviceManualForms[deviceManualFormIndex].fileName = null
			}

			if (!deviceManualForms[deviceManualFormIndex]?.validate())
			{
				allValid = false
			}
            else if(checkForReplication(deviceManualForms, deviceManualFormIndex, "formName"))
            {
                deviceManualForms[deviceManualFormIndex].errors.rejectValue('formName', 'deviceManualForm.formName.uniqueIgnoreCase.invalid')
                allValid = false
            }
			else if(checkForReplication(deviceManualForms, deviceManualFormIndex, "fileName"))
			{
				deviceManualForms[deviceManualFormIndex].errors.rejectValue('fileName', 'deviceManualForm.fileName.uniqueIgnoreCase.invalid')
				allValid = false
			}
			else if(deviceManualForms[deviceManualFormIndex]?.fileName != null && checkNoConflictWithExistingFileName(deviceManualForms[deviceManualFormIndex]?.fileName)) // check that file names provided dont match any existing files
			{
				deviceManualForms[deviceManualFormIndex].errors.rejectValue('fileName', 'deviceManualForm.fileName.uniqueIgnoreCase.invalid')
				allValid = false
			}
			deviceManualFormIndex++
		}
		
		return allValid
	}
	
	/**
	 * Checks that there is no conflict of a file name that is trying to be loaded up by the form and the current files on the file system for a 
	 * given device. The device manuals are grouped into directories numbered by the device id they belong to on the database.
	 * @param _filename The file name checking there is a conflict with.
	 * @return boolean indicating true there is a conflict with the file name supplied and the files on the file system.
	 */
	private boolean checkNoConflictWithExistingFileName(_filename)
	{
		boolean conflict = false
		
		def deviceInstance = Device.get(params.deviceId)
		def f = new File( getRealPath() + params.deviceId.toString() )
		if( f.exists() )
		{
			f.eachFile()
			{ file->
				if( !file.isDirectory() )
				{
					def deviceManualForm = DeviceManualForm.findWhere(storedFileName: file.name, device: deviceInstance)
					if(deviceManualForm && _filename && deviceManualForm?.storedFileName?.equalsIgnoreCase(_filename))
					{
						conflict = true
					}
				}
			}
		}
		
		return conflict
	}
	
	/**
	 * Checks for either the manual name or the form name conflicts on the actual form values submitted
	 * @param _deviceManualForms The set of device manuals
	 * @param index the device manual entry index in the collection
	 * @param fieldname the fieldname that is being checked for replication
	 * @return if replicated field returns true.
	 */
	private boolean checkForReplication(_deviceManualForms, index, fieldname)
	{
		boolean replication = false
		
		if(fieldname?.equalsIgnoreCase("fileName"))
		{
			replication = ((_deviceManualForms.findAll { it?.fileName.equalsIgnoreCase(_deviceManualForms[index]?.fileName) }).size() > 1)
		}
		else if((fieldname?.equalsIgnoreCase("formName")))
		{
			replication = ((_deviceManualForms.findAll { it?.formName.equalsIgnoreCase(_deviceManualForms[index]?.formName) }).size() > 1)
		}
			
		return replication
	}

	/**
	 * Generates a List of integers representing the row number of the manual on the form that has an entry to download a manual.
	 * @return List of integers representing the entries on the form that there is a manual to download.
	 */
	private deviceManualFormsToLoad()
	{
		def deviceManualFormCommand = new DeviceManualFormCommand()
		bindData( deviceManualFormCommand, params )
		List<Integer> usedFields = new ArrayList<Integer>();
		deviceManualFormCommand.forms.eachWithIndex()
		{ obj, i ->

			if ((obj?.formName.size() > 0) || obj?.fileName.size() >0)
			{
				usedFields.add (i)
			}
		}
		
		return usedFields
	}

	/**
	 * Save state between handling requests if some form entries are invalid.
	 */
	private populateSessionValues()
	{
		for (i in deviceManualFormsToLoad())
		{
			if (!request.getFile("form.${i}")?.isEmpty())
			{
				session["fileName[${i}]"] = request.getFile("form.${i}")
			}
		}
	}

    private clearSessionValues()
    {
        session.fileName = null
    }
    
	private String getMimeType(File file)
	{
		// use mime magic
		MagicMimeMimeDetector detector = new MagicMimeMimeDetector();
		Collection mimeTypes = detector.getMimeTypesFile(file);

		if (mimeTypes) return mimeTypes.iterator().getAt(0).toString()

		return "application/octet-stream"
	}

	private String getRealPath()
	{
		return ( grailsApplication.config.forms.deviceManuals.location.toString())
	}

	private String getFileExtension(fileName)
	{
		def fileExtension
		if (fileName != null)
		{
			int mid= fileName.lastIndexOf(".");

			if (!(mid < 0))
			{
				fileExtension=fileName.substring(mid+1,fileName.length());
			}
		}
		return fileExtension
	}
	
	private saveFile(file, deviceManualFormInstance)
	{
		if ((file!= null) && (!file.isEmpty()))
		{
			def fileExtension = getFileExtension(file?.getOriginalFilename())
			def fileName = file?.getOriginalFilename()

			file.transferTo( new File( getRealPath() + params.deviceId.toString() +File.separatorChar + fileName) )
			deviceManualFormInstance.form = deviceManualFormInstance.formName
			deviceManualFormInstance.contentType = file.contentType
			deviceManualFormInstance.fileExtension = fileExtension
			deviceManualFormInstance.storedFileName = fileName
            deviceManualFormInstance.save(flush: true)
            
		}
		else
		{
			deviceManualFormInstance.delete(flush: true)
		}
	}

    private renderUploadErrorMsg(deviceManualForms)
    {
        def deviceManualFormInstanceList = []
        def deviceInstance = Device.get(params.deviceId)
        def f = new File( getRealPath() + params.deviceId.toString() )
        if( f.exists() )
        {
            f.eachFile()
            { file->
                if( !file.isDirectory() )
                {
                    def deviceManualForm = DeviceManualForm.findWhere(storedFileName: file.name, device: deviceInstance)
                    if(deviceManualForm != null)
                    {
                        deviceManualFormInstanceList.add(deviceManualForm)
                    }
                }
            }
        }
        render(view: "list", model: [deviceManualForms: deviceManualForms,deviceManualFormInstanceList: deviceManualFormInstanceList, deviceManualFormInstanceTotal: deviceManualFormInstanceList.size(), deviceInstance: Device.get(params.deviceId), forms:deviceManualForms, fileName: params.fileName, deviceId: params.deviceId ])
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def upload =
	{
		cache false
		def deviceManualForms = []
		def allValid = true
		
		// find row number of device manual rows with entries
		List<Integer> usedFormRowNumbers = deviceManualFormsToLoad()
		def deviceManualFormIndex = 0
        for (rowNumber in usedFormRowNumbers)
		{
			deviceManualForms[deviceManualFormIndex++] = new DeviceManualForm(params["forms["+rowNumber+"]"])
        }

		// check if device manual entries not valid - handleSaveDeviceManualForm()
		if (!validateDeviceManualForms(deviceManualForms, usedFormRowNumbers))
		{
            populateSessionValues()
            renderUploadErrorMsg(deviceManualForms);
		}
		else // if the form entries are valid then save entries to the database and the file system
		{
			deviceManualFormIndex = 0
			for (rowNumber in usedFormRowNumbers)
			{
                if (deviceManualForms[deviceManualFormIndex].save(flush: true))
				{
					// create directory using Java, java.io.File
					new File( getRealPath() + params.deviceId.toString()).mkdirs()
					// this is a Spring org.springframework.web.multipart.commons.CommonsMultipartFile provided by Grails framework
					def file = request.getFile("form.${rowNumber}")
				
					if (file?.isEmpty() && !(session["fileName[${rowNumber}]"] == null))
					{
						file = session["fileName[${rowNumber}]"]
					}
					// save state in case attempted save is unsuccessful
                    populateSessionValues()
					saveFile(file, deviceManualForms[deviceManualFormIndex])
				}
                else
                {
                    allValid = false    
                }
                deviceManualFormIndex++
			}
			
			deviceManualFormIndex = 0
			// remove database entries that were saved if ANY of the row entries failed - handleCleanup()
            if (allValid == false)
            {
                for (rowNumber in usedFormRowNumbers)
                {
                    deviceManualForms[deviceManualFormIndex].delete(flush:true)
                }
                
                renderUploadErrorMsg(deviceManualForms);
                return
            }
            else // remove state as save was successful
            {
                clearSessionValues()
            }
			
            reportSuccessfulUpdate(usedFormRowNumbers)

			redirect url: createLink(controller: 'deviceManualForm', action:'list', mapping:'deviceManualFormDetails', params:[deviceId: params.deviceId, deviceGroupId: params.deviceGroupId])
		}
	}
	
	private reportSuccessfulUpdate(_usedFormRowNumbers)
	{
		switch (_usedFormRowNumbers.size())
		{
			case 0: flash.error = "No manuals selected to upload"
			break

			case 1: flash.message = "${_usedFormRowNumbers.size()} Device manual uploaded"
			break

			case 2..10: flash.message = "${_usedFormRowNumbers.size()} Device manuals uploaded"
			break
			default:
			break
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def downloadFile =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		def fileDoc = new File( getRealPath() + params.deviceId.toString() + File.separatorChar + deviceManualFormInstance.fileName)
		
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
			flash.message = "Device Manual ${deviceManualFormInstance.formName} could not be found"
			redirect url: createLink(controller: 'deviceManualForm', action:'list',
				mapping:'deviceManualFormDetails', params:[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: params.id])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		cache false
		def deviceInstance = Device.get(params.deviceId)
		def deviceManualFormInstanceList = []
		def deviceManualForms = []
		def f = new File( getRealPath() + params.deviceId.toString() )
		
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
		[deviceManualFormInstanceList: deviceManualFormInstanceList, deviceManualFormInstanceTotal: deviceManualFormInstanceList.size(), deviceInstance: deviceInstance, deviceManualForms: deviceManualForms, forms: deviceManualForms, deviceManualFormId: params.id]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def create =
	{
		cache false
		def deviceManualFormInstance = new DeviceManualForm()
		deviceManualFormInstance.properties = params
		def deviceManualForms = []
		return [deviceManualFormInstance: deviceManualFormInstance, deviceManualForms: deviceManualForms]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def save =
	{
		cache false
		def deviceManualFormInstance = new DeviceManualForm(params)
		if (deviceManualFormInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), deviceManualFormInstance.id])}"
			redirect(action: "show", id: deviceManualFormInstance.id)
		}
		else
		{
			render(view: "create", model: [deviceManualFormInstance: deviceManualFormInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def show =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		if (!deviceManualFormInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			[deviceManualFormInstance: deviceManualFormInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def edit =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		if (!deviceManualFormInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [deviceManualFormInstance: deviceManualFormInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def update =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		if (deviceManualFormInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (deviceManualFormInstance.version > version)
				{

					deviceManualFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'deviceManualForm.label', default: 'DeviceManualForm')]
					as Object[], "Another user has updated this DeviceManualForm while you were editing")
					render(view: "edit", model: [deviceManualFormInstance: deviceManualFormInstance])
					return
				}
			}
			deviceManualFormInstance.properties = params
			if (!deviceManualFormInstance.hasErrors() && deviceManualFormInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), deviceManualFormInstance.id])}"
				redirect(action: "show", id: deviceManualFormInstance.id)
			}
			else
			{
				render(view: "edit", model: [deviceManualFormInstance: deviceManualFormInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceManualForm.label', default: 'DeviceManualForm'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def delete =
	{
		cache false
		def deviceManualFormInstance = DeviceManualForm.get(params.id)
		
		if (deviceManualFormInstance)
		{
			try
			{
				def filename = deviceManualFormInstance.fileName 
				deviceManualFormInstance.delete(flush: true)
				def file =  new File( getRealPath() + params.deviceId + File.separatorChar + filename)

				if (file.exists())
				{
					file.delete()
				}
				flash.message = "Device Manual ${deviceManualFormInstance.formName} deleted"
				redirect url: createLink(controller: 'deviceManualForm', action:'list',
					mapping:'deviceManualFormDetails', params:[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: params.id])
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "Device Manual ${deviceManualFormInstance.formName} could not be deleted"
				redirect url: createLink(controller: 'deviceManualForm', action:'list',
					mapping:'deviceManualFormDetails', params:[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: params.id])
			}
		}
		else
		{
			flash.message = "Device Manual ${deviceManualFormInstance.formName} could not be found"
			redirect url: createLink(controller: 'deviceManualForm', action:'list',
				mapping:'deviceManualFormDetails', params:[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: params.id])
		}
	}
}
class DeviceManualFormCommand
{
	DeviceManualForm[] forms = [
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm(),
		new DeviceManualForm()] as DeviceManualForm[]
}