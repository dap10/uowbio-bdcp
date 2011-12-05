Feature: View Devices in Study
  In order manage my data
  As a Researcher
  I want to view a device in a study
  I want to view manuals associated with a device in a study
 
 Background:
 Given I have logged in as "labman"
 Given I have created a project with "-1000", "My Biomechanics Project", "Fred Bloggs", "123456", "Masters of Biomechanics", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "Studying some stuff", "Alice Smith", "labman"
 Given I have created a study with "-2000", "-1000", "My Biomechanics Study", "1073A", "No", "Test Description", "Partner1", "keyword", "Collaborator1", "2011-04-01 00:00:00", "2011-04-01 00:00:00", "10", "Test Criteria"
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 Given I have created a device field with "Header 123", "STATIC_TEXT", "A text here", "false" for "Device1"
 Given I have created a device field with "Sample size", "TEXTAREA", "", "false" for "Device1"
 Given I have created a device field with "Header 456", "STATIC_TEXT", "This is new static", "false" for "Device1"
 Given I have created a device manual form with "deviceManual1", "deviceManual1.txt", "Device1"
 
 Given I am on the home page
 And I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I press "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "select_0"
 Then I press "update"
 Then I should see "Devices"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 
 Scenario: View Device in Study and download device manual
 Given I am on the home page
 Then I follow "My Biomechanics Study"
 Then I follow "Devices"
 Then I should see "Add Device"
 Then I should see "Force Platforms"
 Then I should see "Device1"
 Then I press "forms[0]"
 Then I should see "Device manuals for Device1"
 Then I press "return"
 Then I should see "Force Platforms"
 Then I press "forms[0]"
 Then I should see "Device manuals for Device1"
 
# DELETE IS NOT YET IMPLEMENTED
# This a proposed way to test
# Then I navigate back
# Then I press "deleteForm[0]"
# Then I should see "Device Manual Form deviceManual1 deleted"
# Then I should see "There are no device manuals for this device"
