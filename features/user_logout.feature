Feature: User Logout
  In order manage my data
  As a researcher
  I want to logout of the system

  Background:
    
 
   Scenario: User Logout
   Given I am on the home page
   Then I should see "Please enter your userid and password to login"
   Then I fill in "j_username" with "chrisk"
   Then I fill in "j_password" with "password"
   Then I press "Login"
   Then I should see "Welcome Researcher"
   Then I press "Logout"
   Then I should see "Please enter your userid and password to login"
   When I press browser back button
   Then I should see "Please enter your userid and password to login"
   