package au.org.intersect.bdcp

import grails.test.*


/**
* Unit tests for the domain class {@link Component}
*/
class ComponentTests extends GrailsUnitTestCase {
    
	def component
	
	def study
	
	def project
	
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
		
		mockForConstraintsTests Component, [component]
    }

	/**
	* Cleanup operations after each test
	*/
    protected void tearDown() {
        super.tearDown()
    }

	/**
	* Test that the blank fields in the domain class {@link Component} are
	* correctly validated
	*/
    void testBlank() {

		component = new Component(name: '',
			description: '', study:study)
		
		assertFalse 'No validation for blank field(s)' ,component.validate()
		assertEquals 'Name is blank.','blank', component.errors['name']
		assertEquals 'Description is blank.','blank', component.errors['description']
		component = new Component(name:"TestComponent too", 
			description: "Some Description", study:study)
		assertTrue "A valid component did not validate!", component.validate()
    }
	
	/**
	* Test the range validation of fields in the domain class {@link Component} are
	* correctly validated
	*/
   void testRange()
   {
		component = new Component(name:"012345678910" * 100,
			description: "012345678910" * 100,	
			study:study)
 
	   assertFalse "No validation for size of fields", component.validate()
 
	   assertEquals 'Name does not validate size.','size', component.errors['name']
	   assertEquals 'Description does not validate size.','size', component.errors['description']
	   
	   component = new Component(name:"TestComponent too",
			description: "Some Description",	
			study:study)
 
	   assertTrue component.validate()
   }
   
   /**
    * Test that the sorting of sessions in the domain class {@link Component} functions
    * correctly sorting by name
    */
   void testSort()
   {
      def mockComponent = new Component(name:"TestComponent",
           description: "Test Description",
           study: study)
       mockDomain(Component, [mockComponent])
       def session1 = new Session(name: "B",
           description: "Some Description",
           component: mockComponent)
       mockComponent.addToSessions(session1)
       
       def session2 = new Session(name:"A",
           description: "Some Description",
           component: mockComponent)
       
       mockComponent.addToSessions(session2)
       def session3 = new Session(name:"C",
           description: "Some description",
           component: mockComponent)
       mockComponent.addToSessions(session3)
       mockDomain(Session, [session1,session2,session3])
       
       List<Session> sessions = mockComponent.getSessionsList()
       assertEquals "Session order incorrect for first session", "A", sessions.get(0).name
       assertEquals "Session order incorrect for second session","B", sessions.get(1).name
       assertEquals "Session order incorrect for third session","C", sessions.get(2).name
   }
}
