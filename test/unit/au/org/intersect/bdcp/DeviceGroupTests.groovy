package au.org.intersect.bdcp

import grails.test.*

class DeviceGroupTests extends GrailsUnitTestCase 
{
    def deviceGroup
    
   /**
    * Setup operations before each test
    */
    protected void setUp() 
    {
        super.setUp()
        deviceGroup = new DeviceGroup(groupingName:"TestGrouping")
        mockForConstraintsTests DeviceGroup, [deviceGroup]
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
        assertEquals 'Grouping Name is blank.','blank', deviceGroup.errors['groupingName']
        deviceGroup = new DeviceGroup(groupingName: 'TestGrouping1')
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
       assertEquals 'Grouping Name does not validate size.','size', deviceGroup.errors['groupingName']
       deviceGroup = new DeviceGroup(groupingName:"TestGroupingName")
       assertTrue deviceGroup.validate()
   }
    
}
