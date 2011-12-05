Feature: Create Device Metadata Template
  In order manage my data
  As an Administrator
  I want to create a Device in a Device Grouping
 
 Background:
 Given I have logged in
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 
 Scenario: Create Device Metadata Template with static field
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "Force Platforms"
 Then I should see "Force Platforms"
 Then I should see "Add new device"
 Then I should see "Device1"
 Then I press "device-details[0]"
 Then I should see "Device1 Details Template"
 Then I press "Add Field"
 Then I should see "Add New Device1 Details Template Field"
 Then I fill in "fieldLabel" with "A header 123"
 Then I select radiobutton "STATIC_TEXT" from "fieldType"
 Then I fill in "staticContent" with "This is my static text"
 Then I press "save"
 Then I should see "saved"
 Then I should see "A header 123"
 
  
   