Feature: Add Collaborator who is researcher to study
  As a project owner I see all studies
  As a Researcher
  I want a researcher to only see those studies they collaborate on
 
 Background:
 Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "chrisk"
 Given I have created a collaborator study with "-1000", "-2000", "-6000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria", "researcher"

 Scenario: View Study features
 Given I have logged in as "researcher"
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I press "Logout"
 
 Given I have logged in as "chrisk"
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I press "Logout"
 
 Given I have logged in as "researcher"
 Given I am on the show study page with "-2000", "-1000"
 Then I press "edit"
 Then I press "Logout"
 
 Given I have logged in as "chrisk"
 Given I am on the show study page with "-2000", "-1000"
 Then I press "edit"
 Then I press "Logout"
 
 Given I have logged in as "researcher1"
 Given I am on the home page
 Then I cannot follow "My Biomechanics Study"
 Given I am on the show study page with "-2000", "-1000"
 Then I should see "Sorry, you're not authorized to view this page."
 Then I press "Logout"
 
 Given I have logged in as "researcher"
 Given I am on the list participants page for study "-2000"
 Then I should see "Add Participant"
 Then I press "Logout"
 
 Given I have logged in as "chrisk"
 Given I am on the list participants page for study "-2000"
 Then I should see "Add Participant"
 Then I press "Logout"
 
 Given I have logged in as "researcher1"
 Given I am on the home page
 Then I cannot follow "My Biomechanics Study"
 Given I am on the list participants page for study "-2000"
 Then I should see "Sorry, you're not authorized to view this page."
 Then I press "Logout"
 
 Given I have logged in as "researcher"
 Given I am on the list components page for study "-2000"
 Then I should see "Add Component"
 Then I press "Logout"
 
 Given I have logged in as "chrisk"
 Given I am on the list components page for study "-2000"
 Then I should see "Add Component"
 Then I press "Logout"
 
 Given I have logged in as "researcher1"
 Given I am on the home page
 Then I cannot follow "My Biomechanics Study"
 Given I am on the list components page for study "-2000"
 Then I should see "Sorry, you're not authorized to view this page."
 Then I press "Logout"
 
 Given I have logged in as "researcher"
 Given I am on the list components page for study "-2000"
 Then I should see "Add Component"
 Then I press "Logout"
 
 Given I have logged in as "chrisk"
 Given I am on the list components page for study "-2000"
 Then I should see "Add Component"
 Then I press "Logout"
 
 Given I have logged in as "researcher1"
 Given I am on the home page
 Then I cannot follow "My Biomechanics Study"
 Given I am on the list components page for study "-2000"
 Then I should see "Sorry, you're not authorized to view this page."
 Then I press "Logout"
 
 Given I have logged in as "researcher"
 Given I am on the list devices page for study "-2000"
 Then I should see "Add Device"
 Then I press "Logout"
 
 Given I have logged in as "chrisk"
 Given I am on the list devices page for study "-2000"
 Then I should see "Add Device"
 Then I press "Logout"
 
 Given I have logged in as "researcher1"
 Given I am on the home page
 Then I cannot follow "My Biomechanics Study"
 Given I am on the list devices page for study "-2000"
 Then I should see "Sorry, you're not authorized to view this page."
 Then I press "Logout"
 
 Given I have logged in as "researcher"
 Given I am on the list collaborators page for study "-2000"
 Then I should see "Add Collaborator"
 Then I press "Logout"
 
 Given I have logged in as "chrisk"
 Given I am on the list collaborators page for study "-2000"
 Then I should see "Add Collaborator"
 Then I press "Logout"
 
 Given I have logged in as "researcher1"
 Given I am on the home page
 Then I cannot follow "My Biomechanics Study"
 Given I am on the list collaborators page for study "-2000"
 Then I should see "Sorry, you're not authorized to view this page."
 Then I press "Logout"
