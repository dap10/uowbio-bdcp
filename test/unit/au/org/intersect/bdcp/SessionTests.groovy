package au.org.intersect.bdcp

import grails.test.*


/**
* Unit tests for the domain class {@link Session}
*/
class SessionTests extends GrailsUnitTestCase {
    
	def component
	
	def study
	
	def project
	
	def session
	
	/**
	* Setup operations before each test
	*/
	protected void setUp() {
        super.setUp()
		
		project = new Project(projectTitle: 'TestProject',
			researcherName: 'TestStudent' ,
			degree: 'TestDegree',
			startDate: new Date(),
			endDate: new Date(),
			description: 'Test Description',
			supervisors: 'test supervisor')
		
		
		// Set up default Study so we can easily test single properties.
		study = new Study(studyTitle: 'TestStudy',
				uowEthicsNumber: '110678' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				collaborators: 'some collaborator',
				startDate: new Date(),
				endDate: new Date(),
				project: project,
				numberOfParticipants:"10",
				inclusionExclusionCriteria: "No Criteria")
		component = new Component(name:"TestComponent",
			description: "Some Description",	
			study:study)
		session = new Session(name:"TestSession", description: "Some Description",
			component: component)
		
		mockForConstraintsTests Session, [session]
    }

	/**
	* Cleanup operations after each test
	*/
    protected void tearDown() {
        super.tearDown()
    }

	/**
	* Test that the blank fields in the domain class {@link Session} are
	* correctly validated
	*/
    void testBlank() {

		session = new Session(name: '',
			description: '', component:component)
		
		assertFalse 'No validation for blank field(s)' ,session.validate()
		assertEquals 'Name is blank.','blank', session.errors['name']
		assertEquals 'Description is blank.','blank', session.errors['description']
		session = new Session(name:"TestSession too", 
			description: "Some Description", component:component)
		assertTrue "A valid session did not validate!", session.validate()
    }
	
	/**
	* Test the range validation of fields in the domain class {@link Session} are
	* correctly validated
	*/
   void testRange()
   {
		session = new Session(name:"012345678910" * 100, 
			description: "012345678910" * 100,
			component: component)
 
	   assertFalse "No validation for size of fields", session.validate()
 
	   assertEquals 'Name does not validate size.','size', session.errors['name']
	   assertEquals 'Description does not validate size.','size', session.errors['description']
	   
	   session = new Session(name:"TestSession too", description: "Some Description",
			component: component)
 
	   assertTrue session.validate()
   }
}
