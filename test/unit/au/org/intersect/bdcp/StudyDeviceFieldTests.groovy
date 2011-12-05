package au.org.intersect.bdcp

import grails.test.*
import au.org.intersect.bdcp.enums.FieldType

class StudyDeviceFieldTests extends GrailsUnitTestCase {
	
	def studyDevice
	def device
	
    protected void setUp() {
        super.setUp()
		def project = new Project(projectTitle: 'TestProject',
			researcherName: 'TestStudent' ,
			degree: 'TestDegree',
			startDate: new Date(),
			endDate: new Date(),
			description: 'Test Description',
			supervisors: 'test supervisor')
		
		def study = new Study(studyTitle: 'Testing Study',
			uowEthicsNumber: '110680' ,
			description: 'Test Description',
			industryPartners: 'Partner1',
			keywords: 'keyword',
			collaborators: 'some collaborator',
			startDate: new Date(),
			endDate: new Date(),
			project: project,
			numberOfParticipants:"10",
			inclusionExclusionCriteria: "No Criteria")
   
	   def deviceGroup = new DeviceGroup(groupingName:"TestGrouping")
	   
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
	   
	   studyDevice = new StudyDevice(study: study, device: device)
	   
	   studyDevice.studyDeviceFields = [ ]
	   
	   mockForConstraintsTests(StudyDeviceField)
	   
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testTextValidation() {
		[FieldType.TEXT, FieldType.TEXTAREA].each { fieldType ->
			[true, false].each { mandatory ->
			    def textField = new DeviceField(fieldLabel:"Test " + fieldType.toString(),
				   fieldType: fieldType,
				   device: device,
				   mandatory: mandatory)
			
				def property = fieldType == FieldType.TEXT ? 'text' : 'textArea'
				def studyDeviceField1 = new StudyDeviceField(studyDevice:studyDevice, deviceField:textField)
				studyDeviceField1."$property" = 'something'
				studyDeviceField1.validate();
				assertEquals 'A correct ' + property + ' field does not pass', 0, studyDeviceField1.errors.errorCount
				
				studyDeviceField1 = new StudyDeviceField(studyDevice:studyDevice, deviceField:textField)
				studyDeviceField1."$property" = ''
				studyDeviceField1.validate();
				assertEquals 'A incorrect ' + property + ' field passes', (mandatory ? 1 : 0), studyDeviceField1.errors.errorCount
			}
		}
    }
	
	void testNumerics() {
		[true, false].each { mandatory ->
		    def textField = new DeviceField(fieldLabel:"Test numeric",
			   fieldType: FieldType.NUMERIC,
			   device: device,
			   mandatory: mandatory)
		
			def studyDeviceField1 = new StudyDeviceField(studyDevice:studyDevice, deviceField:textField, numeric:55)
			studyDeviceField1.validate();
			assertEquals 'A correct numerci field does not pass', 0, studyDeviceField1.errors.errorCount
			
			studyDeviceField1 = new StudyDeviceField(studyDevice:studyDevice, deviceField:textField, numeric:null)
			studyDeviceField1.validate();
			assertEquals 'A incorrect numeric field passes', (mandatory ? 1 : 0), studyDeviceField1.errors.errorCount
		}
	}
	
	void testOptions() {
		[FieldType.DROP_DOWN, FieldType.RADIO_BUTTONS].each { fieldType ->
			[true, false].each { mandatory ->
				def textField = new DeviceField(fieldLabel:"Test " + fieldType.toString(),
				   fieldType: fieldType,
				   device: device,
				   mandatory: mandatory,
				   fieldOptions: "Yes\nNo")
			
				def property = fieldType == FieldType.RADIO_BUTTONS ? 'radioButtonsOption' : 'dropDownOption'
				def studyDeviceField1 = new StudyDeviceField(studyDevice:studyDevice, deviceField:textField)
				studyDeviceField1."$property" = 'Yes'
				studyDeviceField1.validate();
				assertEquals 'A correct ' + property + ' field with Yes does not pass', 0, studyDeviceField1.errors.errorCount
				studyDeviceField1 = new StudyDeviceField(studyDevice:studyDevice, deviceField:textField)
				studyDeviceField1."$property" = 'No'
				studyDeviceField1.validate();
				assertEquals 'A correct ' + property + ' field with No does not pass', 0, studyDeviceField1.errors.errorCount

				studyDeviceField1 = new StudyDeviceField(studyDevice:studyDevice, deviceField:textField)
				studyDeviceField1."$property" = 'Blah'
				studyDeviceField1.validate();
				assertEquals 'A incorrect ' + property + ' field passes', 1, studyDeviceField1.errors.errorCount
				studyDeviceField1 = new StudyDeviceField(studyDevice:studyDevice, deviceField:textField)
				studyDeviceField1."$property" = null
				studyDeviceField1.validate();
				assertEquals 'A incorrect ' + property + ' field passes', (mandatory ? 1 : 0), studyDeviceField1.errors.errorCount
			}
		}

	}
}
