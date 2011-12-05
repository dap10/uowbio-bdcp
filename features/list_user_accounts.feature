Feature: List User Accounts
  In order manage my data
  As a System Administrator
  I want to list user accounts in DB

  Scenario: List User Accounts
     Given I have logged in
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I should see a 3 column table "listTable" with contents
     |Administrator	|System 		|sysadm		|
     |Kenward		|Chris			|chrisk 	|
     |Manager		|Lab Manager	|labman		|        
     |Pollum		|David			|dpollum	|
     |Researcher	|The	 		|researcher	|
     |Researcher1	|The	 		|researcher1|
     
     Then I press "Back"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I press "home"
     Then I should see "Welcome Researcher"
     
     Given I am on the home page
     Then I enable javascript
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I press "hideUsers"
     Then I should see a 3 column table "listTable" with contents
     |Administrator	|System 		|sysadm		|
     |Kenward		|Chris			|chrisk 	|
     |Manager		|Lab Manager	|labman		|        
     |Pollum		|David			|dpollum	|
     |Researcher	|The	 		|researcher	|
     |Researcher1	|The	 		|researcher1|
     Then I disable javascript
     
     
      
     
     
     
     