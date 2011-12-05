package au.org.intersect.bdcp

import grails.test.*
import au.org.intersect.bdcp.enums.UserRole


/**
* Unit tests for the domain class {@link UserStore}
*/
class UserTests extends GrailsUnitTestCase {
    
	def userStore
	
	/**
	* Setup operations before each test
	*/
	protected void setUp() {
        super.setUp()
		
		userStore = new UserStore(username:"dpollum", authority: UserRole.ROLE_LAB_MANAGER)
		
		mockForConstraintsTests UserStore, [ userStore ]
    }

	/**
	* Cleanup operations after each test
	*/
    protected void tearDown() {
        super.tearDown()
    }

	/**
	* Test that the blank fields in the domain class {@link UserStore} are
	* correctly validated
	*/
    void testBlank() {

		userStore = new UserStore(username: '', authority:'')
		assertFalse 'No validation for blank field(s)' ,userStore.validate()
		assertEquals 'Username is blank.','blank', userStore.errors['username']
		assertEquals 'Authority is nullable.','nullable', userStore.errors['authority']
		userStore = new UserStore(username: "test", authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
		assertTrue "A valid user did not validate!", userStore.validate()
    }
	
	/**
	* Test that the unique fields in the domain class {@link UserStore} are
	* correctly validated
	*/
	void testUnique() {
		
				userStore = new UserStore(username: "dpollum", authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
				assertFalse 'No validation for unique field(s)' ,userStore.validate()
				assertEquals 'Username is not unique.','unique', userStore.errors['username']
				userStore = new UserStore(username: "test", authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
				assertTrue "A valid user did not validate!", userStore.validate()
			}
	/**
	* Test the range validation of fields in the domain class {@link UserStore} are
	* correctly validated
	*/
   void testRange()
   {
		userStore = new UserStore(username: "012345678910" * 100, authority: UserRole.ROLE_LAB_MANAGER, title: '1' * 21)
 
	   assertFalse "No validation for size of fields", userStore.validate()
 
	   assertEquals 'Username does not validate size.','size', userStore.errors['username']
	   assertEquals 'Title field does not validate size.','size', userStore.errors['title']
	   
	  userStore = new UserStore(username: "test", authority: UserRole.ROLE_LAB_MANAGER, title:'Mrs')
 
	   assertTrue userStore.validate()
   }
   
   /**
   * Test the range validation of fields in the domain class {@link UserStore} are
   * correctly validated
   */
  void testToString()
  {
	  assertEquals "The toString() method returned incorrectly.", "dpollum", userStore.toString()
  }
   
}
