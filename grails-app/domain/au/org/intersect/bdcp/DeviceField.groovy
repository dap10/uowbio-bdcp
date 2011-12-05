package au.org.intersect.bdcp

import au.org.intersect.bdcp.enums.FieldType
import au.org.intersect.bdcp.util.TextUtils

class DeviceField 
{

    String fieldLabel
    FieldType fieldType
    String fieldOptions
	String staticContent
    boolean mandatory
    
    // automatically updated by GORM
    Date dateCreated
    
    // automatically updated by GORM
    Date lastUpdated
    
    static belongsTo = [device: Device]
    
    static hasMany = [studyDeviceFields: StudyDeviceField]
    
    static constraints = 
    {
        fieldLabel(blank:false, size:1..1000)
        fieldType(nullable:false)
	// 10485760 : Maximum size in Postgres, please keep it
	staticContent(maxSize: 10485760,nullable:true, validator:{val, obj ->
		return obj.fieldType != FieldType.STATIC_TEXT || TextUtils.isNotEmpty(val) ? true : ['nullable'] 
		})
        
        fieldOptions(nullable:true, validator: { val, obj ->
			return [FieldType.DROP_DOWN, FieldType.RADIO_BUTTONS].contains(obj.fieldType) ? FieldTypeUtils.validFieldOptions(val) : true;
          })
        mandatory(nullable: false)
    }
    
    String toString()
    {
        fieldLabel
    }
    
    String mandatoryStatus()
    {
		return FieldTypeUtils.mandatoryStatus(fieldType, mandatory)
    }
        
    def getFieldOptionsList()
    {
		return FieldTypeUtils.getFieldOptionsList(fieldOptions)
    }
}
