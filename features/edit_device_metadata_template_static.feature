Feature: Create Device Metadata Template
  In order manage my device templates
  As an Administrator
  I want to edit a Device Field in a Device
 
 Background:
 Given I have logged in
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 Given I have created a device field with "Header 123", "STATIC_TEXT", "A text here", "false" for "Device1"
 Given I have created a device field with "Sample size", "TEXTAREA", "", "false" for "Device1"
 Given I have created a device field with "Header 456", "STATIC_TEXT", "This is new static", "false" for "Device1"
 
 Scenario: Edit Device Metadata Template static field
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "Force Platforms"
 Then I should see "Force Platforms"
 And I should see "Device1"
 Then I press "device-details[0]"
 Then I should see "Device1 Details Template"
 Then I should see table "listTable" with contents
 |Header 123  |Static text Edit |        
 |Sample size |Text Area        |
 |Header 456  |Static text Edit |
 
 And There must be an ID "edit_0"
 And There must be an ID "edit_2"
 When I press "edit_2"
 Then I should see "Edit Device1"        
 And I should have "Header 456" in text field named "fieldLabel"
 And I should see "This is new static"        
  
   