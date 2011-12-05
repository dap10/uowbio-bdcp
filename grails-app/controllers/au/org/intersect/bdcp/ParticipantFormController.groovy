package au.org.intersect.bdcp

import java.io.File

import eu.medsea.mimeutil.detector.MagicMimeMimeDetector
import grails.plugins.springsecurity.Secured




class ParticipantFormController
{
	static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
	
	def roleCheckService

	private ParticipantForm extractParticipantForm(i)
	{
        
		def pfc = new ParticipantFormCommand()
		bindData( pfc, params )
		ParticipantForm participantFormInstance = pfc.forms[i];

		return participantFormInstance
	}

	private String normalizeValue(value)
	{
		value = value.replaceAll(/[^A-Za-z0-9-_\s]/, '')
		return value
	}
	
	/**
	 * Checks if all of the entries in the participant form are valid for a participantForm domain object and that there are no conflicts with same manual names on the 
	 * form or same file name on the form or the file system itself, for a given participant id.
	 * @param participantForms the participantForm objects that correspond to the form entries.
	 * @param usedFormRowNumbers the row indexes that have an entry on the form.
	 * @return true if valid form entries for manuals.
	 */
	private boolean validateParticipantForms(participantForms, usedFormRowNumbers)
	{
		def allValid = true
		
		int participantFormIndex = 0
		for (rowNumber in usedFormRowNumbers)
		{			
			if (request.getFile("form.${rowNumber}")?.isEmpty() && (session["fileName[${rowNumber}]"] == null))
			{
				participantForms[participantFormIndex].fileName = null
			}

			if (!participantForms[participantFormIndex]?.validate())
			{
				allValid = false
			}
            else if(checkForReplication(participantForms, participantFormIndex, "formName"))
            {
                participantForms[participantFormIndex].errors.rejectValue('formName', 'participantForm.formName.uniqueIgnoreCase.invalid')
                allValid = false
            }
			else if(checkForReplication(participantForms, participantFormIndex, "fileName"))
			{
				participantForms[participantFormIndex].errors.rejectValue('fileName', 'participantForm.fileName.uniqueIgnoreCase.invalid')
				allValid = false
			}
			else if(participantForms[participantFormIndex]?.fileName != null && checkNoConflictWithExistingFileName(participantForms[participantFormIndex]?.fileName)) // check that file names provided dont match any existing files
			{
				participantForms[participantFormIndex].errors.rejectValue('fileName', 'participantForm.fileName.uniqueIgnoreCase.invalid')
				allValid = false
			}
			participantFormIndex++
		}
		
		return allValid
	}
	
	/**
	 * Checks that there is no conflict of a file name that is trying to be loaded up by the form and the current files on the file system for a 
	 * given participant. The participant forms are grouped into directories numbered by the participant id they belong to on the database.
	 * @param _filename The file name checking there is a conflict with.
	 * @return boolean indicating true there is a conflict with the file name supplied and the files on the file system.
	 */
	private boolean checkNoConflictWithExistingFileName(_filename)
	{
		boolean conflict = false
		
		def participantInstance = Participant.get(params.participantId)
		def f = new File( getRealPath() + params.participantId.toString() )
		if( f.exists() )
		{
			f.eachFile()
			{ file->
				if( !file.isDirectory() )
				{
					def participantForm = ParticipantForm.findWhere(storedFileName: file.name, participant: participantInstance)
					if(participantForm && _filename && participantForm?.storedFileName?.equalsIgnoreCase(_filename))
					{
						conflict = true
					}
				}
			}
		}
		
		return conflict
	}
	
	/**
	 * Checks for either the participantForm name or the form name conflicts on the actual form values submitted
	 * @param _participantForms The set of participant forms
	 * @param index the participant form entry index in the collection
	 * @param fieldname the fieldname that is being checked for replication
	 * @return if replicated field returns true.
	 */
	private boolean checkForReplication(_participantForms, index, fieldname)
	{
		boolean replication = false
		
		if(fieldname?.equalsIgnoreCase("fileName"))
		{
			replication = ((_participantForms.findAll { it?.fileName.equalsIgnoreCase(_participantForms[index]?.fileName) }).size() > 1)
		}
		else if((fieldname?.equalsIgnoreCase("formName")))
		{
			replication = ((_participantForms.findAll { it?.formName.equalsIgnoreCase(_participantForms[index]?.formName) }).size() > 1)
		}
			
		return replication
	}
	/**
	* Generates a List of integers representing the row number of the participant form that has an entry to download a manual.
	* @return List of integers representing the entries on the form that there is a manual to download.
	*/
	private participantFormsToLoad()
	{
		def participantFormCommand = new ParticipantFormCommand()
		bindData( participantFormCommand, params )
		List<Integer> usedFields = new ArrayList<Integer>();
		participantFormCommand.forms.eachWithIndex()
		{ obj, i ->

			if ((obj?.formName.size() > 0) || obj?.fileName.size() >0)
			{
				usedFields.add (i)
			}
		}
        
		return usedFields
	}

	private populateSessionValues()
	{
		for (i in participantFormsToLoad())
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
		return ( grailsApplication.config.forms.location.toString())
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

	private saveFile(file, participantFormInstance)
	{
		if (!file?.isEmpty() && !(file == null))
		{
			def fileExtension = getFileExtension(file?.getOriginalFilename())
			def fileName = file?.getOriginalFilename()
			def dirName = new File(getRealPath() + params.participantId.toString())
			if (!dirName.exists()) {
				dirName.mkdirs();
			}
			file.transferTo( new File( getRealPath() + params.participantId.toString() +File.separatorChar + fileName) )
			participantFormInstance.form = participantFormInstance.formName
			participantFormInstance.contentType = file.contentType
			participantFormInstance.fileExtension = fileExtension
			participantFormInstance.storedFileName = fileName
            participantFormInstance.save(flush: true)
            
		}
		else
		{
			participantFormInstance.delete(flush: true)
		}
	}

    private renderUploadErrorMsg(participantForms)
    {
        def participantFormInstanceList = []
        def participantInstance = Participant.get(params.participantId)
        def f = new File( getRealPath() + params.participantId.toString() )
        if( f.exists() )
        {
            f.eachFile()
            { file->
                if( !file.isDirectory() )
                {
                    def participantForm = ParticipantForm.findWhere(storedFileName: file.name, participant: participantInstance)
                    if(participantForm != null)
                    {
                        participantFormInstanceList.add(participantForm)
                    }
                }
            }
        }
        render(view: "list", model: [participantForms: participantForms,participantFormInstanceList: participantFormInstanceList, participantFormInstanceTotal: participantFormInstanceList.size(), participantInstance: Participant.get(params.participantId), forms:participantForms, fileName: params.fileName, participantId: params.participantId ])
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def upload =
	{
		cache false
		def participantForms = []
		def allValid = true
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
        
		// find row number of participant form rows with entries
		List<Integer> usedFormRowNumbers = participantFormsToLoad()
		def participantFormIndex = 0
        for (rowNumber in usedFormRowNumbers)
		{
			participantForms[participantFormIndex++] = new ParticipantForm(params["forms["+rowNumber+"]"])
        }

		// check if participant form entries not valid - handleSaveParticipantForm()
		if (!validateParticipantForms(participantForms, usedFormRowNumbers))
		{
			populateSessionValues()
			renderUploadErrorMsg(participantForms);
		}
		else // if the form entries are valid then save entries to the database and the file system
		{
			participantFormIndex = 0
			for (rowNumber in usedFormRowNumbers)
			{
                if (participantForms[participantFormIndex].save(flush: true))
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
					saveFile(file, participantForms[participantFormIndex])
				}
                else
                {
                    allValid = false    
                }
                participantFormIndex++
			}
			
			participantFormIndex = 0
			// remove database entries that were saved if ANY of the row entries failed - handleCleanup()
            if (allValid == false)
            {
                for (rowNumber in usedFormRowNumbers)
                {
                    participantForms[participantFormIndex].delete(flush:true)
                }
                
                renderUploadErrorMsg(participantForms);
                return
            }
            else // remove state as save was successful
            {
                clearSessionValues()
            }
            
			reportSuccessfulUpdate(usedFormRowNumbers)

			redirect url: createLink(controller: 'participantForm', action:'list',
			mapping:'participantFormDetails', params:[studyId: params.studyId, participantId: params.participantId])
		}
	}
	
	private reportSuccessfulUpdate(_usedFormRowNumbers)
	{
		switch (_usedFormRowNumbers.size())
		{
			case 0: flash.error = "No forms selected to upload"
			break

			case 1: flash.message = "${_usedFormRowNumbers.size()} Participant Form uploaded"
			break

			case 2..10: flash.message = "${_usedFormRowNumbers.size()} Participant Forms uploaded"
			break
			default:
			break
		}
	}
	
	/**
	* Display Study only to research owner or research collaborator
	* @param _studyInstance
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
	def downloadFile =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}

		def fileDoc = new File( getRealPath() + params.participantId.toString() +File.separatorChar + participantFormInstance.fileName)

		if(fileDoc.exists())
		{
			def fileName = participantFormInstance.fileName

			if (participantFormInstance.contentType == null)
			{
				response.setContentType(getMimeType(fileDoc))
			}
			else
			{
				response.setContentType participantFormInstance.contentType
			}
			response.setHeader "Content-disposition", "attachment; filename=\"${fileName}\"" ;
			response.outputStream << fileDoc.newInputStream();
			response.outputStream.flush();

			return false
		}
		return null
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def list =
	{
		cache false
		def participantInstance = Participant.get(params.participantId)
		def participantFormInstanceList = []
		def participantForms = []
		
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		def f = new File( getRealPath() + params.participantId.toString() )
		
		if( f.exists() )
		{
			f.eachFile()
			{ file->
				if( !file.isDirectory() )
				{
					def participantForm = ParticipantForm.findWhere(storedFileName: file.name, participant: participantInstance)
                    if(participantForm != null)
					{
						participantFormInstanceList.add(participantForm)
					}
				}
			}
		}
		[participantFormInstanceList: participantFormInstanceList, participantFormInstanceTotal: participantFormInstanceList.size(), participantInstance: participantInstance,participantForms: participantForms, forms:participantForms, participantId: params.participantId]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def create =
	{
		cache false
		def participantFormInstance = new ParticipantForm()
		participantFormInstance.properties = params
		def participantForms = []
		return [participantFormInstance: participantFormInstance, participantForms: participantForms]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def save =
	{
		cache false
		def participantFormInstance = new ParticipantForm(params)
		if (participantFormInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), participantFormInstance.id])}"
			redirect(action: "show", id: participantFormInstance.id)
		}
		else
		{
			render(view: "create", model: [participantFormInstance: participantFormInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def show =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		if (!participantFormInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			[participantFormInstance: participantFormInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def edit =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		if (!participantFormInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [participantFormInstance: participantFormInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def update =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		if (participantFormInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (participantFormInstance.version > version)
				{

					participantFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'participantForm.label', default: 'ParticipantForm')]
					as Object[], "Another user has updated this ParticipantForm while you were editing")
					render(view: "edit", model: [participantFormInstance: participantFormInstance])
					return
				}
			}
			participantFormInstance.properties = params
			if (!participantFormInstance.hasErrors() && participantFormInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), participantFormInstance.id])}"
				redirect(action: "show", id: participantFormInstance.id)
			}
			else
			{
				render(view: "edit", model: [participantFormInstance: participantFormInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participantForm.label', default: 'ParticipantForm'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def delete =
	{
		cache false
		def participantFormInstance = ParticipantForm.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		if (participantFormInstance)
		{
			try
			{
				participantFormInstance.delete(flush: true)
				def filename = participantFormInstance.fileName
				def file =  new File( getRealPath() + params.participantId.toString() +File.separatorChar + participantFormInstance.fileName)
				if (file.exists())
				{
					file.delete()
				}
				flash.message = "Participant Form ${participantFormInstance.formName} deleted"
				redirect url: createLink(controller: 'participantForm', action:'list',
					mapping:'participantFormDetails', params:[studyId: params.studyId, participantId: params.participantId])
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "Participant Form ${participantFormInstance.formName} could not be deleted"
				redirect url: createLink(controller: 'participantForm', action:'list',
					mapping:'participantFormDetails', params:[studyId: params.studyId, participantId: params.participantId])
			}
		}
		else
		{
			flash.message = "ParticipantForm ${participantFormInstance.formName} could not be found"
			redirect url: createLink(controller: 'participantForm', action:'list',
				mapping:'participantFormDetails', params:[studyId: params.studyId, participantId: params.participantId])
		}
	}
}
class ParticipantFormCommand
{
	ParticipantForm[] forms = [
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm(),
		new ParticipantForm()] as ParticipantForm[]
}