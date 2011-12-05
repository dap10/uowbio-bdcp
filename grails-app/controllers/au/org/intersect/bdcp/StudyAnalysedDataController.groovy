package au.org.intersect.bdcp

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.io.File;
import java.util.Set
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class StudyAnalysedDataController
{
	
	def fileService
	
	def roleCheckService
	
	def grailsApplication
	
//	def pattern = Pattern.compile("^(*)/(.*)\$")
	
	def createContext()
	{
		return fileService.createContext( "analysed")
	}

	def securedCommon = { onErrors, block ->
		cache false
		
		def studyInstance = Study.get(Long.parseLong(params.studyId))
		if (studyInstance == null) {
			onErrors.badStudyId()
			return
		}
		
		def canDo = roleCheckService.checkUserRole('ROLE_LAB_MANAGER');
		canDo = canDo || roleCheckService.checkSameUser(studyInstance.project.owner.username) || roleCheckService.isCollaborator(studyInstance)
		if (!canDo) {
			onErrors.unauthorised()
			return
		}
		def context = createContext()
		ensureFilesRoot(context, studyInstance)
		block(studyInstance, context)
	}
	
	def securedBasic = securedCommon.curry([
		'badStudyId' : {
			flash.message = message(code:'study.analysed.invalidId')
			redirect controller:'login', action: 'invalid'
		},
		'unauthorised' : {
			redirect controller:'login', action: 'denied'
		}
		])
	
	def securedJson = securedCommon.curry([
		'badStudyId' : {
			def resp = ['error': 'bad parameters']
			render resp as JSON
		},
		'unauthorised' : {
			def resp = ['error': 'unauthorised']
			render resp as JSON
		}
		]) 
		
	// common security validation and context initialization
	def secured = { block ->
		securedBasic { studyInstance, context ->
			def studyAnalysedData = StudyAnalysedData.findByStudyAndFolder(studyInstance, params.folder)
			def studyAnalysedDataFields = []
			def resultFields = ResultsDetailsField.findAll()
			if (studyAnalysedData == null) {
				studyAnalysedData = new StudyAnalysedData(study:studyInstance, folder:params.folder)
				resultFields.each {  fieldDef ->
					def domObj = new StudyAnalysedDataField(studyAnalysedData: studyAnalysedData, resultsDetailsField: fieldDef)
					studyAnalysedDataFields.add(domObj)
				}
			} else {
				resultFields.each {  fieldDef ->
					def domObj = studyAnalysedData.studyAnalysedDataFields.find { StudyAnalysedDataField sdf -> sdf.resultsDetailsField.equals(fieldDef) }
					if (domObj == null) {
						// newly created field
					    domObj = new StudyAnalysedDataField(studyAnalysedData: studyAnalysedData, resultsDetailsField: fieldDef)
					}
					studyAnalysedDataFields.add(domObj)
				}
			}
			studyAnalysedDataFields.sort {x,y -> x.resultsDetailsField.dateCreated <=> y.resultsDetailsField.dateCreated}		
			block(studyInstance, studyAnalysedData, studyAnalysedDataFields, context)
	    }
	}
	

	def index =
	{
		redirect(action: "list", params: params)
	}


	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		securedBasic { study, context ->
			def studyAnalysedFolders = StudyAnalysedData.findAllByStudy(study)
			render(view:'list', model:[studyInstance: study, folders:studyAnalysedFolders])
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def downloadFiles =
	{
		
		cache false
		
		def context = createContext()
		def study = Study.findById(params.studyId)
		def files = params.list('files')
		def zipName = study.studyTitle + ".zip"
		
		response.setContentType "application/zip"
		response.setHeader "Content-Disposition", "attachment; filename=\"" + zipName + "\""
		response.setHeader "Content-Description", "File download for BDCP"
		response.setHeader "Content-Transfer-Encoding", "binary"

		def zipOs = new ZipOutputStream(response.outputStream)
		def added = new HashSet()
		zipOs.setComment "Created with BDCP web application"
		files.each { String file ->
				addFileToZip(study, context, zipOs, file, added)
			}
		zipOs.close()
		response.flushBuffer()
		
		return true
		
	}
	
	private void addFileToZip(Study study, Object context, ZipOutputStream zipOs, String file, Set added)
	{
		def thePath = file
		File theFileOrDir = fileService.getFileReference( grailsApplication.config.files.analysed.location, thePath)
		def lastMod = theFileOrDir.lastModified()
		if (!theFileOrDir.isDirectory() && !added.contains(theFileOrDir) )
		{
			def zipEntry = new ZipEntry(thePath)
			long fileSize = theFileOrDir.length()
			zipEntry.setTime(lastMod)
			zipEntry.setSize(fileSize)
			zipOs.putNextEntry(zipEntry)
			zipOs << new FileInputStream(theFileOrDir)
			zipOs.closeEntry()
			added.add(theFileOrDir)
		}
		else if (theFileOrDir.isDirectory() && !added.contains(theFileOrDir) )
		{
			addDirectoryToZip (theFileOrDir, study, context, zipOs, file, added)
		}
	}
	
	private void addDirectoryToZip(File directory, Study study, Object context, ZipOutputStream zipOs, String file, Set added)
	{
		directory.eachFile { it -> 
			if( !added.contains(it.toString()) )
			{
				if(it.isDirectory()) added.add(it.toString())
				addFileToZip( study, context, zipOs, 
					it.toString().substring (new Integer(it.toString().indexOf("analysed")).intValue() + (new Integer("analysed".size()).intValue() + 1)), 
					added)
			}
		}
	}
	
	def listFolder =
	{
		securedJson { studyInstance, context ->
			def studyAnalysedData = StudyAnalysedData.findById(params.id)
			def name = params.folderPath
			def folderPath = studyInstance.id + "/" + studyAnalysedData.folder

			if (''.equals(name)) {
				def resp = ['data':studyAnalysedData.folder,'state':'closed','attr':['rel':'root'],'metadata':['folderPath':folderPath]]
				render resp as JSON
				return
			}

			def file = fileService.getFileReference( grailsApplication.config.files.analysed.location, name)
			if (file.isDirectory()) {
			   def folders = file.listFiles().collect { f ->
				   f.isDirectory() ? ['data':f.getName(),'icon':'folder','state':'closed','attr':['rel':'folder'],
					   'metadata':['folderPath':('/'.equals(name) ? '' : name)+'/' +f.getName()]] : ['data':f.getName(),'attr':['rel':'file', 'folderPath':('/'.equals(name) ? '' : name)+"/"+f.getName()]]
			   }
			   render folders as JSON
			} else {
			   def folders = ['data':name,'attr':['rel':'file', 'folderPath':('/'.equals(name) ? '' : name)+"/"+file.getName()]]
			   render folders as JSON
			}
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def createFolder =
	{
		securedBasic { study, context ->
			[studyInstance: study, errors:false, folderName:new FolderCommand(folder:'')]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def doCreateFolder =
	{	FolderCommand folderV ->
		securedBasic { study, context ->
			folderV.folder = folderV.folder?.trim()
			if (folderV.hasErrors()) {
				render(view:'createFolder',model:[studyInstance: study, errors:true, folderName:folderV])
				return
			}
			def ok = true
			StudyAnalysedData.withTransaction { tx ->
				def safOld = StudyAnalysedData.findByStudyAndFolder(study, folderV.folder)
				if (safOld == null) {
					def saf = new StudyAnalysedData(study:study, folder:folderV.folder)
					ok = saf.save(flush:true)
				} else {
					ok = true
				}
				if (ok && fileService.checkIfDirectoryExists( grailsApplication.config.files.analysed.location, folderV.folder, rootPath(study))) {
					flash.message = "Folder already exists"
				} else {
				   ok = ok && fileService.createDirectory( grailsApplication.config.files.analysed.location, folderV.folder, rootPath(study))
				}
				if (!ok) {
					tx.setRollbackOnly()
				}
				if (!ok) {
					flash.error = "Error saving data to DB"
					render(view:'createFolder',model:[studyInstance: study, errors:true, folderName:folderV])
					return
				}
			}
			redirect(params:[studyId:study.id, action:'list'])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def editData =
	{
		secured { study, studyAnalysedData, studyAnalysedDataFields, context ->
			def nextAction = studyAnalysedData.id == null ? 'update' : 'save'
			def mode = ['view', 'editOnly'].contains(params.mode) ? params.mode : 'edit'
			render(view:'viewOrEditData', model:[studyInstance: study, studyAnalysedData: studyAnalysedData, studyAnalysedDataFields:studyAnalysedDataFields, nextAction:nextAction, mode:mode])
		}
	}
	
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
    def updateData = {
		
		secured {  study, studyAnalysedData, studyAnalysedDataFields, context ->
			def fieldsSize = params["fieldsSize"] as Integer
			def mode = ['view', 'editOnly'].contains(params.mode) ? params.mode : 'edit'
			if (studyAnalysedDataFields.size() != fieldsSize) {
				flash.message = 'Number of fields in request does not match database. Editing cancelled'
				redirect url:createLink(mapping:'studyAnalysedData', params:[studyId:studyInstance.id, folder:studyAnalysedData.folder, action:'editData', mode:mode])
				return				
			}
			def ok = true
			for (i in 0..fieldsSize-1) {
				def saf = studyAnalysedDataFields[i]
				def safParams = params["studyAnalysedDataFields["+i+"]"]
				if (saf.id != null) {
					if (saf.version > safParams.version) {
						saf.errors.reject('updated','Someone updated this in the dabase')
						ok = false
						continue
					}
				}
				saf.properties = safParams
				ok = saf.validate() && ok  // note: we want to validate always, so Ok should be last
			}
			if (!ok) {
				def nextAction = studyAnalysedData.id == null ? 'create' : 'update'
				render(view:'viewOrEditData', model:[studyInstance: study, studyAnalysedData: studyAnalysedData, studyAnalysedDataFields:studyAnalysedDataFields, nextAction:nextAction, mode:mode])
				return
			}
			def allOk = true
			StudyAnalysedData.withTransaction { dbt ->
				if (studyAnalysedData.id == null) {
					allOk = studyAnalysedData.save(flush:true) && allOk
				}
				studyAnalysedDataFields.each { allOk = it.save(flush:true) && allOk }
				if (!allOk) {
					dbt.setRollbackOnly()
				}
			}
			if (!allOk) {
				def nextAction = studyAnalysedData.id == null ? 'create' : 'update'
				flash.error = message(code: 'study.analysed.'+nextAction+'.db.error')
				render(view:'viewOrEditData', model:[studyInstance: study, studyAnalysedData: studyAnalysedData, studyAnalysedDataFields:studyAnalysedDataFields, nextAction:nextAction, mode:mode])
				return
			}
			if ('editOnly'.equals(mode)) {
				redirect url:createLink(mapping: "studyAnalysedData", action:"list", params:["studyId":study.id] )
			} else {
            	redirect url:createLink(mapping: "studyAnalysedData", action:"upload", params:["studyId":study.id, "folder":studyAnalysedData.folder] )
			}
		}
        
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def upload =
	{
		secured { study, studyAnalysedData, studyAnalysedDataFields, context ->
			if (params.done != null || params.cancel != null) {
				flash.message = params.done != null ? message(code:'study.analysed.upload.done') : message(code:'study.analysed.upload.cancel') 
				redirect url:createLink(mapping: "studyAnalysedData", action:"list", params:["studyId":study.id] )
			}
			[studyInstance: study, errors:false, folderName:new FolderCommand(folder:params.folder)]
		}
	}
	
	def uploadFiles =
	{	
		cache false
		def studyInstance = Study.get(Long.parseLong(params.studyId))
		if (studyInstance == null) {
			flash.message = message(code:'study.analysed.invalidId')
			redirect controller:'login', action: 'invalid'
			return
		}
		def context = createContext()
		ensureFilesRoot(context, studyInstance)
		def dirstruct = params.dirStruct
		def upload_root = rootPath(studyInstance) + params.destDir
		dirstruct = JSON.parse(dirstruct)
        def success = (fileService.createAllFolders( grailsApplication.config.files.analysed.location, dirstruct, upload_root) == true) ? true : false
        success = success && (fileService.createAllFiles( dirstruct, upload_root, params) == true)
		success = success && (fileService.moveDirectoryFromTmp( grailsApplication.config.files.analysed.location, upload_root, upload_root) == true)
		if (success)
		{
			render "Successfully Uploaded Files!"
		}
		else
		{
			response.sendError 500
		}
	}

   private void ensureFilesRoot(context, studyInstance)
   {
	   def dir = fileService.getFileReference( grailsApplication.config.files.analysed.location, rootPath(studyInstance))
	   if (!dir.exists())
	   {
		   dir.mkdirs()
	   }
   }
   
   private String rootPath(studyInstance)
   {
	   return "${studyInstance.id}/";
   }
   
   
}

class FolderCommand {
	String folder
	
	static constraints =
	{
		folder(nullable:false, blank:false, size:1..255, validFilename:true)
	}
}
