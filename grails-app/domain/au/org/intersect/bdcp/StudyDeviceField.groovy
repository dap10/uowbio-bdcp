package au.org.intersect.bdcp

import java.text.NumberFormat
import java.util.Date

import org.joda.time.*
import org.joda.time.contrib.hibernate.*

import au.org.intersect.bdcp.enums.FieldType
import au.org.intersect.bdcp.util.TextUtils

class StudyDeviceField {

    DeviceField deviceField 
    String text
    String textArea
    String numeric
    LocalDate date
    LocalTime time
    String radioButtonsOption
    String dropDownOption
    
    // automatically updated by GORM
    Date dateCreated
    
    // automatically updated by GORM
    Date lastUpdated
    
    static belongsTo = [studyDevice: StudyDevice]
    
    static mapping = {
        date type: PersistentLocalDate
        time type: PersistentLocalTimeAsTime
        
        table 'study_device_field'
        columns
        {
            date column: "date_field"
        }
    }
	
	def String toString() {
		def prop = deviceField.fieldType.valueColumn
		return "SDF[" + (id == null? '(new)' : id) + ", type:" + deviceField.fieldType + ', value:' + (prop != null ? this."${prop}" : "N/A")+  ']'
	}
	
    static def validator = { column, val, obj ->
        def ft = obj.deviceField.fieldType
		if (!column.equals(ft.valueColumn)) { 
			return
		}
        return ft.fieldValidate(obj.deviceField, val)
    }

    static constraints = {
        deviceField(nullable:false)
        text(blank:true, nullable:true, validator:validator.curry('text'))
        textArea(blank:true, nullable:true, validator:validator.curry('textArea'))
        numeric(blank:true, nullable:true, validator:validator.curry('numeric'))
        date(blank:true, nullable:true, validator:validator.curry('date'))
        time(blank:true, nullable:true, validator:validator.curry('time'))
        radioButtonsOption(blank:true, nullable:true, validator:validator.curry('radioButtonsOption'))
        dropDownOption(blank:true, nullable:true, validator:validator.curry('dropDownOption'))
    }
	
}
