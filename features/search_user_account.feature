Feature: Search User Accounts
  In order manage my data
  As a System Administrator
  I want to search user accounts in LDAP

  Scenario: Search User Accounts
     Given I have logged in
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     |abe       	| John  		| Abe              	| Select  |
     |carlos		| Carlos		| Aya			   	| Select  |
     |chrisk    	| Chris 		| Kenward          	| Select  |
     |davidk    	| David 		| Kenward          	| Select  |
     |dpollum   	| David 		| Pollum           	| Select  |
     |jdoesmith 	| John  		| Doe-Smith        	| Select  |
     |johnk     	| John  		| Kenward          	| Select  |
     |johnma    	| John  		| Michael Anderson 	| Select  |
     |labman    	| Lab Manager  	| Manager   		| Select  |
     |researcher	| The		  	| Researcher		| Select  |
     |researcher1	| The		  	| Researcher1		| Select  |
     |sysadm		| System	  	| Administrator		| Select  |
     
     
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "create"
     Then I should see "New User Account"
     Then I fill in "surname" with "Kenward"
     Then I press "search"
     Then I should see a 4 column table "searchTable" with contents
     |chrisk | Chris | Kenward  | Select  |
     |davidk | David | Kenward  | Select  |
     |johnk  | John  | Kenward  | Select  |
     
     Then I press "select[1]"
     Then I should see "User role and identifier"
     Then I select "Researcher" from "authority"
     Then I fill in "nlaIdentifier" with "http://ands.org.au/abc"
     Then I fill in "title" with "Prof."
     Then I press "select"
     Then I should see "Confirm account creation"
     Then I press "Cancel"
     Then I should see a 4 column table "searchTable" with contents
     |chrisk | Chris | Kenward  | Select  |
     |davidk | David | Kenward  | Select  |
     |johnk  | John  | Kenward  | Select  |
     
     
      
     
     
     
     