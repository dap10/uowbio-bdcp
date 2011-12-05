package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured
import au.org.intersect.bdcp.enums.UserRole
import au.org.intersect.bdcp.ldap.LdapUser



class AdminController
{

	def emailNotifierService
	def sessionRegistry
	def springSecurityService
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def index =
	{
		cache false
		redirect(action: "accountAdmin", params: params)
	}

	static transactional = true

	def allowedMethods = []

      @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
      def create =
      {
            cache false
            def accountStatus = "Failed"

			def username = params.username != null ? params.username : ""
			def givenName = params.givenName != null ? params.givenName : ""
			def sn = params.sn != null ? params.sn : ""
			def role = params.authority != null ? params.authority : ""
			def nlaIdentifier = params.nlaIdentifier != null ? params.nlaIdentifier : ""
			def title = params.title != null ? params.title : null
			
            def user;
            user = new UserStore(username: username, authority: role, nlaIdentifier:nlaIdentifier, title:title);

            if (user?.validate())
            {
                  accountStatus = "Successful"
				  def roleString = (UserRole)role.toString()
				  def rolename = roleString.getName();
                  render (view: "create", model:[username:username, givenName: givenName, sn: sn, role:role, rolename:rolename, nlaIdentifier:nlaIdentifier, title:title])
            }
            else
            {
                  accountStatus = "Failed"
                  render (view: "addRole", model:[accountStatus: accountStatus, user:user, username:username, givenName: givenName, sn: sn, authority:role, nlaIdentifier:nlaIdentifier, title:title])
            }
           
            return [username: username, givenName: givenName, sn: sn, role: role]
      }

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def save =
	{
		cache false
		def accountStatus = "Failed"
		def user
		def email

		if (params.username != null)
		{
			LdapUser match = LdapUser.find(
					filter: "(uid=${params.username})")
			if (match !=  null)
			{
				email = match.mail
				user= new UserStore(username: params.username, authority: params.role, nlaIdentifier:params.nlaIdentifier, title:params.title)
			}
		}
		if (user!= null && user.save(flush:true))
		{
			accountStatus = "Successful"
			emailNotifierService.contactUser(user.username, user.authority.getName(), email)
			render (view: "createStatus", model:[accountStatus: accountStatus, user: user ,username:params.username, role: params.role])
			session.firstName =  ""
			session.surname = ""
			session.userid = ""
		}
		else
		{
			accountStatus = "Failed"
			render (view: "createStatus", model:[accountStatus: accountStatus, user: user ,username:params.username])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def createStatus =
	{ cache false }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def systemAdmin =
    { cache false }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def resultsAdmin =
    { cache false }
    
    
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def accountAdmin =
	{ cache false }
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def search =
	{
		cache false
		def matches = []

		render(view: "search", model:[matches:matches])
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
		def hideDeactivatedUsers = (params.hideUsers == null || params.hideUsers == "false") ? false : true
		def matches = []
		def activatedMatches = []
		UserStore.list().each
		{
			matches <<LdapUser.find(filter: "(uid=${it?.username})")
			if (!it?.deactivated)
			{
				activatedMatches <<LdapUser.find(filter: "(uid=${it?.username})")
			}
		}
		def sortedMatches = matches.sort
		{x,y -> x.sn <=> y.sn}
		def sortedActivatedMatches = activatedMatches.sort
		{x,y -> x.sn <=> y.sn}
		if (hideDeactivatedUsers)
		{
			render (view: "listUsers", model: [ matches: sortedActivatedMatches, hideUsers: hideDeactivatedUsers])
		}
		else
		{
			render (view: "listUsers", model: [ matches: sortedMatches, hideUsers: hideDeactivatedUsers])
		}
	}

	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def edit =
	{
		cache false
		def match = LdapUser.find(filter: "(uid=${params.username})")
		def userStore = UserStore.findByUsername(params.username)
		render (view:"edit", model :[matchInstance: match, userInstance: userStore, hideUsers: params.hideUsers])
	}

	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def update =
	{
		cache false
		def match = LdapUser.find(filter: "(uid=${params.username})")
		def userInstance = UserStore.get(params.id)
		params.title = params.title == null ? null : params.title
		if (userInstance)
		{
			if (params.version)
			{
				def version = params.version.toLong()
				if (userInstance.version > version)
				{

					userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'userStore.label', default: 'UserStore')]
					as Object[], "Another user has updated this user while you were editing")
					render(view: "edit", model: [matchInstance:match, userInstance: userInstance])
					return
				}
			}
			def active = userInstance.deactivated
			userInstance.properties = params
			if (userInstance.deactivated && springSecurityService.principal.getUsername() == params.username)
			{
				flash.error="${userInstance.username} could not be deactivated because you are the current user"
				userInstance.deactivated = false
				render(view: "edit", model: [matchInstance:match, userInstance: userInstance])
				return
			}
			if (!userInstance.hasErrors() && userInstance.save(flush: true))
			{
				def message = "${userInstance.username} updated"
				if (userInstance?.deactivated != active)
				{
					message += " and ${userInstance.deactivated?'deactivated':'activated'} successfully"
				}
				else
				{
					message += " successfully"
				}
				flash.message = message
				if (userInstance?.deactivated)
				{
					
					sessionRegistry.getAllPrincipals().each {
						if (it.getUsername() == params.username)
						{
							sessionRegistry.getAllSessions(it, false).each {
								it.expireNow()
							}
						}
					}
				}
				redirect(action: "listUsers", params:["hideUsers":params.hideUsers])
			}
			else
			{
				println userInstance.errors
				render(view: "edit", model: [matchInstance:match, userInstance: userInstance])
			}
		}
		else
		{
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userStore.label', default: 'UserStore'), params.id])}"
			redirect(action: "listUsers", params:["hideUsers":params.hideUsers])
		}
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def searchUsers =
	{
		cache false
		def matches = []
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

		matches = LdapUser.findAll()
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

		def sortedMatches = matches.sort
		{x,y -> x.getUserId() <=> y.getUserId()?: x.sn <=> y.sn ?: x.givenName <=> y.givenName}

		render (view: "search", model: [firstName: params.firstName, surname:params.surname, userid:params.userid, matches: sortedMatches])
	}
	
	@Secured(['IS_AUTHENTICATED_FULLY', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def addRole =
	{
		cache false

		def username = params.username != null ? params.username : ""
		def givenName = params.givenName != null ? params.givenName : ""
		def sn = params.sn != null ? params.sn : ""
		def role = params.authority != null ? params.authority: ""
		def nlaIdentifier = params.nlaIdentifier != null ? params.nlaIdentifier : null
		
		return [username: username, givenName: givenName, sn: sn, role: role, nlaIdentifier:nlaIdentifier]
	}
	
}
