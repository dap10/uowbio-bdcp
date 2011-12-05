package au.org.intersect.bdcp.constraints

import grails.test.*


/**
* Unit tests for the domain class {@link UserStore}
*/
class ValidFilenameConstraintTests extends GrailsUnitTestCase {
    
	def validator
	
	/**
	* Setup operations before each test
	*/
	protected void setUp() {
        super.setUp()
        validator = new ValidFilenameConstraint()
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
    void testSpecialCharacters() {
		assertFalse 'No validation for slash' , validator.validate("aa/bb")
    }
	
    void testSpecialNames() {
		assertFalse 'No validation for lpt2:' , validator.validate(" lpt2: ")
		assertFalse 'No validation for com1' , validator.validate(" com1 ")
    }

}
