Feature: Edit Project
  In order manage my data
  As a researcher
  I want to edit a project

  Background:
    Given I have logged in as "dpollum"
    Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "dpollum"

   Scenario: Edit Project
   Given I am on the home page
   Then I follow "My Biomechanics Project"
   Then I should see "My Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | My Biomechanics Project |
      | Researcher Name | Fred Bloggs             |
      | Student Number  | 123456                  |
      | Degree          | Masters of Biomechanics |
      | Start Date      | 04/2011                 |
      | End Date        | 04/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |
    Then I press "edit"
    Then I should see "Edit Project"
    Then I should see "projectTitle" with value "My Biomechanics Project"
    Then I should see "researcherName" with value "Fred Bloggs"
    Then I should see "studentNumber" with value "123456"
    Then I should see "degree" with value "Masters of Biomechanics"
    Then I should see "startDate_month" selected with value "4"
    Then I should see "startDate_year" selected with value "2011"
    Then I should see "endDate_month" selected with value "4"
    Then I should see "endDate_year" selected with value "2011"
    Then I should see "description" with value "Studying some stuff"
    Then I should see "supervisors" with value "Alice Smith"
    
    When I press "cancel"
    Then I should see "My Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | My Biomechanics Project |
      | Researcher Name | Fred Bloggs             |
      | Student Number  | 123456                  |
      | Degree          | Masters of Biomechanics |
      | Start Date      | 04/2011                 |
      | End Date        | 04/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |       
    Then I press "edit"
    Then I should see "Edit Project"
    When I fill in "projectTitle" with " 1"
    Then I press "save"
    Then I should see "My Biomechanics Project 1"
    And I should see table "projectTable" with contents
      | Project Title   | My Biomechanics Project 1 |
      | Researcher Name | Fred Bloggs               |
      | Student Number  | 123456                    |
      | Degree          | Masters of Biomechanics   |
      | Start Date      | 04/2011                   |
      | End Date        | 04/2011                   |
      | Description     | Studying some stuff       |
      | Supervisor(s)   | Alice Smith               |
   