Feature: Create Device Metadata Template
  In order manage my data
  As an Administrator
  I want to create a Device in a Device Grouping
 
 Background:
 Given I have logged in
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 
 Scenario: Create Device Metadata Template
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
  
 Then I should see "Device1 Details Template"
 Then I press "Add Field"
 Then I should see "Add New Device1 Details Template Field"
 Then I fill in "fieldLabel" with "Select a numeric field"
 Then I select radiobutton "NUMERIC" from "fieldType"
 Then I press "mandatory"
 Then I press "save"
 Then I should see "Select a numeric field saved"
 
 Then I should see "Device1 Details Template"
 Then I press "Add Field"
 Then I should see "Add New Device1 Details Template Field"
 Then I fill in "fieldLabel" with "Select a date field"
 Then I select radiobutton "DATE" from "fieldType"
 Then I press "save"
 Then I should see "Select a date field saved"
   
 Then I should see "Device1 Details Template"
 Then I press "Add Field"
 Then I should see "Add New Device1 Details Template Field"
 Then I fill in "fieldLabel" with "Select a time field"
 Then I select radiobutton "TIME" from "fieldType"
 Then I press "save"
 Then I should see "Select a time field saved"
 
 Then I should see "Device1 Details Template"
 Then I press "Add Field"
 Then I should see "Add New Device1 Details Template Field"
 Then I fill in "fieldLabel" with "Select a text field"
 Then I select radiobutton "TEXT" from "fieldType"
 Then I press "save"
 Then I should see "Select a text field saved"
 
 Then I should see a 3 column table "listTable" with contents 
 | Location of Strap 		    | Text Area          | False  |
 | Select a Radio Button Option | Radio Buttons Show | False |
 | Select a Drop Down Option    | Drop Down Show     | False |
 | Select a numeric field       | Numeric            | True  |
 | Select a date field          | Date               | False |
 | Select a time field          | Time               | False |
 | Select a text field          | Text Field         | False |
 
 Then I press "Logout"
 Then I should see "Please enter your userid and password to login"
 Given I have logged in as "researcher"
 Given I am on the home page
 And I press "system-administration"
 Then I should see "Sorry, you're not authorized to view this page."
 