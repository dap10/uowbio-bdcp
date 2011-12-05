  Feature: Edit User Account
  In order manage my data
  As a System Administrator
  I want to view a user accounts in DB

  Scenario: Edit User Account
     Given I have logged in
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I should see a 4 column table "listTable" with contents
     |Administrator	|System 		|sysadm		|Edit |
     |Kenward		|Chris			|chrisk 	|Edit |
     |Manager		|Lab Manager	|labman		|Edit |        
     |Pollum		|David			|dpollum	|Edit |
     |Researcher	|The	 		|researcher	|Edit |
     |Researcher1	|The	 		|researcher1|Edit |
     Then I press "edit[3]"
     Then I should see table "userDetailsTable" with contents
     |Firstname                 | David   									    |
     |Surname                   | Pollum 										|
     |User ID                   | dpollum										|
     |Role                      | Lab Manager System Administrator Researcher	|
     |NLA Persistence Identifier|                                               |
     |Title                     |                                               |
     |Deactivate User Account   |         									    |
     And I should have "http://ands.org.au/1234" in text field named "nlaIdentifier"  
     And I should have "Mr" in text field named "title"  
     And I select "Lab Manager" from "authority"
     Then I press "deactivated"
     Then I press "save"
     Then I should see words "dpollum deactivated successfully"
     Then I press "Logout"
   	 Then I should see "Please enter your userid and password to login"
     Then I fill in "j_username" with "dpollum"
   	 Then I fill in "j_password" with "password"
   	 Then I press "Login"
   	 Then I should see "Please enter your userid and password to login"
   	 
   	 Given I have logged in
   	 Given I am on the home page
   	 And I press "system-administration" 
   	 And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I should see a 4 column table "listTable" with contents
     |Administrator	|System 		|sysadm		|Edit |
     |Kenward		|Chris			|chrisk 	|Edit |
     |Manager		|Lab Manager	|labman		|Edit |        
     |Pollum		|David			|dpollum	|Edit |
     |Researcher	|The	 		|researcher	|Edit |
	 |Researcher1	|The	 		|researcher1|Edit |
     Then I press "edit[3]"
     And I select "Researcher" from "authority"
     Then I press "deactivated"
     Then I press "save"
     Then I should see words "dpollum activated successfully"
     Then I press "Logout"
     Then I fill in "j_username" with "dpollum"
   	 Then I fill in "j_password" with "password"
     Then I press "Login"
     Then I should see "Welcome Researcher"
	 Given I am on the home page
   	 And I press "system-administration" 
   	 Then I should see "Sorry, you're not authorized to view this page."

  Scenario: Edit User Account
     Given I have logged in
     Given I am on the home page
     And I press "system-administration"
     And I press "account-administration"
     Then I should see "Account Administration"
     Then I press "list"
     Then I should see "List All Users"
     Then I should see a 4 column table "listTable" with contents
     |Administrator	|System 		|sysadm		|Edit |
     |Kenward		|Chris			|chrisk 	|Edit |
     |Manager		|Lab Manager	|labman		|Edit |        
     |Pollum		|David			|dpollum	|Edit |
     |Researcher	|The	 		|researcher	|Edit |
	 |Researcher1	|The	 		|researcher1|Edit |
     Then I press "edit[3]"
     Then I should see table "userDetailsTable" with contents
     |Firstname                 | David   									    |
     |Surname                   | Pollum 										|
     |User ID                   | dpollum										|
     |Role                      | Lab Manager System Administrator Researcher	|
     |NLA Persistence Identifier|                                               |
     |Title                     |                                               |
     |Deactivate User Account   |         									    |
     And I should have "http://ands.org.au/1234" in text field named "nlaIdentifier"  
     And I should have "Mr" in text field named "title"  
     Then I fill in "nlaIdentifier" with "NEW_IDENTIFIER"
     Then I fill in "title" with "New Title"
     Then I press "save"
     Then I should see words "dpollum updated successfully"
     Then I press "edit[3]"
     Then I should see table "userDetailsTable" with contents
     |Firstname                 | David   									    |
     |Surname                   | Pollum 										|
     |User ID                   | dpollum										|
     |Role                      | Lab Manager System Administrator Researcher	|
     |NLA Persistence Identifier|                                               |
     |Title                     |                                               |
     |Deactivate User Account   |         									    |
     And I should have "NEW_IDENTIFIER" in text field named "nlaIdentifier"  
     And I should have "New Title" in text field named "title"  
     Then I fill in "nlaIdentifier" with "http://ands.org.au/1234"
     Then I press "save"
     Then I should see words "dpollum updated successfully"
   	 