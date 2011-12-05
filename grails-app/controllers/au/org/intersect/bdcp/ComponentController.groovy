package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class ComponentController
{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def fileService
	
	def roleCheckService
	
	def grailsApplication
    
    def createContext()
    {
        return fileService.createContext( "session")
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
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
        
        def sortedComponentInstanceList = Component.findAllByStudy(studyInstance).sort {x,y -> x.name <=> y.name }
        
		[componentInstanceList: sortedComponentInstanceList, componentInstanceTotal: Component.countByStudy(studyInstance), studyInstance: studyInstance]
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
		def componentInstance = new Component()
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		componentInstance.properties = params
		return [componentInstance: componentInstance]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def save =
	{
		def componentInstance = new Component(params)
        def context = createContext()
        def path = params.studyId +"/"
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
        if (componentInstance.save(flush: true))
        {
            fileService.createDirectory(grailsApplication.config.bdcp.files.root, params.studyId.toString(), "")
            fileService.createDirectory(grailsApplication.config.bdcp.files.root, componentInstance.id.toString(), path)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'component.label', default: 'Component'), componentInstance.name])}"
			//            redirect(action: "show", id: componentInstance.id)
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
		else
		{
			render(view: "create", model: [componentInstance: componentInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def show =
	{
		def componentInstance = Component.get(params.id)
		if (!componentInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			[componentInstance: componentInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def edit =
	{
		def componentInstance = Component.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		if (!componentInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), params.name])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
		else
		{
			return [componentInstance: componentInstance, studyId: params.studyId]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def update =
	{
		def componentInstance = Component.get(params.id)
		def studyInstance = Study.get(params.studyId)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}
		
		if (componentInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (componentInstance.version > version)
				{

					componentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'component.label', default: 'Component')]
					as Object[], "Another user has updated this Component while you were editing")
					render(view: "edit", model: [componentInstance: componentInstance])
					return
				}
			}
			componentInstance.properties = params
			if (!componentInstance.hasErrors() && componentInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'component.label', default: 'Component'), componentInstance.name])}"
				redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
			}
			else
			{
				render(view: "edit", model: [componentInstance: componentInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), params.id])}"
			redirect(mapping:"componentDetails",controller: "component", action: "list", id: params.studyId, params:[studyId: params.studyId])
		}
	}
	
}
