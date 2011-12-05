package au.org.intersect.bdcp

import grails.test.*

/**
 * Unit tests for the domain class {@link Study}
 */
class StudyTests extends GrailsUnitTestCase
{
	def study
	
	def project

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
				keywords: 'keyword',
				collaborators: 'some collaborator',
				startDate: new Date(),
				endDate: new Date(),
				project: project,
				numberOfParticipants:"10",
				inclusionExclusionCriteria: "No Criteria")
		
		mockForConstraintsTests Study, [study]
	}

	/**
	 * Tear down operations after each test�
	 */
	protected void tearDown()
	{
		super.tearDown()
	}

	/**
	 * Test the domain class {@link Study} to make sure that blank fields
	 * are correctly validated
	 */
	void testBlank()
	{
		study = new Study(studyTitle: '',
				uowEthicsNumber: '' ,
				description: '',
				industryPartners: '',
				keywords: ',,',
				collaborators: '',
				startDate: '',
				endDate: '')

		study.beforeValidate() // Grails 1.3.7 BUG in Unit Test
		                       // http://jira.grails.org/browse/GRAILS-7432
		
		assertFalse 'No validation for blank field(s)' ,study.validate()

		assertEquals 'Study Title is blank.','blank', study.errors['studyTitle']
		assertEquals 'Description is blank.','blank', study.errors['description']
		assertEquals 'Subjects is blank.','blank', study.errors['keywords']
		
		study = new Study(studyTitle: 'Testing Study',
				uowEthicsNumber: '110680' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				keywords: 'keyword;',
				collaborators: 'some collaborator',
				startDate: new Date(),
				endDate: new Date(),
				project: project,
				numberOfParticipants:"10",
				inclusionExclusionCriteria: "No Criteria")
		
		assertTrue "A valid study did not validate!", study.validate()
	}
	
	/**
	* Test the domain class {@link Study} to make sure that unique fields
	* are correctly validated
	*/
   void testUnique()
   {
	   study = new Study(studyTitle: 'TestStudy',
				uowEthicsNumber: '110678' ,
				description: 'Test Description',
				industryPartners: 'Partner1',
				keywords: 'keyword',
				collaborators: 'some collaborator',
				startDate: new Date(),
				endDate: new Date(),
				project: project,
				numberOfParticipants:"10",
				inclusionExclusionCriteria: "No Criteria")

	   study.beforeValidate() // BUG http://jira.grails.org/browse/GRAILS-7432
	   assertFalse 'No validation for unique field(s)' ,study.validate()

	   assertEquals 'UOW Ethics Number is not unique.','unique', study.errors['uowEthicsNumber']
	   
	   study = new Study(studyTitle: 'Testing Study',
			   uowEthicsNumber: '110680' ,
			   description: 'Test Description',
			   industryPartners: 'Partner1',
			   keywords: 'keyword',
			   collaborators: 'some collaborator',
			   startDate: new Date(),
			   endDate: new Date(),
			   project: project,
			   numberOfParticipants:"10",
			   inclusionExclusionCriteria: "No Criteria")
	   
	   study.beforeValidate() // BUG http://jira.grails.org/browse/GRAILS-7432
	   assertTrue "A valid study did not validate!", study.validate()
   }
	
   /**
   * Test the range validation of fields in the domain class {@link Study} are
   * correctly validated
   */
  void testRange()
  {
	   study = new Study(studyTitle: '012345678910' * 100,
			   uowEthicsNumber: '012345678910' * 100,
			   description: '012345678910' * 100,
			   industryPartners: '012345678910' * 100,
			   keywords: '0123456789;' * 100,
			   collaborators: '012345678910' * 100,
			   startDate: new Date(),
			   endDate: new Date(),
			   project: project,
			   numberOfParticipants:'012345678910' * 100,
			   inclusionExclusionCriteria: '012345678910' * 100)

	  study.beforeValidate() // BUG http://jira.grails.org/browse/GRAILS-7432
	  assertFalse "No validation for size of fields", study.validate()

	  assertEquals 'Study Title does not validate size.','size', study.errors['studyTitle']
	  assertEquals 'UOW Ethics Number does not validate size.','size', study.errors['uowEthicsNumber']
	  assertEquals 'Description does not validate size.','size', study.errors['description']
	  assertEquals 'Industry Partners  does not validate size.','size', study.errors['industryPartners']
	  assertEquals 'Keywords field  does not validate size.','size', study.errors['keywords']
	  assertEquals 'Collaborators  does not validate size.','size', study.errors['collaborators']
	  assertEquals 'Number of Participants  does not validate size.','size', study.errors['numberOfParticipants']
	  assertEquals 'Inclusion Exclusion Criteria  does not validate size.','size', study.errors['inclusionExclusionCriteria']
	  
	  study = new Study(studyTitle: 'Testing Study',
			   uowEthicsNumber: '110680' ,
			   description: 'Test Description',
			   industryPartners: 'Partner1',
			   keywords: 'keyword',
			   collaborators: 'some collaborator',
			   startDate: new Date(),
			   endDate: new Date(),
			   project: project,
			   numberOfParticipants:"10",
			   inclusionExclusionCriteria: "No Criteria")
	  
	  study.beforeValidate() // BUG http://jira.grails.org/browse/GRAILS-7432
	  assertTrue study.validate()
  }
   
}
