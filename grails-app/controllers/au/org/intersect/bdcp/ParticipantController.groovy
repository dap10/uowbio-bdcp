package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class ParticipantController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def roleCheckService
	
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
		def studyInstance = Study.get(params.studyId)
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		def participantsInStudy = Participant.executeQuery('select count(p) from Participant p where p.study = :study',[study:studyInstance])
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[participantInstanceList: Participant.findAllByStudy(studyInstance), participantInstanceTotal: Participant.findAllByStudy(studyInstance).size(), studyInstance:studyInstance, participantsInStudy: participantsInStudy]
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
	def create =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		def participantInstance = new Participant()
		participantInstance.properties = params
		return [participantInstance: participantInstance]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def save =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		def participantInstance = new Participant(params)
		participantInstance.identifier = participantInstance.identifier?.trim()
		if (participantInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'participant.label', default: 'Participant'), participantInstance.identifier])}"
			//			redirect url:createLink(controller: 'participant', action:'list',
			//				mapping:'participantDetails', params:[studyId: params.studyId, id: participantInstance.id])
			redirect(mapping:"participantDetails", controller: "participant", action: "list", id: params.studyId, params:["studyId":params.studyId])
		}
		else
		{
			render(view: "create", model: [participantInstance: participantInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def show =
	{
		cache false
		def participantInstance = Participant.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		if (!participantInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
			redirect(mapping:"participantDetails", controller: "participant", action: "list", id: params.studyId, params:["studyId":params.studyId])
		}
		else
		{
			[participantInstance: participantInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def edit =
	{
		cache false
		def participantInstance = Participant.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		if (!participantInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
			redirect(mapping:"participantDetails", controller: "participant", action: "list", id: params.studyId, params:["studyId":params.studyId])
		}
		else
		{
			return [participantInstance: participantInstance, studyId: params.studyId]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def update =
	{
		cache false
		def participantInstance = Participant.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		if (participantInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (participantInstance.version > version)
				{

					participantInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'participant.label', default: 'Participant')]
					as Object[], "Another user has updated this Participant while you were editing")
					render(view: "edit", model: [participantInstance: participantInstance])
					return
				}
			}
			participantInstance.properties = params
			participantInstance.identifier = participantInstance.identifier?.trim()
			if (!participantInstance.hasErrors() && participantInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'participant.label', default: 'Participant'), participantInstance.identifier])}"
				redirect(mapping:"participantDetails", controller: "participant", action: "list", id: params.studyId, params:["studyId":params.studyId])
			}
			else
			{
				render(view: "edit", model: [participantInstance: participantInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
			redirect(controller: "study", action: "show", id: params.studyId, params:["tab":"ui-tabs-1"])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def delete =
	{
		cache false
		def participantInstance = Participant.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		if (participantInstance)
		{
			try
			{
				participantInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'participant.label', default: 'Participant'), params.id])}"
			redirect(action: "list")
		}
	}
}
