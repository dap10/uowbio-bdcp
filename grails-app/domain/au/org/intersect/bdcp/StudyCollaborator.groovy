package au.org.intersect.bdcp

import au.org.intersect.bdcp.enums.UserRole

class StudyCollaborator{
	
	Study study
	UserStore collaborator
	
	StudyCollaborator() {
		// define default constructor
	}
	
	StudyCollaborator(Study _study, UserStore _collaborator) {
		study = _study
		collaborator = _collaborator
	}

    static mapping = {
        studyCollaboratorFields cascade:'all-delete-orphan'
    }

    
    static constraints = {
        study(nullable: true)
        collaborator(nullable: true)
        
    }
	
}
