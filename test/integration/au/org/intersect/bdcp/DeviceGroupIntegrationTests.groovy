package au.org.intersect.bdcp

import grails.test.*

class DeviceGroupIntegrationTests extends GroovyTestCase 
{
    def deviceGroup
    
   /**
    * Setup operations before each test
    */
    protected void setUp() 
    {
        super.setUp()
        deviceGroup = new DeviceGroup(groupingName:"TestGrouping")
        deviceGroup.save(flush:true)
     }
    
    /**
     * Cleanup operations after each test
     */
    protected void tearDown() 
    {
        super.tearDown()
    }

   /**
    * Test that the blank fields in the domain class {@link DeviceGroup} are
    * correctly validated
    */
    void testBlank() {

        deviceGroup = new DeviceGroup(groupingName: '')
        
        assertFalse 'No validation for blank field(s)' ,deviceGroup.validate()
        assertNotNull 'Grouping Name is not blank.', deviceGroup.errors.getFieldError('groupingName')
        def code = deviceGroup.errors.getFieldError('groupingName')?.codes.find {it == 'deviceGroup.groupingName.blank'}
        assertNotNull 'Grouping Name is not blank', code
        deviceGroup = new DeviceGroup(groupingName: 'TestGrouping1')
        assertTrue "A valid device grouping did not validate!", deviceGroup.validate()
    }
    
    /**
    * Test that the blank fields in the domain class {@link DeviceGroup} are
    * correctly validated
    */
    void testUnique() {

        deviceGroup = new DeviceGroup(groupingName: 'TestGrouping')
        
        assertFalse 'No validation for unique field(s)' ,deviceGroup.validate()
        assertNotNull 'Grouping Name is not unique.', deviceGroup.errors.getFieldError('groupingName')
        def code = deviceGroup.errors.getFieldError('groupingName')?.codes.find {it == 'deviceGroup.groupingName.uniqueIgnoreCase.invalid'}
        assertNotNull 'Grouping Name is not unique', code
        deviceGroup = new DeviceGroup(groupingName: 'TestGrouping2')
        assertTrue "A valid device grouping did not validate!", deviceGroup.validate()
    }
    
    /**
    * Test the range validation of fields in the domain class {@link DeviceGroup} are
    * correctly validated
    */
   void testRange()
   {
       deviceGroup = new DeviceGroup(groupingName:'012345678910' * 100) 
       assertFalse "No validation for size of fields", deviceGroup.validate()
       assertNotNull 'Grouping Name is not unique.', deviceGroup.errors.getFieldError('groupingName')
        def code = deviceGroup.errors.getFieldError('groupingName')?.codes.find {it == 'deviceGroup.groupingName.size.toobig'}
        assertNotNull 'Grouping Name is not too large', code
       deviceGroup = new DeviceGroup(groupingName:"TestGroupingName")
       assertTrue deviceGroup.validate()
   }
    
}
