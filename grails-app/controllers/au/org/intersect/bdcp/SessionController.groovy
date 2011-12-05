package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class SessionController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def fileService
	
	def roleCheckService
	
	def grailsApplication
    
    def createContext()
    {
        return fileService.createContext( "session")
    }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def index =
	{
		redirect(action: "list", params: params)
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def list =
	{
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[sessionInstanceList: Session.list(params), sessionInstanceTotal: Session.count()]
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def create =
	{
		def sessionInstance = new Session()
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		sessionInstance.properties = params
		return [sessionInstance: sessionInstance]
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def save =
	{
        def context = createContext()
        def sessionInstance = new Session(params)
        def path = params.studyId +"/" + sessionInstance.component.id + "/"
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		if (sessionInstance.save(flush: true))
		{
            fileService.createDirectory(grailsApplication.config.bdcp.files.root, params.studyId.toString(),"")
            fileService.createDirectory(grailsApplication.config.bdcp.files.root, sessionInstance.id.toString(), path)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'session.label', default: 'Session'), sessionInstance.name])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
		else
		{
			render(view: "create", model: [sessionInstance: sessionInstance])
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
	def show =
	{
		def sessionInstance = Session.get(params.id)
		if (!sessionInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), params.name])}"
			redirect(action: "list")
		}
		else
		{
			[sessionInstance: sessionInstance]
		}
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def edit =
	{
		
		def sessionInstance = Session.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		if (!sessionInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), params.name])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
		else
		{
			return [sessionInstance: sessionInstance]
		}
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def update =
	{
		def sessionInstance = Session.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		if (sessionInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (sessionInstance.version > version)
				{

					sessionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'session.label', default: 'Session')]
					as Object[], "Another user has updated this Session while you were editing")
					render(view: "edit", model: [sessionInstance: sessionInstance])
					return
				}
			}
			sessionInstance.properties = params
			if (!sessionInstance.hasErrors() && sessionInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'session.label', default: 'Session'), sessionInstance.name])}"
				redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
			}
			else
			{
				render(view: "edit", model: [sessionInstance: sessionInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), params.name])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
	}

}
