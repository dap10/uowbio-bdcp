Feature: Create Device Grouping
  In order manage my data
  As an Administrator
  I want to create a Device Grouping
 
 Scenario: Create Device Grouping
 Given I have logged in
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 And I press "Back"
 Then I should see "Welcome Researcher"
 Then I press "system-administration"
 Then I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "Back"
 Then I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "createGrouping"
 Then I should see "Add New Device Grouping"
 Then I fill in "groupingName" with "Force Platforms"
 Then I press "create"
 Then I should see "Device Grouping Force Platforms saved"
 Then I should see "Force Platforms"
   