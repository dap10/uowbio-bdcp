Feature: Edit Device Grouping
  In order manage my data
  As an Administrator
  I want to edit a Device Grouping
 
 Background:
 Given I have logged in
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "createGrouping"
 Then I should see "Add New Device Grouping"
 Then I fill in "groupingName" with "Force Platforms"
 Then I press "create"
 Then I should see "Device Grouping Force Platforms saved"
 Then I should see "Force Platforms"
 
 
 Scenario: Edit Device Grouping
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "edit-name[0]"
 Then I should see "Edit Device Grouping"
 Then I fill in "groupingName" with " 1"
 Then I press "save"
 Then I should see "Device Grouping Force Platforms 1 updated"
 Then I should see "Force Platforms 1"
   