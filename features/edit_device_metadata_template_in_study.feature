Feature: Edit Device Metadata for Study
  In order manage my data
  As a Researcher
  I want to edit metadata for a device in a study
 
 Background:
 Given I have logged in as "labman"
 Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
 Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 
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
 Then I fill in "fieldLabel" with "Location of Strap"
 Then I select radiobutton "TEXTAREA" from "fieldType"
 Then I press "save"
 Then I should see "Location of Strap saved"
 
 Then I should see "Device1 Details Template"
 Then I press "Add Field"
 Then I should see "Add New Device1 Details Template Field"
 Then I fill in "fieldLabel" with "Select a Radio Button Option"
 Then I select radiobutton "RADIO_BUTTONS" from "fieldType"
 Then I fill in "fieldOptions" with "Option1"
 Then I fill in "fieldOptions" with enter
 Then I fill in "fieldOptions" with "Option2"
 Then I fill in "fieldOptions" with enter
 Then I press "save"
 Then I should see "Select a Radio Button Option saved"
 
 Then I should see "Device1 Details Template"
 Then I press "Add Field"
 Then I should see "Add New Device1 Details Template Field"
 Then I fill in "fieldLabel" with "Select a Drop Down Option"
 Then I select radiobutton "DROP_DOWN" from "fieldType"
 Then I fill in "fieldOptions" with "Option1"
 Then I fill in "fieldOptions" with enter
 Then I fill in "fieldOptions" with "Option2"
 Then I fill in "fieldOptions" with enter
 Then I press "save"
 Then I should see "Select a Drop Down Option saved"
 
 Given I am on the home page
 And I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "select_0"
 Then I fill in "studyDeviceFields[0].textArea" with "Some text"
 Then I select radiobutton "Option1" from "studyDeviceFields[1].radioButtonsOption"
 Then I select "Option1" from "studyDeviceFields[2].dropDownOption"
 Then I press "update"
 Then I should see "Devices"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 
 Scenario: Edit Device Metadata for Study
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I press "edit_0"
 Then I should see "Device1 Details Template Form"
 Then I fill in "studyDeviceFields[0].textArea" with ",Some more text"
 Then I press "update"
 Then I should see "Device Device1 updated"
 Then I press "edit_0"
 Then I should see ",Some more text"
 
 
