Feature: As a Lab Manager or System Administrator I Create a project for a researcher 
  In order to manage my data as a researcher
  I want a Lab Manager or System Administrator to create a project for them

  Scenario: Create project
	Given I have logged in as "labman"
	Given I am on the home page
	And I press "all-projects"
	Then I should see "Add Project"
	Then I follow "Add Project"
	Then I press "Back"
	Then I should see "Add Project"
	Then I follow "Add Project"
	Then I should see "Select researcher to create project for"
    Then I fill in "firstName" with ""
    Then I fill in "surname" with ""
    Then I fill in "userid" with "res"
    Then I press "search"
    Then I should see a 4 column table "searchTable" with contents
     |researcher  |The | Researcher  | Select |
     |researcher1 |The | Researcher1 | Select |
    Then I press "select[0]"
    Then I should see "Add New Project for researcher"
    When I fill in "projectTitle" with "Researcher Biomechanics Project"
    And I fill in "researcherName" with "The researcher"
    And I fill in "studentNumber" with "1234567890"
    And I fill in "degree" with "B Biomech"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "saved"
	Then I press "Logout"
      
	Given I have logged in as "researcher"
	Given I am on the home page
    Then I follow "Researcher Biomechanics Project"
    Then I should see "Researcher Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | Researcher Biomechanics Project |
      | Researcher Name | The researcher                  |
      | Student Number  | 1234567890                      |
      | Degree          | B Biomech						  |
      | Start Date      | 03/2011                 		  |
      | End Date        | 03/2011                 		  |
      | Description     | Studying some stuff     		  |
      | Supervisor(s)   | Alice Smith             		  |
	Then I press "Logout"
	
	Given I have logged in as "researcher1"
    Then I cannot follow "Researcher Biomechanics Project"
    Then I press "Logout"
    
    Given I have logged in as "labman"
    Given I am on the home page
    Then I cannot follow "Researcher Biomechanics Project"
	And I press "all-projects"
	Then I should see "Researcher Biomechanics Project"
	Then I press "Logout"
	
	Given I have logged in as "sysadm"
    Given I am on the home page
    Then I cannot follow "Researcher Biomechanics Project"
	And I press "all-projects"
	Then I should see "Researcher Biomechanics Project"
	Then I press "Logout"
	
	Given I have logged in as "sysadm"
	Given I am on the home page
	And I press "all-projects"
	Then I should see "Add Project"
	Then I follow "Add Project"
	Then I should see "Select researcher to create project for"
    Then I fill in "firstName" with ""
    Then I fill in "surname" with ""
    Then I fill in "userid" with "res"
    Then I press "search"
    Then I should see a 4 column table "searchTable" with contents
     |researcher  |The | Researcher  | Select |
     |researcher1 |The | Researcher1 | Select |
    Then I press "select[1]"
    Then I should see "Add New Project for researcher1"
    When I fill in "projectTitle" with "Researcher1 Biomechanics Project"
    And I fill in "researcherName" with "The researcher1"
    And I fill in "studentNumber" with "1234567890"
    And I fill in "degree" with "B Biomech"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "saved"
	Then I press "Logout"
      
	Given I have logged in as "researcher1"
	Given I am on the home page
    Then I follow "Researcher1 Biomechanics Project"
    Then I should see "Researcher1 Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | Researcher1 Biomechanics Project |
      | Researcher Name | The researcher1                  |
      | Student Number  | 1234567890                       |
      | Degree          | B Biomech			   			   |
      | Start Date      | 03/2011                 		   |
      | End Date        | 03/2011                 		   |
      | Description     | Studying some stuff     		   |
      | Supervisor(s)   | Alice Smith             		   |
	Then I press "Logout"
	
	Given I have logged in as "researcher1"
	Given I am on the home page
    Then I follow "Researcher1 Biomechanics Project"
    Then I should see "Researcher1 Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | Researcher1 Biomechanics Project |
      | Researcher Name | The researcher1                  |
      | Student Number  | 1234567890                      |
      | Degree          | B Biomech						  |
      | Start Date      | 03/2011                 		  |
      | End Date        | 03/2011                 		  |
      | Description     | Studying some stuff     		  |
      | Supervisor(s)   | Alice Smith             		  |
	Then I press "Logout"
	
	Given I have logged in as "researcher"
    Then I cannot follow "Researcher1 Biomechanics Project"
    Then I press "Logout"
    