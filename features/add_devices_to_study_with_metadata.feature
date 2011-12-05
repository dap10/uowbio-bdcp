Feature: Add Device to Study with Metadata
  In order manage my data
  As a Researcher
  I want to add a device to a study with metadata
 
 Background:
 Given I have logged in as "labman"
 Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
 Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 Given I have created a device with "Device2", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 
 Given I have created a deviceField with "Device1", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "false", "Text", "TEXT", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:01:00", "2011-03-01 00:00:00", "true", "Text_Mandatory", "TEXT", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:02:00", "2011-03-01 00:01:00", "false", "Text_Area", "TEXTAREA", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:03:00", "2011-03-01 00:01:00", "true", "Text_Area_Mandatory", "TEXTAREA", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:04:00", "2011-03-01 00:02:00", "false", "Radio_Button", "RADIO_BUTTONS", "Option1"
 Given I have created a deviceField with "Device1", "2011-03-01 00:05:00", "2011-03-01 00:02:00", "true", "Radio_Button_Mandatory", "RADIO_BUTTONS", "Option1"
 Given I have created a deviceField with "Device1", "2011-03-01 00:06:00", "2011-03-01 00:03:00", "false", "Drop_Down", "DROP_DOWN", "Option1"
 Given I have created a deviceField with "Device1", "2011-03-01 00:07:00", "2011-03-01 00:03:00", "true", "Drop_Down_Mandatory", "DROP_DOWN", "Option1"
 Given I have created a deviceField with "Device1", "2011-03-01 00:08:00", "2011-03-01 00:04:00", "false", "Time", "TIME", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:09:00", "2011-03-01 00:04:00", "true", "Time_Mandatory", "TIME", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:10:00", "2011-03-01 00:05:00", "false", "Date", "DATE", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:11:00", "2011-03-01 00:05:00", "true", "Date_Mandatory", "DATE", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:11:00", "2011-03-01 00:05:00", "false", "Numeric", "NUMERIC", ""
 Given I have created a deviceField with "Device1", "2011-03-01 00:11:00", "2011-03-01 00:05:00", "true", "Numeric_Mandatory", "NUMERIC", ""
 
 Given I have created a deviceField with "Device2", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "false", "Text", "TEXT", ""
 Given I have created a deviceField with "Device2", "2011-03-01 00:01:00", "2011-03-01 00:00:00", "true", "Text_Mandatory", "TEXT", ""
 Given I have created a deviceField with "Device2", "2011-03-01 00:02:00", "2011-03-01 00:01:00", "false", "Text_Area", "TEXTAREA", ""
 Given I have created a deviceField with "Device2", "2011-03-01 00:03:00", "2011-03-01 00:01:00", "true", "Text_Area_Mandatory", "TEXTAREA", ""
 Given I have created a deviceField with "Device2", "2011-03-01 00:04:00", "2011-03-01 00:02:00", "false", "Radio_Button", "RADIO_BUTTONS", "Option1"
 Given I have created a deviceField with "Device2", "2011-03-01 00:05:00", "2011-03-01 00:02:00", "true", "Radio_Button_Mandatory", "RADIO_BUTTONS", "Option1"
 Given I have created a deviceField with "Device2", "2011-03-01 00:06:00", "2011-03-01 00:03:00", "false", "Drop_Down", "DROP_DOWN", "Option1"
 Given I have created a deviceField with "Device2", "2011-03-01 00:07:00", "2011-03-01 00:03:00", "true", "Drop_Down_Mandatory", "DROP_DOWN", "Option1"
 Given I have created a deviceField with "Device2", "2011-03-01 00:08:00", "2011-03-01 00:04:00", "false", "Time", "TIME", ""
 Given I have created a deviceField with "Device2", "2011-03-01 00:09:00", "2011-03-01 00:04:00", "true", "Time_Mandatory", "TIME", ""
 Given I have created a deviceField with "Device2", "2011-03-01 00:10:00", "2011-03-01 00:05:00", "false", "Date", "DATE", ""
 Given I have created a deviceField with "Device2", "2011-03-01 00:11:00", "2011-03-01 00:05:00", "true", "Date_Mandatory", "DATE", ""
 Given I have created a deviceField with "Device2", "2011-03-01 00:11:00", "2011-03-01 00:05:00", "false", "Numeric", "NUMERIC", ""
 Given I have created a deviceField with "Device2", "2011-03-01 00:11:00", "2011-03-01 00:05:00", "true", "Numeric_Mandatory", "NUMERIC", ""
 
 Scenario: Add Device to Study with Metadata
 Given I am on the home page
 And I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I enable javascript
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "select_0"
 Then I fill in "studyDeviceFields[0].text" with "Text&^%$"
 Then I fill in "studyDeviceFields[1].text" with "Some text"
 Then I fill in "studyDeviceFields[2].textArea" with "Some textarea"
 Then I fill in "studyDeviceFields[3].textArea" with "Text&^%$"
 Then I select radiobutton "Option1" from "studyDeviceFields[4].radioButtonsOption"
 Then I select radiobutton "Option1" from "studyDeviceFields[5].radioButtonsOption"
 Then I select "Option1" from "studyDeviceFields[6].dropDownOption"
 Then I select "Option1" from "studyDeviceFields[7].dropDownOption"
 Then I select "12" from "studyDeviceFields[8].time_hour"
 Then I select "12" from "studyDeviceFields[8].time_minute"
 Then I select "01" from "studyDeviceFields[9].time_hour"
 Then I select "00" from "studyDeviceFields[9].time_minute"
 Then I fill in "studyDeviceFields[10].date" with "1/03/2011"
 Then I fill in "studyDeviceFields[11].date" with "31/03/2011"
 Then I fill in "studyDeviceFields[12].numeric" with "0"
 Then I fill in "studyDeviceFields[13].numeric" with "text"
 
 Then I press "update"
 Then I should see "Please provide a value that is a number for the question: Numeric_Mandatory"
 Then I have cleared and filled in "studyDeviceFields[13].numeric" with "9E15"
 Then I press "update"
 Then I should see "Devices"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I disable javascript
 
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I should see "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1 ... already selected"
 
 Given I am on the home page
 And I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I enable javascript
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device2"
 Then I press "select_1"
 Then I fill in "studyDeviceFields[1].text" with "Some text"
 Then I fill in "studyDeviceFields[3].textArea" with "Text&^%$"
 Then I select radiobutton "Option1" from "studyDeviceFields[5].radioButtonsOption"
 Then I select "Option1" from "studyDeviceFields[7].dropDownOption"
 Then I select "01" from "studyDeviceFields[9].time_hour"
 Then I select "00" from "studyDeviceFields[9].time_minute"
 Then I fill in "studyDeviceFields[11].date" with "31/03/2011"
 Then I fill in "studyDeviceFields[13].numeric" with "9E15"
 
 Then I press "update"
 
 Then I should see "Devices"
 Then I should see "Force Platforms"
 Then I should see "Device2"
 Then I disable javascript
 
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I should see "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device2"
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device2 ... already selected"
