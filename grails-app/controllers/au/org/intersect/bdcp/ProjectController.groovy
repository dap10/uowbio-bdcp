package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured
import au.org.intersect.bdcp.ldap.LdapUser

class ProjectController
{
	def springSecurityService
	
	def roleCheckService
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def index =
	{
		cache false
		redirect(action: "list", params: params)
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def list =
	{
		cache false

		params.max = Math.min( params.max ? params.max.toInteger() : 5, 100)
		def collaborator = UserStore.findByUsername(principal.username);
		def collaboratorProjectInstanceList = [];
		collaboratorProjectInstanceList = collaborator.studyCollaborators.collect { it.study.project }
		collaboratorProjectInstanceList = collaboratorProjectInstanceList.unique()
		def collaboratorProjectStudies = [];

		collaboratorProjectInstanceList.each
		{
			// look at each project in the collaborator list and remove studies that aren't collaborative studies
			collaboratorProjectStudies = it?.studies.findAll
			{
				it?.studyCollaborators.collaborator.username.findAll
				{
					it.equals(principal.username)
				}
			}
			it.collaboratorStudies = collaboratorProjectStudies
		}
	   
		def myProjectInstanceList = [];
		myProjectInstanceList = Project.findAllByOwner(UserStore.findByUsername(principal.username), params);
		def myCompleteProjectInstanceList = Project.findAllByOwner(UserStore.findByUsername(principal.username));
	   
		[myProjectInstanceList: myProjectInstanceList, myProjectInstanceListTotal: myCompleteProjectInstanceList.size(), collaboratorProjectInstanceList: collaboratorProjectInstanceList]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def listAll =
	{
		cache false
		
		params.max = Math.min( params.max ? params.max.toInteger() : 5, 100)
		def allProjectInstanceList = Project.list(params);
		
		// assign firstname and surname to an owner, so I can sort projects by first name
		def ldapUserDetail
		allProjectInstanceList.each
		{
			ldapUserDetail = LdapUser.find(filter: "(uid=${it?.owner?.username})")
			it?.owner?.firstName = ldapUserDetail?.givenName
			it?.owner?.surname = ldapUserDetail?.sn
		}
		
		allProjectInstanceList.sort{it.owner.firstName}
		
		[allProjectInstanceList: allProjectInstanceList, allProjectInstanceListTot: Project.count()]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def create =
	{
		cache false
		def projectInstance = new Project()
		projectInstance.properties = params
		
		return [projectInstance: projectInstance]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def createForResearcher =
	{
		cache false
		def projectInstance = new Project()
		projectInstance.properties = params
		
		return [projectInstance: projectInstance, nameCreateProjFor: params.nameCreateProjFor]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def searchUsers =
	{
		cache false
		def matches = []
		
		[matches:matches]
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def listUsers = 
	{
		cache false
		
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
			if (!it?.deactivated)
			{
				activatedMatches << LdapUser.find(filter: "(uid=${it?.username})")
			}
		}
		
		ldapUsers.retainAll(activatedMatches)
		
		def sortedActivatedMatches = ldapUsers.sort
		{x,y -> x.sn <=> y.sn}
		
		render (view: "searchUsers", model: [firstName: params.firstName, surname:params.surname, userid:params.userid, matches: sortedActivatedMatches])
	}
	
	private String normalizeValue(value)
	{
		value = value.replaceAll(/[^A-Za-z0-9-]/, '')
		return value
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def save =
	{
		cache false
		def projectInstance = new Project(params)
		
		def userStore = UserStore.findByUsername(principal.username)
		projectInstance.setOwner(userStore);
		
		if (projectInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.projectTitle])}"
			redirect(action: "list", id: projectInstance.id)
		}
		else
		{
			render(view: "create", model: [projectInstance: projectInstance])
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def saveUserProject =
	{
		cache false
		def projectInstance = new Project(params)
		
		def userStore = UserStore.findByUsername(params.projectOwnerName)
		projectInstance.setOwner(userStore);
		
		if (projectInstance.save(flush: true))
		{
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.projectTitle])}"
			redirect(action: "listAll", id: projectInstance.id)
		}
		else
		{
			render(view: "create", model: [projectInstance: projectInstance])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def show =
	{
		cache false
		def projectInstance = Project.get(params.id)
		
		if (!projectInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			// if researcher role, display research project to researcher who owns project, else redirect to non authorized access page.
			if(roleCheckService.checkUserRole('ROLE_RESEARCHER')) {
				redirectNonAuthorizedResearcherAccessProject(projectInstance)
			}
			[projectInstance: projectInstance]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def displayUser =
	{
		cache false
		def projectInstance = Project.get(params.id)
		
		if (!projectInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			[projectInstance: projectInstance, firstName: params.firstName, surname: params.surname]
		}
	}
	
	/**
	 * Display project only to research owner
	 * @param _projectInstance
	 */
	private void redirectNonAuthorizedResearcherAccessProject(Project _projectInstance)
	{
		if(!_projectInstance.owner.username.equals(principal.username)){
			redirect controller:'login', action: 'denied'
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def edit =
	{
		cache false
		def projectInstance = Project.get(params.id)
		if (!projectInstance)
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
		else
		{
			return [projectInstance: projectInstance]
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def update =
	{
		cache false
		def projectInstance = Project.get(params.id)
		if (projectInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (projectInstance.version > version)
				{

					projectInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'project.label', default: 'Project')]
					as Object[], "Another user has updated this Project while you were editing")
					render(view: "edit", model: [projectInstance: projectInstance])
					return
				}
			}
			projectInstance.properties = params
			if (!projectInstance.hasErrors() && projectInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.projectTitle])}"
				redirect(action: "show", id: projectInstance.id)
			}
			else
			{
				render(view: "edit", model: [projectInstance: projectInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN', 'ROLE_RESEARCHER'])
	def delete =
	{
		cache false
		def projectInstance = Project.get(params.id)
		if (projectInstance)
		{
			try
			{
				projectInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e)
			{
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
	}
	
	private currentUser() {
		return User.get (springSecurityService.principal.id)
	}
}
