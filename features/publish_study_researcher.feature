Feature: Publish Study
  In order to advertise my data
  As a researcher
  I want to mark as study as public
 
 Background:
    Given I have logged in as "researcher"
 	Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "researcher"
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
  Then I cannot press "Publish"
  Then I press "Logout"
  
     