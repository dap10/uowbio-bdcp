Feature: Create Device
  In order manage my data
  As an Administrator
  I want to create a Device in a Device Grouping
 
 Background:
 Given I have logged in
 Given I have created a device grouping with "Force Platforms"

 Scenario: Create Device
 Given I am on the home page
 And I press "system-administration"
 And I should see "System Administration"
 Then I press "device-administration"
 Then I should see "Device Administration"
 Then I press "Force Platforms"
 Then I should see "Force Platforms"
 Then I should see "Add new device"
 Then I press "Add new device"
 Then I should see "Add New Device"
 Then I fill in "name" with "Device1"
 Then I fill in "description" with "Some Description"
 Then I fill in "manufacturer" with "Some manufacturer"
 Then I fill in "locationOfManufacturer" with "Some location"
 Then I fill in "modelName" with "Some model"
 Then I fill in "serialNumber" with "Some serial Number"
 Then I fill in "uowAssetNumber" with "Some UOW Asset number" 
 Then I select "2011" from "dateOfPurchase_year"
 Then I select "March" from "dateOfPurchase_month"
 Then I select "1" from "dateOfPurchase_day"
 Then I select "2011" from "dateOfDelivery_year"
 Then I select "March" from "dateOfDelivery_month"
 Then I select "1" from "dateOfDelivery_day"
 Then I fill in "purchasePrice" with "$10.00"
 Then I fill in "vendor" with "Some vendor"
 Then I fill in "fundingSource" with "Some funding Source"
 Then I fill in "maintServiceInfo" with "Maintenance Service Info"
 Then I press "save"
 Then I should see "Device Device1 saved"
 Then I should see "Devices"
 Then I should see "Device1" 

 
 
 
   