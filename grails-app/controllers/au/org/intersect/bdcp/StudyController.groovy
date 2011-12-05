package au.org.intersect.bdcp

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

import grails.plugins.springsecurity.Secured

import au.org.intersect.bdcp.rifcs.Rifcs
import au.org.intersect.bdcp.ldap.LdapUser

class StudyController
{
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def rifcsService
	
	def roleCheckService
	
	def factory = TransformerFactory.newInstance()
	
	def getContextRootPath(def servletRequest)
	{
		return servletRequest.getSession().getServletContext().getRealPath("/")
	}
	

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}


	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def list =
	{
		cache false
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[studyInstanceList: Study.list(params), studyInstanceTotal: Study.count()]
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
    def device = 
    {
        cache false
        
    }
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def create =
	{
		cache false
		def projectid = params.projectId
		def studyInstance = new Study()
		studyInstance.properties = params

		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudyProject(projectid)
		}
		
		return [studyInstance: studyInstance, projectid: projectid]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def listCollaborators =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}

		def collaborators = studyInstance.studyCollaborators.collect { it.collaborator }
		
		def matches = []
		collaborators.each
		{
			matches << LdapUser.find(filter: "(uid=${it?.username})")
		}
		def sortedCollaboratorMatches = matches.sort
		{x,y -> x.sn <=> y.sn}

		[studyInstance: studyInstance, collaboratorInstanceList: sortedCollaboratorMatches, collaboratorInstanceTotal: sortedCollaboratorMatches.size()]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def searchCollaborators =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		def matches = []

		[matches:matches, studyInstance: studyInstance]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def addCollaborator = 
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		def userStore = UserStore.findByUsername(params.username)

		def studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(studyInstance, userStore)
		
		if (!studyCollaborator) { // if collaborator doesn't already exist
			studyCollaborator = new StudyCollaborator(studyInstance,userStore)
			studyCollaborator.save(flush: true)
		}
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
		}

		def collaborators = studyInstance.studyCollaborators.collect { it.collaborator }
		
		def matches = []
		collaborators.each
		{
			matches << LdapUser.find(filter: "(uid=${it?.username})")
		}
		def sortedCollaboratorMatches = matches.sort
		{x,y -> x.sn <=> y.sn}
		
		render(view: "listCollaborators", model: [studyInstance: studyInstance, username: params.username, collaboratorInstanceList: sortedCollaboratorMatches, collaboratorInstanceTotal: sortedCollaboratorMatches.size()])
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def deleteCollaborator =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		def userStore = UserStore.findByUsername(params.collaboratorUid)

		def studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(studyInstance,userStore)
		if(studyCollaborator) {
			studyCollaborator.delete(flush: true)
		}

		def collaborators = studyInstance.studyCollaborators.collect { it.collaborator }
		
		def matches = []
		collaborators.each
		{
			matches << LdapUser.find(filter: "(uid=${it?.username})")
		}
		def sortedCollaboratorMatches = matches.sort
		{x,y -> x.sn <=> y.sn}
		
		render(view: "listCollaborators", model: [studyInstance: studyInstance, collaboratorInstanceList: sortedCollaboratorMatches, collaboratorInstanceTotal: sortedCollaboratorMatches.size()])
	}
	
	private String normalizeValue(value)
	{
		value = value.replaceAll(/[^A-Za-z0-9-]/, '')
		return value
	}
	
	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def listUsers =
	{
		cache false
		def studyInstance = Study.get(params.studyId)
		
		if (params.firstName != null)
		{
			session.firstName = params.firstName
		}
		else
		{
			session.firstName = ""
		}
		if (params.surname != null)
		{
			session.surname = params.surname
		}
		else
		{
			session.surname=""
		}
		if (params.userid != null)
		{
			session.userid = params.userid
		}
		else
		{
			session.userid = ""
		}
		
		def ldapUsers = []
		ldapUsers = LdapUser.findAll()
		{
			and
			{
				if (!session.userid?.isEmpty())
				{
					like "uid", "*" + normalizeValue(session.userid) + "*"
				}
				else
				{
					like "uid", "*"
				}
			}
			and
			{
				if (!session.surname?.isEmpty())
				{
					like "sn", "*" + normalizeValue(session.surname) + "*"
				}
				else
				{
					like "sn", "*"
				}
			}
			and
			{
				if (!session.firstName?.isEmpty())
				{
					like "givenName", "*" + normalizeValue(session.firstName) +"*"
				}
				else
				{
					like "givenName", "*"
				}
			}
		}
		
		def activatedMatches = []
		UserStore.list().each
		{
			// if not deactivated user and user not the owner of the study
			if ((!it?.deactivated) && (it?.username != studyInstance.project.owner.username))
			{
				activatedMatches << LdapUser.find(filter: "(uid=${it?.username})")
			}
		}
		
		activatedMatches = removeExistingCollaboratorsFromMatches(studyInstance, activatedMatches)
		
		ldapUsers.retainAll(activatedMatches)
		
		def sortedActivatedMatches = ldapUsers.sort
		{x,y -> x.sn <=> y.sn}
		
		render (view: "searchCollaborators", model: [firstName: params.firstName, surname:params.surname, userid:params.userid, matches: sortedActivatedMatches, studyInstance: studyInstance])

	}
	
	private Object removeExistingCollaboratorsFromMatches(studyInstance, activatedMatches)
	{
		for (collaboratorName in studyInstance.studyCollaborators.collaborator.username) {
			activatedMatches = activatedMatches.findAll { !(it?.uid == collaboratorName)}
		}
		return activatedMatches;
	}
	
	private Object matchWithSearchParameters(activatedMatches, matches)
	{
		for (match in matches) {
			println "the match was..." + match
		}
		return activatedMatches;
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def save =
	{
		cache false
		def projectid = params.projectId
		def studyInstance = new Study(params)
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudyProject(projectid)
		}
		
		if (studyInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'study.label', default: 'Study'), studyInstance.studyTitle])}"
			redirect(action: "show", id: studyInstance.id)
		}
		else
		{
			render(view: "create", model: [studyInstance: studyInstance, projectid: projectid])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def show =
	{
		cache false
		def studyInstance = Study.get(params.id)
		def canPublish = (roleCheckService.checkSameUser(studyInstance.project.owner.username) || roleCheckService.checkUserRole('ROLE_LAB_MANAGER') || roleCheckService.checkUserRole('ROLE_SYS_ADMIN'))
		
		// if ur a researcher and you either own or collaborate on a study then look at it, else error page.
		// researchers cant publish
		if (roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
			redirectNonAuthorizedResearcherAccessStudy(studyInstance)
			canPublish = false
		}
		
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		if (!studyInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(controller: "project", action: "list")
		}
		else
		{
			[studyInstance: studyInstance, canPublish: canPublish,
				participantInstanceList: Participant.findAllByStudy(studyInstance), participantInstanceTotal: Participant.countByStudy(studyInstance),projectid: params.projectid]
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
   
   /**
   * Display Study only to research owner or research collaborator
   * @param _projectid
   */
   private void redirectNonAuthorizedResearcherAccessStudyProject(_projectid)
   {
	   def userStore = UserStore.findByUsername(principal.username)
	   def project = Project.get(_projectid)
	   def projectStudies = project.studies

	   // if ur the project owner then dont redirect
	   if(project.owner.username.equals(principal.username))
			return;

	   if(projectStudies.size() > 0) { // if there are studies belonging to the project check that the user is a collaborator of any study for the project
		   def studyCollaborator = false
		   for (study in projectStudies) {
			   if(StudyCollaborator.findByStudyAndCollaborator(study,userStore)) {
				   studyCollaborator = true
			   }
		   }
		   if(!studyCollaborator) { // there are no collaborators of studies with username belonging to this project
			   redirect controller:'login', action: 'denied'
		   }
	   }
	   else { // if not collaborator then not authorised for a researcher
		   if(!project.owner.username.equals(principal.username)){
			   redirect controller:'login', action: 'denied'
		   }
	   }
   }

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def showRifcs = 
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (!studyInstance)
		{
			response.contentType = "text/plain"
			render "ERROR"
			return null
		}
		else
		{
			if (studyInstance.project.owner.nlaIdentifier == null
				|| studyInstance.project.owner.nlaIdentifier.length() == 0)
			{
				response.contentType = "text/plain"
				render "OWNER ERROR"
				return null
			}
			def rifcsXslt = this.getClass().getResourceAsStream('rifcs2preview.xslt')
			if (rifcsXslt == null)
			{
				response.contentType = "text/plain"
				render "XSLT ERROR"
				return null
			}
			def xmlStream = rifcsService.streamXml(rifcsService.createStudyXml(studyInstance,
				[['key':'(from project owner)','type':'isManagedBy'],['key':'(from BRL)','type':'isOwnedBy']]))
			def os = new ByteArrayOutputStream()
			os << xmlStream
			os.close()
			def transformer = factory.newTransformer(new StreamSource(rifcsXslt))
			response.contentType = "text/plain"
			transformer.transform(new StreamSource(new ByteArrayInputStream(os.toByteArray())), new StreamResult(response.outputStream))
			response.outputStream.flush()
			response.outputStream.close()
			return null
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def publishRifcs = 
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (!studyInstance)
		{
			response.contentType = "text/plain"
			render "ERROR"
			return
		}
		else
		{
			if (!roleCheckService.checkSameUser(studyInstance.project.owner.username) && !roleCheckService.checkUserRole('ROLE_LAB_MANAGER')) {
				response.contentType = "text/plain"
				render "ERROR"
				return
			}
			studyInstance.published = true
			studyInstance.project.owner.published = true
			studyInstance.project.owner.save(flush:true)
			studyInstance.save(flush:true)
			rifcsService.schedulePublishing(getContextRootPath(request))
			response.contentType = "text/plain"
			render "OK"
			return
		}
	}
	
	def buildRifcs =
	{ studyInstance ->
		def rifcs = new Rifcs()
		def specials = [
			'collection.identifier.local' :  createLink(mapping:'studyDetails', action:'show', params:[id:studyInstance.id, projectId:studyInstance.project.id])
			]
		def rifcsModel = rifcs.fromStudy(studyInstance, specials)
		return rifcsModel
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def edit =
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (!studyInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [studyInstance: studyInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def update =
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (studyInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (studyInstance.version > version)
				{

					studyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'study.label', default: 'Study')]
					as Object[], "Another user has updated this Study while you were editing")
					render(view: "edit", model: [studyInstance: studyInstance])
					return
				}
			}
			studyInstance.properties = params
			if (!studyInstance.hasErrors() && studyInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'study.label', default: 'Study'), studyInstance.studyTitle])}"
				redirect(action: "show", id: studyInstance.id)
			}
			else
			{
				render(view: "edit", model: [studyInstance: studyInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_RESEARCHER', 'ROLE_SYS_ADMIN'])
	def delete =
	{
		cache false
		def studyInstance = Study.get(params.id)
		if (studyInstance)
		{
			try
			{
				studyInstance.delete(flush: true)
				//				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
				//				redirect(action: "list")
				redirect(controller:"project", action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])}"
			redirect(action: "list")
		}
	}
}
