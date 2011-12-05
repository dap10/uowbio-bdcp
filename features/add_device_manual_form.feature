Feature: Add Device Manual Form
  In order manage my devices
  As a lab manager or system administrator
  I want to add a device manual form 
 
 Background:
    Given I have logged in as "labman"
	Given I have created a device grouping with "Force Platforms"
	Given I have created a device with "Device1", "Some Description", "Some Manufacturer", "Some location", "Some model", "Some serial number", "Some UOW Asset number", "2011-03-01 00:00:00", "2011-03-01 00:00:00", "$10.00", "Intersect", "Some funding source", "Maintenance Service Info"
 
 Scenario: Create Device
	Given I am on the home page
    Then I enable javascript	
	And I press "system-administration"
	And I should see "System Administration"
	Then I press "device-administration"
	Then I should see "Device Administration"
	Then I press "Force Platforms"
	Then I should see "Force Platforms"
	Then I should see "Add new device"
	Then I should see "Device1"    
	Then I press "forms[0]"
    Then I should see "Device Device1 Manuals"
    Then I should see "Add Manual"
    Then I press "Logout"
    
    Given I have logged in as "sysadm"
    Given I am on the home page
	And I press "system-administration"
	And I should see "System Administration"
	Then I press "device-administration"
	Then I should see "Device Administration"
	Then I press "Force Platforms"
	Then I should see "Force Platforms"
	Then I should see "Add new device"
	Then I should see "Device1"    
	Then I press "forms[0]"
    Then I should see "Device Device1 Manuals"
    Then I should see "Add Manual"
    Then I press "Logout"

    Given I have logged in as "labman"
    Given I am on the home page
	And I press "system-administration"
	And I should see "System Administration"
	Then I press "device-administration"
	Then I should see "Device Administration"
	Then I press "Force Platforms"
	Then I should see "Force Platforms"
	Then I should see "Add new device"
	Then I should see "Device1"    
	Then I press "forms[0]"
    Then I should see "Device Device1 Manuals"
    Then I should see "Add Manual"
    Then I press "Logout"
    