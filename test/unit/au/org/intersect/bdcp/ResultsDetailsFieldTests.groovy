package au.org.intersect.bdcp

import grails.test.*

import au.org.intersect.bdcp.enums.FieldType

class ResultsDetailsFieldTests extends GrailsUnitTestCase {
    
    def resultsDetailsField
    
    protected void setUp() {
        
        super.setUp()
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.TEXTAREA,
            mandatory: false)
        
        mockForConstraintsTests ResultsDetailsField, [resultsDetailsField]
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testBlank() {
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel: '',
            fieldType: FieldType.TEXT,
            mandatory: true)
        
        assertFalse 'No validation for blank field(s)' ,resultsDetailsField.validate()
        
        assertEquals 'Field Label is blank.','blank', resultsDetailsField.errors['fieldLabel']
                
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
                    fieldType: FieldType.TEXTAREA,
                    mandatory: false)
                
        assertTrue "A valid resultsDetailsField did not validate!", resultsDetailsField.validate()

    }
    
    void testNullable() {
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel: 'some test',
            fieldType: null,
            staticContent: null,
            fieldOptions: null,
            mandatory: '')
        
        assertFalse 'No validation for null field(s)' ,resultsDetailsField.validate()
        
                assertEquals 'Field Type is null.','nullable', resultsDetailsField.errors['fieldType']
                
                resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
                    fieldType: FieldType.TEXTAREA,
                    staticContent: null,
                    fieldOptions: null,
                    mandatory: false)
                
                assertTrue "A valid resultsDetailsField did not validate!", resultsDetailsField.validate()

    }
    
    void testNullValueWhenStaticContentSelected()
    {
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.STATIC_TEXT,
            staticContent: null,
            fieldOptions: null,
            mandatory: false)
        
        assertFalse 'No validation for null field(s)' ,resultsDetailsField.validate()
        assertEquals 'staticContent is null.','nullable', resultsDetailsField.errors['staticContent']
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.STATIC_TEXT,
            staticContent: "test1",
            fieldOptions: null,
            mandatory: false)
        
        assertTrue "A valid resultsDetailsField did not validate!", resultsDetailsField.validate()
    }
    
    void testNullValueWhenRadioButtonsSelected()
    {
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.RADIO_BUTTONS,
            staticContent: null,
            fieldOptions: null,
            mandatory: false)
        
        assertFalse 'No validation for null field(s)' ,resultsDetailsField.validate()
        assertEquals 'fieldOptions is null.','nullable', resultsDetailsField.errors['fieldOptions']
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.RADIO_BUTTONS,
            staticContent: null,
            fieldOptions: "Option1\nOption2",
            mandatory: false)
        
        assertTrue "A valid resultsDetailsField did not validate!", resultsDetailsField.validate()
    }
    
    void testNullValueWhenDropDownSelected()
    {
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.DROP_DOWN,
            staticContent: null,
            fieldOptions: null,
            mandatory: false)
        
        assertFalse 'No validation for null field(s)' ,resultsDetailsField.validate()
        assertEquals 'fieldOptions is null.','nullable', resultsDetailsField.errors['fieldOptions']
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.DROP_DOWN,
            staticContent: null,
            fieldOptions: "Option1\nOption2",
            mandatory: false)
        
        assertTrue "A valid resultsDetailsField did not validate!", resultsDetailsField.validate()
    }
    
    void testNumberOfOptionsValidationWhenDropDownSelectedAndTooFewOptions()
    {
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.DROP_DOWN,
            staticContent: null,
            fieldOptions: "option1",
            mandatory: false)
        
        assertFalse 'No validation for number of options field(s)' ,resultsDetailsField.validate()
        assertEquals 'fieldOptions is too small.','size', resultsDetailsField.errors['fieldOptions']
        
        
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.DROP_DOWN,
            staticContent: null,
            fieldOptions: "Option1\nOption2",
            mandatory: false)
        
        assertTrue "A valid resultsDetailsField did not validate!", resultsDetailsField.validate()
    }
    
    void testNumberOfOptionsValidationWhenRadioButtonsSelectedAndTooFewOptions()
    {
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.RADIO_BUTTONS,
            staticContent: null,
            fieldOptions: "option1",
            mandatory: false)
        
        assertFalse 'No validation for number of options field(s)' ,resultsDetailsField.validate()
        assertEquals 'fieldOptions is too small.','size', resultsDetailsField.errors['fieldOptions']
        
        
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.RADIO_BUTTONS,
            staticContent: null,
            fieldOptions: "Option1\nOption2",
            mandatory: false)
        
        assertTrue "A valid resultsDetailsField did not validate!", resultsDetailsField.validate()
    }
    
    void testUniqueOptionsValidationWhenRadioButtonsSelectedAndOptionsNotUnique()
    {
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.RADIO_BUTTONS,
            staticContent: null,
            fieldOptions: "option1\noption1",
            mandatory: false)
        
        assertFalse 'No validation for unique value of options field(s)' ,resultsDetailsField.validate()
        assertEquals 'fieldOptions are not unique.','unique', resultsDetailsField.errors['fieldOptions']
        
        
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.RADIO_BUTTONS,
            staticContent: null,
            fieldOptions: "Option1\nOption2",
            mandatory: false)
        
        assertTrue "A valid resultsDetailsField did not validate!", resultsDetailsField.validate()
    }
    
    void testUniqueOptionsValidationWhenDropDownSelectedAndOptionsNotUnique()
    {
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.DROP_DOWN,
            staticContent: null,
            fieldOptions: "option1\noption1",
            mandatory: false)
        
        assertFalse 'No validation for unique value of options field(s)' ,resultsDetailsField.validate()
        assertEquals 'fieldOptions are not unique.','unique', resultsDetailsField.errors['fieldOptions']
        
        
        
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.DROP_DOWN,
            staticContent: null,
            fieldOptions: "Option1\nOption2",
            mandatory: false)
        
        assertTrue "A valid resultsDetailsField did not validate!", resultsDetailsField.validate()
    }
    
    void testToStringReturnsFieldValue()
    {
        assertEquals 'toString() operation functions incorrectly','some label', resultsDetailsField.toString()
    }
    
    void testGetFieldOptionsReturnsAllOptions()
    {
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.DROP_DOWN,
            staticContent: null,
            fieldOptions: "option1\noption2",
            mandatory: false)
        
        assertEquals "All options not returned correctly",['option1', 'option2'], resultsDetailsField.getFieldOptionsList()
    }
  
    void testValidFieldOptionsMethodWhenValEmpty()
    {
        resultsDetailsField = new ResultsDetailsField(fieldLabel:'some label',
            fieldType: FieldType.DROP_DOWN,
            fieldOptions: "",
            mandatory: false) 
        
        assertFalse "Not returning nullable when fieldOptions empty",resultsDetailsField.validate()
        assertEquals 'fieldOptions are not ok','nullable', resultsDetailsField.errors['fieldOptions']
    }  
    
}
