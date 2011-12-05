Feature: Create Directory
  In order manage my data
  As a researcher
  I want to create a directory for a particular session and particular study
 
 Background:
    Given I have logged in as "labman"
    Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
    Given the file service folder "files/sessions" is empty
    
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I press "createComponent"
    Then I should see "Add New Component"
    Then I fill in "name" with "TestComponent"
    Then I fill in "description" with "Some Description"
    Then I press "save"
    Then I should see "TestComponent"
    Then I should see "Components"
    
    Then I press "createSession[0]"
    Then I should see "Add New Session"
    Then I fill in "name" with "TestSession"
    Then I fill in "description" with "Some Description"
    Then I press "save"
    Then I should see "TestSession"
    Then I should see "Components"
    
	Scenario: Create Directory
	Given I am on the home page
	And I follow "My Biomechanics Study"
	Then I follow "Files"

	