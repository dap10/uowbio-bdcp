package au.org.intersect.bdcp

import java.io.Serializable

class DeviceManualForm implements Serializable
{
	String formName
	String form
	String fileName
	String contentType
	String fileExtension
	String storedFileName
	String toString()
	{
		return "${formName}"
	}
	
	static belongsTo = [device:Device]
	
	static constraints =
	{
		formName(blank:false, size:1..255, matches:/^[a-zA-Z0-9-_\s]+/, uniqueIgnoreCase:[scope:"device"])
		form(nullable:true)
		contentType(nullable:true)
		fileExtension(nullable:true)
		fileName(nullable:false, blank:false, size:1..1000)
        storedFileName(nullable:true)
	}
	
}
