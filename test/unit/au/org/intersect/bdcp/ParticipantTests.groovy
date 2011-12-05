package au.org.intersect.bdcp

import grails.test.*

/**
 * Unit tests for the domain class {@link Participant}
 */
class ParticipantTests extends GrailsUnitTestCase
{
	def study
	
	def project
	
	def participant

	/**
	 * Setup operations before each test
	 */
	protected void setUp()
	{
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
		participant = new Participant(identifier:"101",
			    study:study)
		
		mockForConstraintsTests Participant, [participant]
	}

	/**
	 * Tear down operations after each testì
	 */
	protected void tearDown()
	{
		super.tearDown()
	}

	/**
	 * Test the domain class {@link Participant} to make sure that blank fields
	 * are correctly validated
	 */
	void testBlank()
	{
		participant = new Participant(identifier:"",
			    study:study)

		assertFalse 'No validation for blank field(s)' ,participant.validate()

		assertEquals 'Identifier is blank.','blank', participant.errors['identifier']
		
		participant = new Participant(identifier:"1",
			study:study)
		
		assertTrue "A valid participant did not validate!", participant.validate()
	}
	
	/**
	* Test the domain class {@link Participant} to make sure that unique fields
	* are correctly validated
	*/
	void testUnique()
	{
		participant = new Participant(identifier:"101",
			study:study)
		
		assertFalse 'No validation for unique field(s)', participant.validate()
		
		assertEquals 'Identifier is not unique.','unique', participant.errors['identifier']
		
		participant = new Participant(identifier:"102",
			study:study)
		
		assertTrue "A valid participant did not validate!", participant.validate()
	}
	
	/**
	* Test the range validation of fields in the domain class {@link Participant} are
	* correctly validated
	*/
   void testRange()
   {
		participant = new Participant(identifier:"012345678910" * 100,
			study:study)
 
	   assertFalse "No validation for size of fields", participant.validate()
 
	   assertEquals 'Identifier does not validate size.','size', participant.errors['identifier']
	   
	   participant = new Participant(identifier:"102",
		   study:study)
 
	   assertTrue participant.validate()
   }
	
}
