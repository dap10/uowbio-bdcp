 Feature: Add Files
  In order manage my data
  As a researcher
  I want to upload files for a particular session and particular study
 
 Background:
    Given I have logged in as "labman"
    Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
    Given I have created a component with "TestComponent", "Some Description"
    Given I have created a session with "TestSession", "Some Description"
    
	Scenario: Add Files
	Given I am on the home page
	And I press "all-projects"
	Then I follow "My Biomechanics Project"
	And I follow "My Biomechanics Study"
	Then I follow "Files"
	
	Then I press "Logout"
	Given I have logged in as "sysadm"
	Given I am on the home page
	And I press "all-projects"
	Then I follow "My Biomechanics Project"
	And I follow "My Biomechanics Study"
	Then I follow "Files"
    Then I should see "Sorry, you're not authorized to view this page."
	