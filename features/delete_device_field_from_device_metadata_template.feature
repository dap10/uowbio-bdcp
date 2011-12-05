Feature: Delete Device Field From Device Metadata Template
  In order manage my data
  As an Administrator
  I want to Delete a Device Field from a Device
 
 Background:
 Given I have logged in
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 Given I have created a deviceField with "Device1", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "true", "Location of Strap", "TEXTAREA", ""
 
 Scenario: Delete Device Field From Device Metadata Template
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
 Then I should see "Location of Strap"
 Then I press "delete[0]"
 Then I should see "Device field - Location of Strap deleted"
 Then I press "Logout"