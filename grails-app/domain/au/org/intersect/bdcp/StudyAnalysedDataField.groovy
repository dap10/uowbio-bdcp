package au.org.intersect.bdcp

import java.text.NumberFormat
import java.util.Date

import org.joda.time.*
import org.joda.time.contrib.hibernate.*

import au.org.intersect.bdcp.enums.FieldType
import au.org.intersect.bdcp.util.TextUtils

class StudyAnalysedDataField {

	ResultsDetailsField resultsDetailsField
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
    
    static belongsTo = [studyAnalysedData: StudyAnalysedData]
    
    static mapping = {
        date type: PersistentLocalDate
        time type: PersistentLocalTimeAsTime
        
        table 'study_analysed_field'
        columns
        {
            date column: "date_field"
        }
    }
    
	def String toString() {
		def prop = resultsDetailsField.fieldType.valueColumn
		return "SAF[" + (id == null? '(new)' : id) + ", type:" + resultsDetailsField.fieldType + 
		([FieldType.RADIO_BUTTONS, FieldType.DROP_DOWN].contains(resultsDetailsField.fieldType) ? ', opts:' + (resultsDetailsField.getFieldOptionsList()) : '') + 
		', value:' + (prop != null ? this."${prop}" : "N/A")+  ']'
	}
	
    static def validator = { column, val, obj ->
        def ft = obj.resultsDetailsField.fieldType
		if (!column.equals(ft.valueColumn)) { 
			return
		}
        return ft.fieldValidate(obj.resultsDetailsField, val)
    }

    static constraints = {
        resultsDetailsField(nullable:false)
        text(blank:true, nullable:true, validator:validator.curry('text'))
        textArea(blank:true, nullable:true, validator:validator.curry('textArea'))
        numeric(blank:true, nullable:true, validator:validator.curry('numeric'))
        date(blank:true, nullable:true, validator:validator.curry('date'))
        time(blank:true, nullable:true, validator:validator.curry('time'))
        radioButtonsOption(blank:true, nullable:true, validator:validator.curry('radioButtonsOption'))
        dropDownOption(blank:true, nullable:true, validator:validator.curry('dropDownOption'))
    }

}
