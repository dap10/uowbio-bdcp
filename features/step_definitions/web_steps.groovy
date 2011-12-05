import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import groovy.sql.Sql

import au.org.intersect.bdcp.UserStore;
import au.org.intersect.bdcp.Project;

import au.org.intersect.uploader.http.MultipartPostHelper;

this.metaClass.mixin(cuke4duke.GroovyDsl)

/**
 * This is a set of steps that do the usual things on web pages, such as filling in form fields, clicking buttons and links etc
 * This should not contain application-specific steps - they should go in their own step file.
 */

Given(~"I am on the home page") { ->
    browser.get("http://localhost:8080/BDCP")
}

Given(~"I am on the show study page with \"(.*)\", \"(.*)\"") { String study_id, String project_id ->
	browser.get("http://localhost:8080/BDCP/project/${project_id}/study/show/${study_id}")
}

Given(~"I am on the list participants page for study \"(.*)\"") { String study_id ->
	browser.get("http://localhost:8080/BDCP/study/${study_id}/participant/list")
}

Given(~"I am on the list components page for study \"(.*)\"") { String study_id ->
	browser.get("http://localhost:8080/BDCP/study/${study_id}/component/list")
}

Given(~"I am on the list files page for study \"(.*)\"") { String study_id ->
	browser.get("http://localhost:8080/BDCP/study/${study_id}/sessionFile/fileList")
}

Given(~"I am on the list devices page for study \"(.*)\"") { String study_id ->
	browser.get("http://localhost:8080/BDCP/study/${study_id}/studyDevice/list")
}

Given(~"I am on the list collaborators page for study \"(.*)\"") { String study_id ->
	browser.get("http://localhost:8080/BDCP/study/${study_id}/listCollaborators")
}

Given(~"I have logged in") { ->
	browser.get("http://localhost:8080/BDCP/login/auth")
	fieldElement = browser.findElement(By.name("j_username"))
	fieldElement.sendKeys("labman")
	fieldElement = browser.findElement(By.name("j_password"))
	fieldElement.sendKeys("password")
	browser.findElementById("Login").click()
}

Given(~"I have logged in as \"(.*)\"") { String username ->
	browser.get("http://localhost:8080/BDCP/login/auth")
	fieldElement = browser.findElement(By.name("j_username"))
	fieldElement.sendKeys(username)
	fieldElement = browser.findElement(By.name("j_password"))
	fieldElement.sendKeys("password")
	browser.findElementById("Login").click()
}

Given(~"I am on the email page") { ->
	browser.get("http://localhost:8080/BDCP/greenmail/list")
}

Given(~"I have created a project with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String project_id, String project_title, String researcher_name, String student_number, String degree, String start_date, String end_date, String description, String supervisors, String username ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	def userStoreId = (int)(getUserStore("${username}").id)
	sql.execute("INSERT INTO project (id,version, project_title, researcher_name, student_number, degree, start_date, end_date, description, supervisors, owner_id) VALUES ('${project_id}','0',${project_title}, ${researcher_name}, ${student_number}, ${degree}, '${start_date}', '${end_date}', ${description}, ${supervisors}, ${userStoreId});")
}

def getUserStore(String username) {
	def userStore
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.eachRow("Select * from user_store WHERE username = ?", [username]) {
		userStore = it.toRowResult()
	}
	return userStore
}

Given(~"I have created a study with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String study_id, String project_id, String study_title, String uow_ethics_number, String has_additional_ethics_requirements, String description, industry_partners, keywords, collaborators, String start_date, String end_date, String number_of_participants, String inclusion_exclusion_criteria ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO study (id,version, project_id, study_title, uow_ethics_number, has_additional_ethics_reqs, description, industry_partners, keywords, collaborators, start_date, end_date, number_of_participants, inclusion_exclusion_criteria, date_created, last_updated) VALUES ('${study_id}','0', '${project_id}', ${study_title}, ${uow_ethics_number}, ${has_additional_ethics_requirements}, ${description}, ${industry_partners}, ${keywords}, ${collaborators}, '${start_date}', '${end_date}', ${number_of_participants}, ${inclusion_exclusion_criteria}, now(), now());")
}

Given(~"I have created a collaborator study with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String project_id, String study_id, String study_collaborator_id, String study_title, String uow_ethics_number, String has_additional_ethics_requirements, String description, industry_partners, String keywords, collaborators, String start_date, String end_date, String number_of_participants, String inclusion_exclusion_criteria, String collaborator_name ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	def collaboratorId = (int)(getUserStore("${collaborator_name}").id)
	sql.execute("INSERT INTO study (id,version, project_id, study_title, uow_ethics_number, has_additional_ethics_reqs, description, industry_partners, keywords, collaborators, start_date, end_date, number_of_participants, inclusion_exclusion_criteria, date_created, last_updated) VALUES ('${study_id}','0', '${project_id}', ${study_title}, ${uow_ethics_number}, ${has_additional_ethics_requirements}, ${description}, ${industry_partners}, ${keywords}, ${collaborators}, '${start_date}', '${end_date}', ${number_of_participants}, ${inclusion_exclusion_criteria}, now(), now());")
	sql.execute("INSERT INTO study_collaborator (id, version, collaborator_id, study_id) VALUES ('${study_collaborator_id}', '0', ${collaboratorId}, '${study_id}')")
}

Given(~"I have created a component with \"(.*)\", \"(.*)\"") { String name, String description ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO component (id,version, study_id, name, description) VALUES ('-3000','0','-2000', ${name}, ${description});")
}

Given(~"I have created a session with \"(.*)\", \"(.*)\"") { String name, String description ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	sql.execute("INSERT INTO study_session (id,version, component_id, name, description) VALUES ('-4000','0', '-3000', ${name}, ${description});")
}

Given(~"I have created a device grouping with \"(.*)\"") { String groupingName ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
    sql.execute("INSERT INTO device_group(id,version, grouping_name) VALUES ('-5000','0',${groupingName});")
}

Given(~"I have created a device with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String name, String description, String manufacturer, String locationOfManufacturer, String model, String serialNumber, String uowAssetNumber, String dateOfPurchase, String dateOfDelivery, String purchasePrice, String vendor, String fundingSource, String maintServiceInfo ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver") 
    sql.execute("INSERT INTO device(id,version, device_group_id, name, description, manufacturer, location_of_manufacturer, model_name, serial_number, uow_asset_number, date_of_purchase, date_of_delivery, purchase_price, vendor, funding_source, maint_service_info) VALUES (nextval('hibernate_sequence'),'0','-5000', ${name}, ${description}, ${manufacturer}, ${locationOfManufacturer}, ${model}, ${serialNumber}, ${uowAssetNumber}, '${dateOfPurchase}', '${dateOfDelivery}', ${purchasePrice}, ${vendor}, ${fundingSource}, ${maintServiceInfo});")
}

Given(~"I have created a device field with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\" for \"(.*)\"") { String fieldLabel, String fieldType, String staticContent, Boolean mandatory, String deviceName ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver") 
	def row = sql.firstRow("SELECT id FROM device WHERE name=${deviceName}")
	def deviceId = row.id;
	row = sql.firstRow("SELECT count(id) as num FROM device_field WHERE device_id=${deviceId}")
    def fieldIndex = row.num
    sql.execute("INSERT INTO device_field(id,device_id,device_fields_idx,field_label,field_type,static_content,mandatory,date_created,last_updated,version) VALUES (nextval('hibernate_sequence'),${deviceId},${fieldIndex},${fieldLabel},${fieldType},${staticContent},${mandatory}, '2011-03-01 00:00:00', '2011-03-01 00:00:00',0);")
}

Given(~"I have created a device manual form with \"(.*)\", \"(.*)\", \"(.*)\"") { String formname, String filename, String deviceName ->
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	def row = sql.firstRow("SELECT id FROM device WHERE name=${deviceName}")
	def deviceId = row.id;
	sql.execute("INSERT INTO device_manual_form(id, device_id, version, form_name, file_name, stored_file_name) VALUES (nextval('hibernate_sequence'),${deviceId}, '0', ${formname}, ${filename}, ${filename});")
	def filePath = new File("web-app/uowbio/forms/deviceManuals/" + deviceId + "/" + filename)
	filePath.getParentFile().mkdirs()
	println "CREATING " + filePath.getAbsolutePath()
	filePath << ("FILE: " + filename + " @ " + filePath.getAbsolutePath())	
}

Given(~"I have created a deviceField with \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"") { String deviceName, String dateCreated, String lastUpdated, String mandatory, String fieldLabel, String fieldType, String fieldOptions ->
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
    def deviceId = (int)(getDevice("${deviceName}").id)
    def row = sql.firstRow("SELECT count(id) as num FROM device_field WHERE device_id=${deviceId}")
    def fieldIndex = row.num
    sql.execute("INSERT INTO device_field(id, version,device_id, device_fields_idx,date_created, last_updated, mandatory, field_label, field_type, field_options) VALUES (nextval('hibernate_sequence'), '0','${deviceId}', ${fieldIndex}, '${dateCreated}', '${lastUpdated}', '${mandatory}', ${fieldLabel}, ${fieldType}, ${fieldOptions});")
}

Given(~"I have created result fields") { 
	def insertResultField = { sql, columns ->
		def sqlStmt = "INSERT INTO results_details_field(id,version, field_label, field_type, field_options, static_content, mandatory, date_created, last_updated) " +
		  "VALUES (nextval('hibernate_sequence'), '0','${columns['fieldLabel']}','${columns['fieldType']}','${columns['fieldOptions']}','${columns['staticContent']}','${columns['mandatory']}',now(),now())";
		println sqlStmt
        sql.execute(sqlStmt)
	}
	def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
	def labels = ['Results','Student initials','Summary',
		'Analysis date','Analysis time','Data rows',
		'Software','Quality']
	def content = ['Register important <b>results</b>', null, null,
		null, null, null,
		"Windows\nMac\nLinux\nOther", "Best\nOk\nAverage\nPoor\nNone"]
	def types = ['STATIC_TEXT', 'TEXT', 'TEXTAREA',
		'DATE', 'TIME', 'NUMERIC',
		'RADIO_BUTTONS','DROP_DOWN']
	for (i in 0..7) {
		def field = [fieldLabel: labels[i], fieldType: types[i], mandatory: (i % 2 == 0)]
		if (content[i] != null) {
			if (i == 0) {
				field['staticContent'] = content[i]
			} else {
				field['fieldOptions'] = content[i]
			}
		}
		insertResultField(sql, field)
	}
}

def deleteAll(File f) {
	if (!f.exists()) { return; }
	if (!f.isDirectory()) {
		if (!f.delete()) { throw new RuntimeException("Cannot delete " + f.getAbsolutePath()); }
	    return;
	}
	f.listFiles().each { File child ->
		if (!".".equals(child.getName()) && !"..".equals(child.getName())) deleteAll(child)
	}
	if (!f.delete()) { throw new RuntimeException("Cannot delete directory " + f.getAbsolutePath()); }
}

Given(~"the file service folder \"(.*)\" is empty") { String path ->
	def rootPath = new File("web-app/" + path)
	deleteAll(rootPath)
}

Given(~"the file service folder \"(.*)\" exists") { String path ->
	def rootPath = new File("web-app/" + path)
	if (!rootPath.exists()) {
	   assertTrue(rootPath.mkdirs())
	}
}

Then(~"I should have file service (.*) \"(.*)\"") { String type, String path ->
	def file = new File("web-app/" + path)
	assertTrue(file.getAbsolutePath() + " not found", file.exists())
	assertTrue("folder".equals(type) == file.isDirectory())
}

Then(~"I use uploader to upload files to study (\\d+) in folder \"(.*)\"") { String studyId, String destFolder ->
	def mph = new MultipartPostHelper("http://localhost:8080/BDCP/studyAnalysedData/${studyId}/uploadFiles")
    def destDir = destFolder
	def dirStruct = """[{"folder_root":"test-files","file_1":"test-files/form-upload1.txt",
		              "file_2":"test-files/form-upload2.txt","file_3":"test-files/form-upload3.txt"}]"""
	mph.addStringPart('dirStruct', dirStruct, 'application/json', 'utf-8')
	mph.addStringPart('destDir', destDir, 'text/plain', 'utf-8')
	mph.addFilePart('file_1', new File('features/test-files/form-upload1.txt'))
	mph.addFilePart('file_2', new File('features/test-files/form-upload2.txt'))
	mph.addFilePart('file_3', new File('features/test-files/form-upload3.txt'))
	mph.execute()
}

def getDevice(String name) {
    def device
    def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/bdcp-test", "grails", "grails", "org.postgresql.Driver")
    sql.eachRow("Select * from Device WHERE name = ?", [name]) {
        device = it.toRowResult()
    }
    return device
}

Given(~"I have deleted all emails") { ->
	browser.get("http://localhost:8080/BDCP/greenmail/clear")
}

Given(~"I follow \"(.*)\"") { String linkText->
	List<WebElement> links = browser.findElementsByTagName('a')
	WebElement element = links.find {
		it.text.toLowerCase().contains(linkText.toLowerCase())
	}
	element.click()
}

Then(~"I cannot follow \"(.*)\"") { String link ->
	try {
		WebElement element = browser.findElement(By.linkText(link))
		Assert.fail()
	} catch (org.openqa.selenium.NoSuchElementException nse) {
		assertNotNull(nse)
	} catch (Exception e) {
		Assert.fail()
	}
}

Given(~"I have cleared and filled in \"(.*)\" with \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
	fieldElement.clear()
	fieldElement.sendKeys(text)
}

When(~"I fill in \"(.*)\" with \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
    fieldElement.sendKeys(text)
}

When(~"I delete and fill in \"(.*)\" with \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
	fieldElement.clear()
    fieldElement.sendKeys(text)
}

When(~"I fill in \"(.*)\" with enter") { String field ->
    fieldElement = browser.findElement(By.name(field))
    fieldElement.sendKeys("\n")
}

When(~"I select \"(.*)\" from \"(.*)\"") { String value, String field ->
    fieldElement = browser.findElement(By.name(field))
	elements = fieldElement.findElements(By.tagName('option')).findAll { it -> it.getText().equals(value) }
	elements[0].click()
}

When(~"I select radiobutton \"(.*)\" from \"(.*)\"") { String value, String field ->
    List<WebElement> radioButtons = browser.findElements(By.name(field))
    for (WebElement radio: radioButtons)
    {
       if (radio.getAttribute("value").equals(value))
       {
           radio.click();
       }
    }
}

When(~"I press \"(.*)\"") { String button ->
    browser.findElementById(button).click()
}

Then(~"I cannot press \"(.*)\"") { String button ->
	try {
		WebElement element = browser.findElementById(button).click()
		Assert.fail()
	} catch (org.openqa.selenium.NoSuchElementException nse) {
		assertNotNull(nse)
	} catch (Exception e) {
		Assert.fail()
	}
}

When(~"I press browser back button"){
	browser.navigate().back()
}

Then(~"I navigate to \"(.*)\"") { String text ->
    browser.get(text)
}

Then(~"I should see \"(.*)\"") { String text ->
    assertThat(browser.findElementByTagName('body').text, containsString(text))
}

Then(~"I should see plain \"(.*)\"") { String text ->
    assertThat(browser.getPageSource(), containsString(text))
}

Then(~"I should see in \"(.*)\" phrase \"(.*)\"") { String elementId, String phrase ->
	element = browser.findElementById(elementId)
	assertNotNull(element)
	assertTrue(element.isDisplayed())
    assertThat(element.text, containsString(phrase))
}

Then(~"I should see words \"(.*)\"") { String text ->
	bodyText = browser.findElementByTagName('body').text
	text.split().each { word ->
		assertThat(bodyText, containsString(word))
	}
}

Then(~"I should have \"(.*)\" in text field named \"(.*)\"") { String text, String fieldname ->
	fieldElement = browser.findElement(By.name(fieldname))
    assertThat(fieldElement.getAttribute('value'), containsString(text))
}

Then(~"There must be an ID \"(.*)\" which is (visible|hidden)") { String elementId, String shown ->
	element = browser.findElementById(elementId)
    assertNotNull(element)
	assertEquals(shown,(element.isDisplayed() ? 'visible' : 'hidden'))
}

Then(~"There must be an ID \"(.*)\"") { String elementId ->
    assertNotNull(browser.findElementById(elementId))
}

Then(~"There must be an? (.+) with class \"(.*)\"") { String tagName, String className ->
    def elems = browser.findElementsByCssSelector(tagName+"."+className)
	assertNotNull(tagName+"."+className + " not found", elems)
	assertTrue("should have only one", elems.size()==1)
}

Then(~"There must be (\\d+) (.+) with class \"(.*)\"") { String number, String tagName, String className ->
	def n = Integer.parseInt(number)
    def elems = browser.findElementsByCssSelector(tagName+"."+className)
	assertNotNull(tagName+"."+className + " not found", elems)
	assertTrue("should have only one", elems.size()==n)
}

Then(~"I clear \"(.*)\"") { String field ->
	fieldElement = browser.findElement(By.name(field))
	fieldElement.clear()
}

Then(~"I enable javascript")
{ 
	browser.setJavascriptEnabled(true)
}

Then(~"I disable javascript")
{
	browser.setJavascriptEnabled(false)
}

Then(~"I should see \"(.*)\" with value \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
	assertThat(fieldElement.getAttribute('value'), containsString(text))
}

Then(~"I should see \"(.*)\" selected with value \"(.*)\"") { String field, String text ->
	fieldElement = browser.findElement(By.name(field))
	selectedOptions = fieldElement.findElements(By.tagName('option')).findAll{ it -> it.getAttribute('selected') != null && it.getAttribute('selected') }
	if (selectedOptions.size() == 0) {
		selectedOptions = [fieldElement.findElement(By.tagName('option'))]
	}
	assertEquals(text,selectedOptions[0].getAttribute('value'))
}

Then(~"I select file \"(.*)\" from \"(.*)\"") { String filePath, String field ->
	fieldElement = browser.findElement(By.name(field))
	fieldElement.sendKeys(new File(filePath).getAbsolutePath())
}

Then(~"I should see table \"(.*)\" with contents") { String tableId, cuke4duke.Table table ->
	webTable = browser.findElementsByCssSelector("table#${tableId} tbody tr").collect {
        def cols = it.findElementsByTagName('td')
        [cols[0].text, cols[1].text]
	}
	table.diffLists(webTable)
}


Then(~"I should see a 3 column table \"(.*)\" with contents") { String tableId, cuke4duke.Table table ->
	webTable = browser.findElementsByCssSelector("table#${tableId} tbody tr").collect {
		def cols = it.findElementsByTagName('td')
		[cols[0].text, cols[1].text, cols[2].text]
	}
	table.diffLists(webTable)
}

Then(~"I should see a 4 column table \"(.*)\" with contents") { String tableId, cuke4duke.Table table ->
	webTable = browser.findElementsByCssSelector("table#${tableId} tbody tr").collect {
		def cols = it.findElementsByTagName('td')
		[cols[0].text, cols[1].text, cols[2].text, cols[3].text]
	}
	table.diffLists(webTable)
}

Then(~"I click tree node \"(.*)\"") { String nodeLabel ->
	def elem = browser.findElements(By.xpath("//body/div//ul/li[contains(a,'${nodeLabel}')]")).get(0)
	assertNotNull("Tree label " + nodeLabel + " not found", elem)
	elem.findElement(By.tagName("ins")).click()
}

Then(~"I print the page") {
	println "*** BROWSER LOCATION: [" + browser.getCurrentUrl() + "] ***"
	println browser.getPageSource()
	println "***"
}

Then(~"I print the page to \"(.*)\"") { String fname ->
	println "*** BROWSER LOCATION: [" + browser.getCurrentUrl() + "] ***"
	def txt = browser.getPageSource()
	(new File(fname)) << txt
}

Then(~"I navigate back") {
	browser.navigate().back()
}

Then(~"I wait for ajax") {
	Thread.sleep(5000)
}

Then(~"I wait 10mins") {
	Thread.sleep(600000)
}


