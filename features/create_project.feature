Feature: Create project
  In order manage my data
  As a researcher
  I want to create a project

  Scenario: Create project
    Given I have logged in as "researcher"
    Given I am on the create project page
    When I fill in "projectTitle" with "My Biomechanics Project"
    And I fill in "researcherName" with "researcher"
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
    Then I follow "My Biomechanics Project"
    Then I should see "My Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | My Biomechanics Project |
      | Researcher Name | researcher              |
      | Student Number  | 123456                  |
      | Degree          | Masters of Biomechanics |
      | Start Date      | 03/2011                 |
      | End Date        | 03/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |

	Then I press "Logout"
	Given I have logged in as "chrisk"
    Given I am on the create project page
    When I fill in "projectTitle" with "Chris Biomechanics Project"
    And I fill in "researcherName" with "chrisk"
    And I fill in "studentNumber" with "12345678"
    And I fill in "degree" with "Masters of Biomechanics"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "saved"
    Then I follow "Chris Biomechanics Project"
    Then I should see "Chris Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | Chris Biomechanics Project		|
      | Researcher Name | chrisk              				|
      | Student Number  | 12345678                  		|
      | Degree          | Masters of Biomechanics 			|
      | Start Date      | 03/2011                 			|
      | End Date        | 03/2011                 			|
      | Description     | Studying some stuff     			|
      | Supervisor(s)   | Alice Smith             			|
      
	Then I press "Logout"
	Given I have logged in as "sysadm"
    Given I am on the create project page
    When I fill in "projectTitle" with "Sysadm Biomechanics Project"
    And I fill in "researcherName" with "sysadm"
    And I fill in "studentNumber" with "123456789"
    And I fill in "degree" with "Masters of Biomechanics"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "saved"
    Then I follow "Sysadm Biomechanics Project"
    Then I should see "Sysadm Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | Sysadm Biomechanics Project		|
      | Researcher Name | sysadm              				|
      | Student Number  | 123456789                  		|
      | Degree          | Masters of Biomechanics 			|
      | Start Date      | 03/2011                 			|
      | End Date        | 03/2011                 			|
      | Description     | Studying some stuff     			|
      | Supervisor(s)   | Alice Smith             			|
      
	Then I press "Logout"
	Given I have logged in as "labman"
    Given I am on the create project page
    When I fill in "projectTitle" with "labman Biomechanics Project"
    And I fill in "researcherName" with "labman"
    And I fill in "studentNumber" with "1234567890"
    And I fill in "degree" with "Masters of Biomechanics"
    And I select "March" from "startDate_month"
    And I select "2011" from "startDate_year"
    And I select "March" from "endDate_month"
    And I select "2011" from "endDate_year"
    And I fill in "description" with "Studying some stuff"
    And I fill in "supervisors" with "Alice Smith"
    And I press "create"
    Then I should see "saved"
    Then I follow "labman Biomechanics Project"
    Then I should see "labman Biomechanics Project"
    And I should see table "projectTable" with contents
      | Project Title   | labman Biomechanics Project		|
      | Researcher Name | labman              				|
      | Student Number  | 1234567890                  		|
      | Degree          | Masters of Biomechanics 			|
      | Start Date      | 03/2011                 			|
      | End Date        | 03/2011                 			|
      | Description     | Studying some stuff     			|
      | Supervisor(s)   | Alice Smith             			|

	Then I press "Logout"
	Given I have logged in as "sysadm"
	Given I am on the home page
	And I press "all-projects"
	Then I should see "My Biomechanics Project"
    Then I follow "My Biomechanics Project"
    Then I should see "My Biomechanics Project owned by The Researcher"
    Then I follow "My Biomechanics Project owned by The Researcher"
    And I should see table "projectTable" with contents
      | Project Title   | My Biomechanics Project |
      | Researcher Name | researcher              |
      | Student Number  | 123456                  |
      | Degree          | Masters of Biomechanics |
      | Start Date      | 03/2011                 |
      | End Date        | 03/2011                 |
      | Description     | Studying some stuff     |
      | Supervisor(s)   | Alice Smith             |
      
	Then I press "Logout"
	Given I have logged in as "sysadm"
	Given I am on the home page
	And I press "all-projects"
    Then I should see "labman Biomechanics Project"
	Then I follow "labman Biomechanics Project"
    Then I should see "labman Biomechanics Project owned by Lab Manager Manager"
    Then I follow "labman Biomechanics Project owned by Lab Manager Manager"
    And I should see table "projectTable" with contents
      | Project Title   | labman Biomechanics Project  			|
      | Researcher Name | labman                               	|
      | Student Number  | 1234567890                			|
      | Degree          | Masters of Biomechanics              	|
      | Start Date      | 03/2011                              	|
      | End Date        | 03/2011                              	|
      | Description     | Studying some stuff                  	|
      | Supervisor(s)   | Alice Smith                          	|
      
	Then I press "Logout"
	Given I have logged in as "labman"
	Given I am on the home page
	And I press "all-projects"
	Then I should see "My Biomechanics Project"
	Then I follow "My Biomechanics Project"
    Then I should see "My Biomechanics Project owned by The Researcher"
    Then I follow "My Biomechanics Project owned by The Researcher"
    And I should see table "projectTable" with contents
		| Project Title   | My Biomechanics Project |
		| Researcher Name | researcher              |
		| Student Number  | 123456                  |
		| Degree          | Masters of Biomechanics |
		| Start Date      | 03/2011                 |
		| End Date        | 03/2011                 |
		| Description     | Studying some stuff     |
		| Supervisor(s)   | Alice Smith             |

      
	Then I press "Logout"
	Given I have logged in as "labman"
	Given I am on the home page
	And I press "all-projects"
	Then I should see "Sysadm Biomechanics Project"
	Then I follow "Sysadm Biomechanics Project"
    Then I should see "Sysadm Biomechanics Project owned by System Administrator"
    Then I follow "Sysadm Biomechanics Project owned by System Administrator"
	And I should see table "projectTable" with contents
		| Project Title   | Sysadm Biomechanics Project  		 |
		| Researcher Name | sysadm                               |
		| Student Number  | 123456789                    		 |
		| Degree          | Masters of Biomechanics              |
		| Start Date      | 03/2011                              |
		| End Date        | 03/2011                              |
		| Description     | Studying some stuff                  |
		| Supervisor(s)   | Alice Smith                          |
      
	Then I press "Logout"
	Given I have logged in as "researcher"
    Then I cannot follow "Chris Biomechanics Project"