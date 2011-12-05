package au.org.intersect.bdcp

class RoleCheckService {

    static transactional = false

	def springSecurityService
	
	/**
	* Check the role of the logged in user.
	*/
   def boolean checkUserRole(roleType)
   {
	   def auth = springSecurityService.authentication;
	   def role = auth.getPrincipal().getAuthorities()[0];
	   return role.equals(roleType)
   }
   
   /**
   * Check the role of the logged in user.
   */
  def boolean checkSameUser(username)
  {
	  def auth = springSecurityService.authentication;
	  def ppal = auth.getPrincipal()
	  return ppal.getUsername().equals(username)
  }

  def boolean isCollaborator(studyInstance)
  {
	   def userStore = UserStore.findByUsername(getUsername())
	   def studyCollaborator = StudyCollaborator.findByStudyAndCollaborator(studyInstance,userStore)
	   return studyCollaborator != null
  }
  
  def getUsername()
  {
	  def auth = springSecurityService.authentication;
	  return auth.getPrincipal().getUsername()
  }

}
