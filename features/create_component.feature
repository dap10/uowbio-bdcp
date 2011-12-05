Feature: Create Component
  In order manage my data
  As a researcher
  I want to create components for a study
 
 Background:
    Given I have logged in as "labman"
 	Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
    Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
   
  Scenario: Create Component
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Component"
    Then I follow "Add Component"
    Then I should see "Add New Component"
    And I fill in "name" with "TestComponent"
    And I fill in "description" with "Some Description"
    And I press "save"
    Then I should see "saved"
    Then I should see "TestComponent"
    
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Component"
    Then I follow "Add Component"
    Then I should see "Add New Component"
    Then I press "cancel"
    Then I should see "My Biomechanics Study"
    Then I should see "Add Component"
    
    Given I am on the home page
    And I follow "My Biomechanics Study"
    Then I follow "Components"
    Then I should see "Add Component"
    Then I follow "Add Component"
    Then I should see "Add New Component"
    Then I fill in "description" with ""
    Then I fill in "name" with ""
    And I press "save"
    Then I should see "Please provide a description for the component"
    Then I should see "Please provide a name for the component"
    Then I fill in "description" with "My Description"
    Then I fill in "name" with "Test Component 2"
    Then I press "save"
    Then I should see "My Biomechanics Study"
    Then I should see "Add Component"
	Then I should see "Test Component 2"      
  