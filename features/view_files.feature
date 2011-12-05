Feature: View Files
  In order manage my data
  As a researcher
  I want to view files uploaded for a study
  As a system administrator
  I cannot see files in a study unless I am a collaborator or an owner of the study
 
 Background:
    Given I have logged in as "labman"
 	Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
    Given I have created a project with "-1001", "My Other Biomechanics Project", "Joe Bloggs", "123", "B Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "sysadm"
	Given I have created a study with "-2001", "-1001", "My Other Biomechanics Study", "1073B", "No", "Test Description", "Partner1", "keyword too", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"

	Given I am on the home page
	And I follow "My Biomechanics Study"
	Then I follow "Files"
   
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Component"
    Then I follow "Add Component"
    Then I should see "Add New Component"
    And I fill in "name" with "TestComponent"
    And I fill in "description" with "Some Description"
    And I press "save"
    Then I should see "saved"
    Then I should see "TestComponent"

    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Session"
    Then I follow "Add Session"
    Then I should see "Add New Session"
    Then I fill in "name" with "TestSession"
    Then I fill in "description" with "Some Description"
    And I press "save"
    Then I should see "TestSession"  
    
	Given I am on the home page
	And I press "all-projects"
	Then I follow "My Other Biomechanics Project"
    And I follow "My Other Biomechanics Study"
	Then I follow "Components"
    Then I should see "Add Component"
    Then I follow "Add Component"
    Then I should see "Add New Component"
    And I fill in "name" with "TestComponent1"
    And I fill in "description" with "Some Description1"
    And I press "save"
    Then I should see "saved"
    Then I should see "TestComponent1"
    
    Then I follow "Components"
    Then I should see "Add Session"
    Then I follow "Add Session"
    Then I should see "Add New Session"
    Then I fill in "name" with "TestSession1"
    Then I fill in "description" with "Some Description1"
    And I press "save"
    Then I should see "TestSession1"

	Scenario: View Files
	Given I am on the home page
	And I follow "My Biomechanics Study"
	Then I follow "Files"
	Then I press "Logout"
 
	Given I have logged in as "sysadm"
	Given I am on the home page
	Then I follow "My Other Biomechanics Study"
	Then I follow "Files"
	
	Given I am on the home page
	And I press "all-projects"
	Then I follow "My Biomechanics Project"
	Then I follow "My Biomechanics Study"
	Then I follow "Files"
	Then I should see "Sorry, you're not authorized to view this page."
	
	Given I am on the home page
	And I press "all-projects"
	Then I follow "My Biomechanics Project"
	Then I follow "My Biomechanics Study"
	Then I follow "Collaborators"
	Then I press "Add Collaborator"
	Then I should see "Add Collaborator to study My Biomechanics Study"
	Then I fill in "firstName" with ""
	Then I fill in "surname" with ""
	Then I fill in "userid" with "sysadm"
	 Then I press "search"
	Then I should see a 4 column table "searchTable" with contents
	| sysadm | System | Administrator | Select |
	Then I press "select[0]"
	Then I should see "Collaborator sysadm added to study My Biomechanics Study"
	
	Given I am on the home page
	And I press "all-projects"
	Then I follow "My Biomechanics Project"
	Then I follow "My Biomechanics Study"
	Then I follow "Files"
	Then I press "Logout"