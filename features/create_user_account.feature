Feature: Create User Account
  In order manage my data
  As a System Administrator
  I want to create a user account

  Scenario: Create User Account
    Given I have logged in as "sysadm"
    Given I am on the home page
    And I press "system-administration"
    And I press "account-administration"
    And I should see "Account Administration"
    And I press "Back"
    Then I should see "System Administration"
    
    Given I am on the home page
    And I press "system-administration"
    And I press "account-administration"
    Then I should see "Account Administration"
    Then I press "create"
    Then I should see "New User Account"
    And I press "Back"
    Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I press "cancel"
     Then I should see "Account Administration"
     
     
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I fill in "firstName" with "David"
     Then I fill in "surname" with ""
     Then I fill in "userid" with ""
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     |davidk  |David |Kenward|Select |
     |dpollum |David |Pollum |Select |
     
	 Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I fill in "userid" with "johnk"
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     |johnk  |John      | Kenward  | Select |
     Then I press "select[0]"
     Then I should see "nlaIdentifier" with value ""
     Then I should see "authority" selected with value ""
     Then I press "Back"
     Then I should see "New User Account"
     
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Given I have cleared and filled in "firstName" with ""
     Given I have cleared and filled in "surname" with ""
     Given I have cleared and filled in "userid" with "johnk"
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     | johnk  | John | Kenward | Select |
     Then I press "select[0]"
     Then I should see "nlaIdentifier" with value ""
     Then I should see "authority" selected with value ""
     Then I select "Researcher" from "authority"
     Then I fill in "title" with "Mr"
     Then I press "select"
     Then I should see "Confirm account creation"
     Then I press "Confirm"
     Then I should see "Account Creation Successful"
     Then I press "accountAdmin"
     Then I should see "Account Administration"
     Given I am on the email page
     Then I should see "New Biomechanics Data Capture System Account for johnk"
     
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I fill in "userid" with "johnk"
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     |johnk |John |Kenward  |Select  |
     Then I press "select[0]"
     Then I should see "nlaIdentifier" with value ""
     Then I should see "authority" selected with value ""
     Then I select "Researcher" from "authority"
     Then I fill in "title" with "Prof"
     Then I press "select"
     Then I should see "User ID is not unique."
     Then I press "Back"
     Then I should see a 4 column table "searchTable" with contents
     |johnk |John |Kenward |Select |