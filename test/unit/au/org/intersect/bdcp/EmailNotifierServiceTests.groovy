package au.org.intersect.bdcp

import grails.plugin.mail.MailService
import grails.test.*
import groovy.mock.interceptor.MockFor

class EmailNotifierServiceTests extends GrailsUnitTestCase {
    
	def emailNotifierService
	def mockMail
	
	protected void setUp() {
        super.setUp()
		
		emailNotifierService = new EmailNotifierService()
		mockMail = new MockFor(MailService.class)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testServiceCall() {
		//emailNotifierService.contactUser("dpollum", "dpollum@uow.edu.au")

    }
}
