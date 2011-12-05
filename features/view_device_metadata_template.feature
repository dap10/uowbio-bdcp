Feature: View Device Metadata Template
  In order manage my data
  As an Administrator
  I want to View a Device in a Device Grouping
 
 Background:
 Given I have logged in
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 Given I have created a deviceField with "Device1", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "true", "Location of Strap", "TEXTAREA", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "true", "Select a Radio Button Option", "RADIO_BUTTONS", "Option1"
 Given I have created a deviceField with "Device1", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "true", "Select a Drop Down Option", "DROP_DOWN", "Option1"
 
 Scenario: View Device Metadata Template
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
 Then I press "show[1]"
 Then I should see table "fieldDetailsTable" with contents
      | Field Label   | Select a Radio Button Option |
      | Field Type    | Radio Buttons                |
      | Mandatory     | True                         |
      | Field Options | Option1                      |
 
 Then I press "Back"
 Then I should see "Device1 Details Template"
 Then I press "show[2]"
 Then I should see table "fieldDetailsTable" with contents
      | Field Label   | Select a Drop Down Option    |
      | Field Type    | Drop Down                    |
      | Mandatory     | True                         |
      | Field Options | Option1                      | 
   