package au.org.intersect.bdcp
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class LogoutController
{

	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index =
	{
		cache false
		// TODO  put any pre-logout code here
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
	}
}
