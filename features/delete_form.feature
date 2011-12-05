Feature: Delete Form
  In order manage my data
  As a researcher
  I want to delete a form to from a participant
 
 Background:
    Given I have logged in as "labman"
 	Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
   
    Given I am on the home page
    Then I enable javascript
    And I follow "My Biomechanics Study"
    Then I follow "Participants"
    Then I should see "Add Participant"
    Then I follow "Add Participant"
    Then I should see "Add New Participant"
    And I fill in "identifier" with "101"
    And I press "save"
    Then I should see "saved"
    Then I should see "101"
    
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Participants"
    Then I should see "Add Participant"
    Then I should see "101"
    Then I press "forms[0]"
    Then I should see "Participant 101"
    Then I should see "Forms"
    
    Scenario: Delete Form
