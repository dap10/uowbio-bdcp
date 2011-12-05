Feature: View Collaborators of Study
  In order to collaborate
  As a Researcher
  I want to view who collaborates on my study
 
 Background:
 Given I have logged in as "labman"
 Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "chrisk"
 Given I have created a collaborator study with "-1000", "-2000", "-6000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria", "researcher"
 
 Scenario: View Collaborators of Study
 Given I am on the home page
 And I press "all-projects"
 Then I follow "My Biomechanics Project"
 Then I follow "My Biomechanics Study"
 Then I follow "Collaborators"
 Then I should see "Add Collaborator"
 Then I should see "researcher"
 Then I press "Logout"
   	 
 Given I have logged in as "chrisk"
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Collaborators"
 Then I should see "Add Collaborator"
 Then I should see "researcher"