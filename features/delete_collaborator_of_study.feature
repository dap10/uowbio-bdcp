Feature: Delete Collaborator of Study
  In order to cease a collaboration
  As a Researcher
  I want collaborators removed from my study by a Lab Manager
 
 Background:
 Given I have logged in as "labman"
 Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "chrisk"
 Given I have created a collaborator study with "-1000", "-2000", "-6000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria", "researcher"
 
 Scenario: Delete Collaborator from Study
 Given I am on the home page
 And I press "all-projects"
 Then I follow "My Biomechanics Project"
 Then I follow "My Biomechanics Study"
 Then I follow "Collaborators"
 Then I press "Add Collaborator"
 Then I should see "Add Collaborator to study My Biomechanics Study"
 Then I fill in "firstName" with ""
 Then I fill in "surname" with "Researcher1"
 Then I fill in "userid" with ""
 Then I press "search"
 Then I should see a 4 column table "searchTable" with contents
 | researcher1 | The | Researcher1 | Select |
 Then I press "select[0]"
 Then I should see "Collaborator researcher1 added to study My Biomechanics Study"
 Then I press "delete[0]"
 Then I should see "Collaborators"
 Then I press "delete[0]"
 Then I should see "Collaborators"
 Then I press "Logout"
   	 
 Given I have logged in as "researcher"
 Given I am on the home page
 Then I cannot follow "My Biomechanics Study"
 Then I press "Logout"
 
 Given I have logged in as "researcher1"
 Given I am on the home page
 Then I cannot follow "My Biomechanics Study"
 Then I press "Logout"
