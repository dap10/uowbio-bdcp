Feature: Publish Study
  In order to advertise my data
  As a researcher
  I want to mark as study as public
 
 Background:
    Given I have logged in as "labman"
 	Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
   
  Scenario: Publish Study
  Given I am on the home page
  And I enable javascript
  And I follow "My Biomechanics Study"
  Then I should see table "studyTable" with contents
      | Study Title                    | My Biomechanics Study   |
      | UOW Ethics Number              | 1073A                   |
      | Additional Ethics Requirements | No                      |
      | Description                    | Test Description        |
      | Industry Partners              | Partner1                |
      | Subjects                       | keyword                 |
      | Collaborators                  | Collaborator1           |
      | Start Date                     | 04/2011                 |
      | End Date                       | 04/2011                 |
      | Number of Participants         | 10                      |
      | Inclusion Exclusion Criteria   | Test Criteria           |
  And There must be an ID "publishDialog" which is hidden
  And There must be an ID "publishRightsDialog" which is hidden
  And I should see "Publish"
  
  Given I press "publishButton"
  Then I wait for ajax
  Then There must be an ID "publishDialog" which is visible
  And I should see in "publishDialog" phrase "My Biomechanics Study" 
  Then I press "Logout"
     