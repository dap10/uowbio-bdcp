package au.org.intersect.bdcp


import grails.test.GrailsUnitTestCase

import au.org.intersect.*


/**
 * Unit tests for the domain class {@link Project} 
 */
class ProjectTests extends GrailsUnitTestCase
{

	def project

	def study
	
    def user 
	/**
	 * Setup operations before each test
	 */
	protected void setUp()
	{
		super.setUp()
		
        user = new UserStore(username:"dpollum", authority:"ROLE_LAB_MANAGER")
        
		study = new Study(studyTitle: 'TestStudy',
			uowEthicsNumber: '110678' ,
			description: 'Test Description',
			industryPartners: 'Partner1',
			collaborators: 'some collaborator',
			startDate: new Date(),
			endDate: new Date())
		
		// Set up default Project so we can easily test single properties.
		project = new Project(projectTitle: 'TestProject',
				researcherName: 'TestStudent' ,
				degree: 'TestDegree',
				startDate: new Date(),
				endDate: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor',
				owner: user)
	
		project.studies = [study]
		
		mockForConstraintsTests Project, [project]
	}

	/**
	 * Perform these operations after each test
	 */
	protected void tearDown()
	{
		super.tearDown()
	}

	void testToString()
	{
		assertEquals "The toString() method returned incorrectly.", "TestProject", project.toString()
	}
	
	/**
	 * Test that the blank fields in the domain class {@link Project} are
	 * correctly validated
	 */
	void testBlank()
	{
		project = new Project(projectTitle: '',
				researcherName: '' ,
				degree: '',
				startDate: '',
				endDate: '',
				description: '',
				supervisors: '',
				owner: '')

		assertFalse "No validation exists for blank field(s)",project.validate()

		assertEquals 'Project Title is blank.','blank', project.errors['projectTitle']
		assertEquals 'Researcher Name is blank.','blank', project.errors['researcherName']
		assertEquals 'Degree is blank.','blank', project.errors['degree']
		assertEquals 'Description is blank.','blank', project.errors['description']
		assertEquals 'Supervisors is blank.','blank', project.errors['supervisors']
		assertEquals 'Owner is nullable.','nullable', project.errors['owner']

		project = new Project(projectTitle: 'Testing Project',
				researcherName: 'TestStudent' ,
				studentNumber: 'StudentNumber' ,
				degree: 'TestDegree',
				startDate: new Date(),
				endDate: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor',
				owner: user)

		assertTrue project.validate()
	}

	/**
	 * Test the nullable fields in the domain class {@link Project} are
	 * correctly validated
	 */
	void testNullable()
	{
		project = new Project(projectTitle: 'Testing Project',
				researcherName: 'TestStudent' ,
				studentNumber: 'TestNumber',
				degree: 'TestDegree',
				startDate: null,
				endDate: null,
				description: 'Test Description',
				supervisors: 'test supervisor',
				owner: user)

		assertFalse "No validation for nullable fields", project.validate()

		assertEquals 'Start Date is nullable.','nullable', project.errors['startDate']
		assertEquals 'End Date is nullable.','nullable', project.errors['endDate']

		project = new Project(projectTitle: 'Testing Project',
				researcherName: 'TestStudent' ,
				studentNumber: 'TestNumber',
				degree: 'TestDegree',
				startDate: new Date(),
				endDate: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor',
				owner: user)
                

		assertTrue project.validate()
	}
	
	/**
	* Test the range validation of fields in the domain class {@link Project} are
	* correctly validated
	*/
   void testRange()
   {
	   project = new Project(projectTitle: '012345678910' * 100,
			   researcherName: '012345678910' * 100 ,
			   studentNumber: '012345678910' * 100,
			   degree: '012345678910' * 100,
			   startDate: new Date(),
			   endDate: new Date(),
			   description: '012345678910' * 100,
			   supervisors: '012345678910' * 100, 
			   owner: user)

	   assertFalse "No validation for size of fields", project.validate()

	   assertEquals 'Project Title does not validate size.','size', project.errors['projectTitle']
	   assertEquals 'Researcher Name does not validate size.','size', project.errors['researcherName']
	   assertEquals 'Student Number does not validate size.','size', project.errors['studentNumber']
	   assertEquals 'Degree does not validate size.','size', project.errors['degree']
	   assertEquals 'Description  does not validate size.','size', project.errors['description']
	   assertEquals 'Supervisors  does not validate size.','size', project.errors['supervisors']
	   
	   project = new Project(projectTitle: 'Testing Project',
			   researcherName: 'TestStudent' ,
			   studentNumber: 'TestNumber',
			   degree: 'TestDegree',
			   startDate: new Date(),
			   endDate: new Date(),
			   description: 'Test Description',
			   supervisors: 'test supervisor',
			   owner: user)

	   assertTrue project.validate()
   }
   
	
	/**
	 * Test that the one to many relationship between project and study
	 * works correctly.
	 */
	void testProjectAndStudyRelationship()
	{
		assertTrue "Not enough studies have been returned.", project.studies?.size() > 0
	}
}
