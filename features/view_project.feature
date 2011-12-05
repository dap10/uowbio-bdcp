Feature: View Project
  In order manage my data
  As a researcher
  I want to view a project

  Background:
    Given I have logged in as "researcher"
    Given I am on the create project page
    When I fill in "projectTitle" with "Researcher Biomechanics Project"
    And I fill in "researcherName" with "Fred Bloggs"
    And I fill in "studentNumber" with "123456"
    And I fill in "degree" with "Masters of Biomechanics"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "saved"
    Then I follow "Researcher Biomechanics Project"
    Then I should see "Researcher Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | Researcher Biomechanics Project |
      | Researcher Name | Fred Bloggs             |
      | Student Number  | 123456                  |
      | Degree          | Masters of Biomechanics |
      | Start Date      | 03/2011                 |
      | End Date        | 03/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |
   

   Scenario: View Project
   Given I am on the home page
   Then I follow "Researcher Biomechanics Project"
   Then I should see "Researcher Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | Researcher Biomechanics Project |
      | Researcher Name | Fred Bloggs             |
      | Student Number  | 123456                  |
      | Degree          | Masters of Biomechanics |
      | Start Date      | 03/2011                 |
      | End Date        | 03/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |
   

   Then I press "Logout"
   Given I have logged in as "researcher1"
   Then I cannot follow "Researcher Biomechanics Project"
   
   Then I press "Logout"
   Given I have logged in as "labman"
   Given I am on the home page
   Then I cannot follow "Researcher Biomechanics Project"
   And I press "all-projects"
   Then I follow "Researcher Biomechanics Project"
   Then I should see "Researcher Biomechanics Project owned by The Researcher"
   And I press "Back"
   Then I should see "Researcher Biomechanics Project"
   Then I follow "Researcher Biomechanics Project"
   Then I should see "Researcher Biomechanics Project owned by The Researcher"
   Then I follow "Researcher Biomechanics Project owned by The Researcher"
   Then I should see "Researcher Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | Researcher Biomechanics Project |
      | Researcher Name | Fred Bloggs             |
      | Student Number  | 123456                  |
      | Degree          | Masters of Biomechanics |
      | Start Date      | 03/2011                 |
      | End Date        | 03/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |
   
   Then I press "Logout"
   Given I have logged in as "sysadm"
   Given I am on the home page
   Then I cannot follow "Researcher Biomechanics Project"
   And I press "all-projects"
   Then I follow "Researcher Biomechanics Project"
   Then I should see "Researcher Biomechanics Project owned by The Researcher"
   
   