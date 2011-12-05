Feature: Add 2 Collaborators to 2 separate studies in 2 separate projects
  As a project owner I see all studies
  In order to collaborate
  As a Researcher
  I want a researcher to only see those studies they collaborate on
 
 Background:
 Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "chrisk"
 Given I have created a collaborator study with "-1000", "-2000", "-6000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria", "researcher"
 Given I have created a project with "-1001", "My Other Biomechanics Project", "Jo Bloggs", "123", "Grad Dip Biomechanics", "2011-05-01 00:00:00", "2011-05-01 00:00:00", "Studying some stuff", "Alan Smith", "chrisk"
 Given I have created a collaborator study with "-1001", "-2001", "-6001", "My Other Biomechanics Study", "1073B", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-05-01 00:00:00", "2011-05-01 00:00:00", "10", "Test Criteria", "researcher1" 

 Scenario: View Collaborators of Studies I own
 Given I have logged in as "chrisk"
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Collaborators"
 Then I press "Add Collaborator"
 Then I should see "Sorry, you're not authorized to view this page."
 Given I am on the home page
 Then I follow "My Other Biomechanics Study"
 Then I follow "Collaborators"
 Then I press "Add Collaborator"
 Then I should see "Sorry, you're not authorized to view this page."
 Then I press "Logout"

 Given I have logged in as "researcher"
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I cannot follow "My Other Biomechanics Study"
 Then I press "Logout"
 
 Given I have logged in as "researcher1"
 Given I am on the home page
 Then I follow "My Other Biomechanics Study"
 Then I cannot follow "My Biomechanics Study"
 Then I press "Logout"