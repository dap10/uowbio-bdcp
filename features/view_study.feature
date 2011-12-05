Feature: View Study
  In order manage my data
  As a researcher
  I want to view a study
 
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
    
    Given I am on the home page
    And I follow "Add Study"
    When I fill in "studyTitle" with "Researcher Biomechanics Study"
    And I fill in "uowEthicsNumber" with "1073A"
    And I fill in "description" with "Test Description"
    And I fill in "industryPartners" with "Partner1"
    And I fill in "keywords" with "some wonderful keywords"
    And I fill in "collaborators" with "Collaborator1"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    
    And I fill in "numberOfParticipants" with "10"
    And I fill in "inclusionExclusionCriteria" with "Test Criteria"
    And I press "create"
    Then I should see "saved"
    Then I should see "Researcher Biomechanics Study"
    And I should see table "studyTable" with contents
      | Study Title                    | Researcher Biomechanics Study   |
      | UOW Ethics Number              | 1073A                   |
      | Additional Ethics Requirements | No                      |
      | Description                    | Test Description        |
      | Industry Partners              | Partner1                |
      | Subjects                       | some wonderful keywords |
      | Collaborators                  | Collaborator1           |
      | Start Date                     | 03/2011                 |
      | End Date                       | 03/2011                 |
      | Number of Participants         | 10                      |
      | Inclusion Exclusion Criteria   | Test Criteria           |
   
  Scenario: View Study
  Given I am on the home page
  And I follow "Researcher Biomechanics Study"
  And I should see table "studyTable" with contents
      | Study Title                    | Researcher Biomechanics Study   |
      | UOW Ethics Number              | 1073A                   |
      | Additional Ethics Requirements | No                      |
      | Description                    | Test Description        |
      | Industry Partners              | Partner1                |
      | Subjects                       | some wonderful keywords |
      | Collaborators                  | Collaborator1           |
      | Start Date                     | 03/2011                 |
      | End Date                       | 03/2011                 |
      | Number of Participants         | 10                      |
      | Inclusion Exclusion Criteria   | Test Criteria           |
   Then I follow "Project List"
   Then I should see "Researcher Biomechanics Project"
   
   Given I am on the home page
   And I follow "Researcher Biomechanics Study"
   Then I press "edit"
   Then I should see "Edit Study"
   Then I should see "studyTitle" with value "Researcher Biomechanics Study"
    Then I should see "uowEthicsNumber" with value "1073A"
    Then I should see "description" with value "Test Description"
    Then I should see "industryPartners" with value "Partner1"
    Then I should see "keywords" with value "some wonderful keywords"
    Then I should see "collaborators" with value "Collaborator1"
    Then I should see "startDate_month" selected with value "3"
    Then I should see "startDate_year" selected with value "2011"
    Then I should see "endDate_month" selected with value "3"
    Then I should see "endDate_year" selected with value "2011"
    Then I should see "numberOfParticipants" with value "10"
    Then I should see "inclusionExclusionCriteria" with value "Test Criteria"
    
   Given I am on the home page
   And I follow "Researcher Biomechanics Study"
   Then I follow "Participants"
   Then I should see "Add Participant" 
    
   Given I am on the home page
   And I follow "Researcher Biomechanics Study"
   Then I follow "Components"
   Then I should see "Add Component"
    
   Given I am on the home page
   And I follow "Researcher Biomechanics Study"
   Then I follow "Collaborators"
   Then I should see "Add Collaborator"
   Then I press "Logout"
   
   Given I have logged in as "labman"
   Given I am on the home page
   Then I cannot follow "Researcher Biomechanics Project"
   And I press "all-projects"
   Then I follow "Researcher Biomechanics Project"
   Then I should see "Researcher Biomechanics Project owned by The Researcher"
   Then I should see "Researcher Biomechanics Study"
   Then I press "Logout"
   
   Given I have logged in as "sysadm"
   Given I am on the home page
   Then I cannot follow "Researcher Biomechanics Project"
   And I press "all-projects"
   Then I follow "Researcher Biomechanics Project"
   Then I should see "Researcher Biomechanics Project owned by The Researcher"
   Then I should see "Researcher Biomechanics Study"
   Then I press "Logout"
   
   Given I have logged in as "researcher1"
   Given I am on the home page
   Then I cannot follow "Researcher Biomechanics Project"
   And I press "all-projects"
   Then I should see "Sorry, you're not authorized to view this page."
   Then I press "Logout"
