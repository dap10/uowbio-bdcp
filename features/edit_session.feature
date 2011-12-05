Feature: Edit Session
  In order manage my data
  As a researcher
  I want to edit a session for a study
 
 Background:
    Given I have logged in as "labman"
 	Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
   
   
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

	Scenario: Edit Session
	Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Session"
    Then I should see "TestSession"
    Then I press "edit-session[0]"
    Then I should see "Edit Session"
    Then I press "cancel"
    Then I should see "Add Session"
    Then I should see "TestSession"
    
    Then I press "edit-session[0]"
    Then I should see "Edit Session"
    And I fill in "name" with "1"
    And I press "save"
    Then I should see "updated"
    Then I should see "Add Session"
    Then I should see "TestSession1"
    
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Session"
    Then I should see "TestSession"
    Then I press "edit-session[0]"
    Then I should see "Edit Session"
    Then I clear "name"
    Then I clear "description"
    Then I press "save"
    Then I should see "Please provide a description for the session"
    Then I should see "Please provide a name for the session"
    Then I press "cancel"
    Then I should see "Add Session"     
  