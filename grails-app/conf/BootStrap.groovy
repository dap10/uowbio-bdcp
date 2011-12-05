import grails.util.Environment

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import au.org.intersect.bdcp.Component
import au.org.intersect.bdcp.Device
import au.org.intersect.bdcp.DeviceField
import au.org.intersect.bdcp.DeviceGroup
import au.org.intersect.bdcp.DeviceManualForm
import au.org.intersect.bdcp.Participant
import au.org.intersect.bdcp.ParticipantForm
import au.org.intersect.bdcp.Project
import au.org.intersect.bdcp.ResultsDetailsField
import au.org.intersect.bdcp.Session
import au.org.intersect.bdcp.StaticMetadataObject
import au.org.intersect.bdcp.Study
import au.org.intersect.bdcp.StudyCollaborator
import au.org.intersect.bdcp.StudyDevice
import au.org.intersect.bdcp.UserStore
import au.org.intersect.bdcp.enums.FieldType
import au.org.intersect.bdcp.enums.UserRole


class BootStrap
{
	def springSecurityService
	def concurrentSessionController
	def securityContextPersistenceFilter
	def grailsApplication
	def fileService
        def d1LdapServer
	
	def init =
	{ servletContext ->

		println "*** STARTING ENVIRONMENT : ${Environment.current} ***"
		securityContextPersistenceFilter.forceEagerSessionCreation = true
		SpringSecurityUtils.clientRegisterFilter('concurrentSessionFilter',
		SecurityFilterPosition.CONCURRENT_SESSION_FILTER)
		
		environments
		{
			production
			{
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
                user.save(flush:true)
				user = new UserStore(username:"egully", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mrs')
				user.save(flush:true)
                user = new UserStore(username:"kherrman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
                user.save(flush:true)
				user = new UserStore(username:"pnewnam", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
				user.save(flush:true)
				
				createStaticData() 
			}
			
			development
			{ 
				createTestData(servletContext)
				createStaticData()
			}
			
			test
			{
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
                user.save(flush:true)
				user = new UserStore(username:"egully", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mrs')
				user.save(flush:true)
                user = new UserStore(username:"kherrman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
                user.save(flush:true)
				user = new UserStore(username:"pnewnam", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
				user.save(flush:true)
				
				createStaticData() 
			}
			
			cucumber
			{
				// SEE features/support/env.groovy for initialization values as used in cuke4duke
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, nlaIdentifier:"http://ands.org.au/1234", title:'Mr')
                user.save(flush:true)
				user =new UserStore(username:"chrisk", deactivated: false, authority: UserRole.ROLE_RESEARCHER, nlaIdentifier:null, title:'Mr')
				user.save(flush:true)
				user = new UserStore(username:"labman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, nlaIdentifier:"http://ands.org.au/5678", title:'Mr')
				user.save(flush:true)
				user = new UserStore(username:"sysadm", deactivated: false, authority: UserRole.ROLE_SYS_ADMIN, nlaIdentifier:null, title:'Mr')
				user.save(flush:true)
				user = new UserStore(username:"researcher", deactivated: false, authority: UserRole.ROLE_RESEARCHER, nlaIdentifier:null, title:'Mr')
				user.save(flush:true)
				createStaticData() 
			}
			
			intersect_test
			{
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
				user.save(flush:true)				
                                user =new UserStore(username:"chrisk", deactivated: false, authority: UserRole.ROLE_RESEARCHER, title:'Mr')
                                user.save(flush:true)
				user = new UserStore(username:"labman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
				user.save(flush:true)
				user = new UserStore(username:"sysadm", deactivated: false, authority: UserRole.ROLE_SYS_ADMIN, title:'Mr')
				user.save(flush:true)
				user = new UserStore(username:"researcher", deactivated: false, authority: UserRole.ROLE_RESEARCHER, title:'Mr')
				user.save(flush:true)
				createStaticData() 
			}

			intersect_demo
			{
				def user = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
				user.save(flush:true)				
                                user =new UserStore(username:"chrisk", deactivated: false, authority: UserRole.ROLE_RESEARCHER, title:'Mr')
                                user.save(flush:true)
				user = new UserStore(username:"labman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
				user.save(flush:true)
				user = new UserStore(username:"sysadm", deactivated: false, authority: UserRole.ROLE_SYS_ADMIN, title:'Mr')
				user.save(flush:true)
				user = new UserStore(username:"researcher", deactivated: false, authority: UserRole.ROLE_RESEARCHER, title:'Mr')
				user.save(flush:true)
				createStaticData() 
			}

		}

		List.metaClass.partition = {size ->
			if (!delegate)
				return []
		
			def rslt = delegate.inject([[]]) {ret, elem ->
				(ret.last() << elem).size() >= size ? (ret << []) : ret
			}
			!rslt.last() ? rslt[0..-2] : rslt
		}
		
        String.metaClass.capitalise = { delegate[0].toUpperCase()+delegate[1..-1] }
		
	}
	
	def createStaticData =
	{
		def files = [['short':'uow','fname':'static~1.xml'],['short':'bml','fname':'static~2.xml']]
		files.each { props ->
				def shortDescription = props['short']
				def staticObj = StaticMetadataObject.findByShortDescription(shortDescription)
				if (staticObj != null) {
					return
				}
				def fname = props['fname']
				def content = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("WEB-INF/$fname")).text
				staticObj = new StaticMetadataObject(shortDescription:shortDescription, description:shortDescription, xmlContent:content)
				staticObj.save(flush:true)
			}
	}

	def createTestData = { context ->
        def user1 = new UserStore(username:"dpollum", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, nlaIdentifier:"http://nla.ands.org.au/1234", title:'Mr')
        user1.save(flush:true)
        
        def user2 = new UserStore(username:"chrisk", deactivated: false, authority: UserRole.ROLE_RESEARCHER, nlaIdentifier:"http://nla.ands.org.au/2345", title:'Mr')
        user2.save(flush:true)
		
		def user = new UserStore(username:"researcher", deactivated: false, authority: UserRole.ROLE_RESEARCHER, title:'Mr')
		user.save(flush:true)
		
		def user3 = new UserStore(username:"researcher1", deactivated: false, authority: UserRole.ROLE_RESEARCHER, title:'Mr')
		user3.save(flush:true)
        
		def project = new Project(projectTitle: 'TestProject',
				researcherName: 'researcher' ,
				studentNumber: 'StudentNumber' ,
				degree: 'TestDegree',
				startDate: new Date(),
				endDate: new Date(),
				description: 'Test Description',
				supervisors: 'test supervisor',
                owner: user2)
		project.save(flush: true)

		def study = new Study(studyTitle: 'TestStudy',
				uowEthicsNumber: '110678' ,
				additionalEthicRequirements:"Some requirements",
				description: 'Test Description',
				industryPartners: 'Partner1',
				keywords: 'keyword',
				collaborators: 'some collaborator',
				startDate: new Date(),
				endDate: new Date(),
				project: project,
				numberOfParticipants:"10",
				inclusionExclusionCriteria:"test Criteria")
		study.save(flush: true)
		
		def studyCollaborator = new StudyCollaborator(study, user)
		studyCollaborator.save(flush: true)

		def participant = new Participant(identifier:"10",
				study: study)
		participant.save(flush: true)
		
		def participantForm = new ParticipantForm(formName:"bash profile",
			form: ".bashrc",
			participant: participant)
		participantForm.save(flush: true)
		
        def component = new Component(name:"testComponent",
            description: "testComponentDescription", study: study)
        component.save(flush: true)
        
        def sessionInstance = new Session(name:"testSession",
            description: "testSessionDescription", component: component)
        sessionInstance.save(flush:true)
        
        def deviceGroup = new DeviceGroup(groupingName: "Force Platforms")
        deviceGroup.save()
		
		user = new UserStore(username:"labman", deactivated: false, authority: UserRole.ROLE_LAB_MANAGER, title:'Mr')
		user.save(flush:true)
		
		user = new UserStore(username:"sysadm", deactivated: false, authority: UserRole.ROLE_SYS_ADMIN, title:'Mr')
		user.save(flush:true)
        
        def device = new Device(name: "Device1",
            description: "Some device",
            manufacturer: "Some manufacturer",
            locationOfManufacturer: "Some location",
            modelName: "Some model",
            serialNumber: "11231ABC",
			uowAssetNumber: "11231ABC",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "\$10.00",
            vendor: "Intersect",
            fundingSource: "Some funding Body",
			maintServiceInfo: "Maintenance/Service information",
            deviceGroup: deviceGroup)
        device.save(flush: true)
		
//		def deviceManualForm = new DeviceManualForm(formName: "devicemanual1",
//				form: "theForm1",
//				fileName: "filename1.txt",
//				fileExtension: "txt",
//				storedFileName: "filename1.txt",
//				device: device)
//		deviceManualForm.save(flush: true)
//		createFileForDevice(context, device, "filename1.txt")

        def device2 = new Device(name: "Device2",
            description: "Some device",
            manufacturer: "Some manufacturer",
            locationOfManufacturer: "Some location",
            modelName: "Some model",
            serialNumber: "11231ABC",
            uowAssetNumber: "11231ABC",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "\$10.00",
            vendor: "Intersect",
            fundingSource: "Some funding Body",
            maintServiceInfo: "Maintenance/Service information",
            deviceGroup: deviceGroup)
        device2.save(flush: true)
		
//		def deviceManualForm2 = new DeviceManualForm(formName: "devicemanual2",
//			form: "theForm2",
//			fileName: "filename2.txt",
//			fileExtension: "txt",
//			storedFileName: "filename2.txt",
//			device: device2)
//		deviceManualForm2.save(flush: true)
//		createFileForDevice(context, device2, "filename2.txt")
//		
//		
//		def deviceManualForm2a = new DeviceManualForm(formName: "devicemanual2a",
//			form: "theForm2a",
//			fileName: "filename2a.txt",
//			fileExtension: "txt",
//			storedFileName: "filename2a.txt",
//			device: device2)
//		deviceManualForm2a.save(flush: true)
//		createFileForDevice(context, device2, "filename2a.txt")
		
        def device3 = new Device(name: "Device3",
            description: "Device no fields",
            manufacturer: "Some manufacturer",
            locationOfManufacturer: "Some location",
            modelName: "Ugly",
            serialNumber: "11231ABC3",
            uowAssetNumber: "11231ABC3",
            dateOfPurchase: new Date(),
            dateOfDelivery: new Date(),
            purchasePrice: "\$30.00",
            vendor: "Intersect",
            fundingSource: "Other funding Body",
            maintServiceInfo: "Maintenance/Service information here",
            deviceGroup: deviceGroup)
        device3.save(flush: true)
		
        createSampleAllDeviceFields(device, device1Data(), true) 
		
		createSampleAllDeviceFields(device2, device2Data(), false)
		
		createSampleResultDetailsFields()
		
        def studyDevice = StudyDevice.link(study, device);
        studyDevice.save(flush: true)

	}
	
	def createFileForDevice = { context, device, fileName ->
		def realPath = context.getRealPath("/") + grailsApplication.config.forms.deviceManuals.location.toString()
		def file = new File(realPath + device.id.toString() +File.separatorChar + fileName)
		file.getParentFile().mkdirs()
		file << ("FILE: " + fileName)
	}

	def createSampleResultDetailsFields = {
		def labels = ['Results','Student initials','Summary',
			'Analysis date','Analysis time','Data rows',
			'Software','Quality']
		def content = ['Register important <b>results</b>', null, null,
			null, null, null,
			"Windows\nMac\nLinux\nOther", "Best\nOk\nAverage\nPoor\nNone"]
		def types = [FieldType.STATIC_TEXT, FieldType.TEXT, FieldType.TEXTAREA,
			FieldType.DATE, FieldType.TIME, FieldType.NUMERIC,
			FieldType.RADIO_BUTTONS,FieldType.DROP_DOWN]
		for (i in 0..7) {
	        def field = new ResultsDetailsField(fieldLabel: labels[i],
	            fieldType: types[i],
	            mandatory: (i % 2 == 0))
			if (content[i] != null) {
				if (i == 0) {
					field.staticContent = content[i]
				} else {
					field.fieldOptions = content[i]
				}
			}
	        field.save(flush: true)
		}
	}

	def createSampleAllDeviceFields = { device, data, mandatory ->
		def (labels, content) = data
		def types = [FieldType.STATIC_TEXT, FieldType.TEXT, FieldType.TEXTAREA,
			FieldType.DATE, FieldType.TIME, FieldType.NUMERIC,
			FieldType.RADIO_BUTTONS,FieldType.DROP_DOWN]
		for (i in 0..7) {
	        def deviceField = new DeviceField(fieldLabel: labels[i],
	            fieldType: types[i],
	            mandatory: mandatory)
			if (content[i] != null) {
				if (i == 0) {
					deviceField.staticContent = content[i]
				} else {
					deviceField.fieldOptions = content[i]
				}
			}
	        device.addToDeviceFields(deviceField)
	        deviceField.save(flush: true)
		}
	}
	
	def device1Data = {
		def labels = ['Instructions','Participan initials','Interview result',
			'Experiment date','Experiment time','Sequence',
			'Shirt colour','Age range']
		def content = ['Some instructions with <i>HTML</i>!', null, null,
			null, null, null,
			"White\nYellow\nOrange\nRed\nPink\nGreen\nDrak green\nBlue\nPurple\nBlack", "0-17\n18-45\n46-75\n75+"]
		return [labels, content]
	}

	def device2Data = {
		def labels = ['More instructions','Location','Location description',
			'Building date','Experiment time','Number of levels',
			'Building type','Model used']
		def content = ['Some instructions with <i>HTML</i>!', null, null,
			null, null, null,
			"House\nSmall building\nWarehouse\nOther\nFactory", "M100\nM100+\nZ200\n"]
		return [labels, content]
	}
	
	def destroy =
	{
        environments
        {
            development 
            {
                def d1LdapServer
                d1LdapServer.stop()
            }
            
            test
            {
                def d1LdapServer
                d1LdapServer.stop()
            }

            cucumber
            {
                def d1LdapServer
                d1LdapServer.stop()
            }

        }
        
    }
}
