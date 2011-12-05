package au.org.intersect.bdcp

import grails.test.*

import au.org.intersect.bdcp.enums.FieldType

class DeviceFieldTests extends GrailsUnitTestCase {
    
    def deviceField
    
    def device
    
    def deviceGroup
    
    protected void setUp() {
        super.setUp()
        
        deviceGroup = new DeviceGroup(groupingName:"TestGrouping")
        
        device = new Device(name: "Device1",
            description: "Some description",
            manufacturer: "Some manufacturer",
            locationOfManufacturer: "Some location",
            modelName: "Some model",
            serialNumber: "Some serialNumber",
			uowAssetNumber: "Some uowAssetNumber",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "\$10.00",
            vendor: "Some vendor",
            fundingSource: "Some funding Source",
			maintServiceInfo: "Maintenance Service Info",
            deviceGroup: deviceGroup)
        
        deviceField = new DeviceField(fieldLabel:"Test Field",
            fieldType: FieldType.TEXT,
            device: device)
        mockForConstraintsTests DeviceField, [deviceField]
    }

    protected void tearDown() {
        super.tearDown()
    }

    /**
    * Test that the blank fields in the domain class {@link DeviceField} are
    * correctly validated
    */
    void testBlank() {

        deviceField = new DeviceField(fieldLabel: '',
            fieldType: FieldType.TEXT,
            device: device)
        
        assertFalse 'No validation for blank field(s)' ,deviceField.validate()
        assertEquals 'Field Label is blank.','blank', deviceField.errors['fieldLabel']
        deviceField = new DeviceField(fieldLabel: 'Test Field',
            fieldType: FieldType.TEXT,
            device: device)
        assertTrue "A valid device field did not validate!", deviceField.validate()
    }
    
    /**
    * Test the range validation of fields in the domain class {@link DeviceField} are
    * correctly validated
    */
   void testRange()
   {
       deviceField = new DeviceField(fieldLabel:'012345678910' * 100,
           fieldType: FieldType.TEXT,
           device: device)
       assertFalse "No validation for size of fields", deviceField.validate()
       assertEquals 'Field Label does not validate size.','size', deviceField.errors['fieldLabel']
       deviceField = new DeviceField(fieldLabel:"Test Field",
            fieldType: FieldType.TEXT,
            device: device)
       assertTrue deviceField.validate()
   }
   
   /**
    * Test the validation for the fieldOptions field in the domain class {@link DeviceField} are
    * correctly validated.
    */
   void testFieldOptionsNullable()
   {
        deviceField = new DeviceField(fieldLabel:"Some label",
            fieldType: FieldType.DROP_DOWN, 
            device: device)
        assertFalse "No validation for FieldOptions", deviceField.validate()
        assertEquals "FieldOptions is not validated for null values", 'nullable', deviceField.errors['fieldOptions']
        
        deviceField = new DeviceField(fieldLabel: "Some label",
            fieldType: FieldType.RADIO_BUTTONS,
            device: device)
        
        assertFalse "No validation for FieldOptions", deviceField.validate()
        assertEquals "FieldOptions is not validated for null values", 'nullable', deviceField.errors['fieldOptions']
        
        deviceField = new DeviceField(fieldLabel: "Some label",
            fieldType: FieldType.DROP_DOWN,
            fieldOptions: "",
            device: device) 
        assertFalse "No validation for FieldOptions", deviceField.validate()
        assertEquals "FieldOptions is not validated for empty values", 'nullable', deviceField.errors['fieldOptions']
        
        deviceField = new DeviceField(fieldLabel: "Some label",
            fieldType: FieldType.DROP_DOWN,
            fieldOptions: "test\n test2",
            device: device)
        
        assertTrue "A valid Device Field did not validate!" , deviceField.validate()
        
   }
   
   void testFieldOptionsWithOnlyOneOption()
   {
       deviceField = new DeviceField(fieldLabel: "Some label",
           fieldType: FieldType.DROP_DOWN,
           fieldOptions: "test\n",
           device: device)
       assertFalse "No validation for FieldOptions", deviceField.validate()
       assertEquals "FieldOptions is not validated for less than 2 values", 'size', deviceField.errors['fieldOptions']
       
       deviceField = new DeviceField(fieldLabel: "Some label",
           fieldType: FieldType.DROP_DOWN,
           fieldOptions: "test\ntest2\n",
           device: device)
       assertTrue "A valid Device Field did not validate!" , deviceField.validate()
    }
   
   void testFieldOptionsWithTwoSameOptions()
   {
       deviceField = new DeviceField(fieldLabel: "Some label",
           fieldType: FieldType.DROP_DOWN,
           fieldOptions: "test\ntest\n",
           device: device)
       assertFalse "No validation for FieldOptions", deviceField.validate()
       assertEquals "FieldOptions is not validated for values that are not unique", 'unique', deviceField.errors['fieldOptions']
       deviceField = new DeviceField(fieldLabel: "Some label",
           fieldType: FieldType.DROP_DOWN,
           fieldOptions: "test\ntest2\n",
           device: device)
       assertTrue "A valid Device Field did not validate!" , deviceField.validate()
       
   }
   
}
