 package au.org.intersect.bdcp

import java.util.Date;

import au.org.intersect.bdcp.enums.UserRole

class UserStore{

    String username
	String firstName
	String surname
	String title
	boolean deactivated
    UserRole authority
    String nlaIdentifier // RIF-CS
	Boolean published // RIF-CS
	Date dateCreated
	Date lastUpdated

	
	static hasMany = [studyCollaborators: StudyCollaborator]
	
    static constraints = {
    	username(blank:false, unique:true, size:1..1000)
		firstName(nullable:true, size:1..1000)
		surname(nullable:true, size:1..1000)
		title(nullable:false, blank:false, size:2..20)
		authority(nullable:false, blank:false)
		nlaIdentifier(nullable:true, blank:false, maxSize:255)
		published(nullable:true)
	}
	
	String toString() {
		return "${username}"
	}
	
}
