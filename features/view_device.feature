Feature: View Device
  In order manage my data
  As an Administrator
  I want to create a Device in a Device Grouping
 
 Background:
 Given I have logged in
 Given I have created a device grouping with "Force Platforms"
 Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding Source", "Maintenance Service Info"
 
 Scenario: View Device
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "Force Platforms"
 Then I should see "Force Platforms"
 Then I should see "Add new device"
 Then I should see "Device1"
 Then I press "Device1"
 And I should see table "deviceTable" with contents
      | Name                      																		| Device1                  |
      | Description			      																		| Some Description         |
      | Manufacturer			  																		| Some Manufacturer        |
      | Location Of Manufacturer  																		| Some location            |
      | Model					  																		| Some model               |
      | Serial Number			  																		| Some serial number       |
      | UOW Asset Number		  																		| Some UOW Asset number    |
      | Date Of Purchase		  																		| 01/03/2011               |
      | Date Of Delivery          																		| 01/03/2011               |
      | Purchase Price		      																		| $10.00                   |
      | Vendor					  																		| Intersect                |
      | Funding Source			  																		| Some funding Source      |
      | Maintenance/Service Information (Please record the date, details and cost for each entry)       | Maintenance Service Info |
      
   