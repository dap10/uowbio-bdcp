package au.org.intersect.bdcp

class Device {

    String name
    String description
    String manufacturer
    String locationOfManufacturer
    String modelName
    String serialNumber
	String uowAssetNumber
    Date dateOfPurchase
    Date dateOfDelivery
    String purchasePrice
    String vendor
    String fundingSource
	String maintServiceInfo
	List deviceFields
    
    static belongsTo = [deviceGroup: DeviceGroup]
    
    static constraints = 
    {
        name(blank:false, size:1..1000, uniqueIgnoreCase:[scope:"none"])
        description(blank:false, size:1..1000)
        manufacturer(blank:false, size:1..1000)
        locationOfManufacturer(nullable:true, size:1..1000)
        modelName(blank:false, size:1..1000)
        serialNumber(nullable:true, size:1..1000)
		uowAssetNumber(nullable:true, size:0..1000)
        dateOfPurchase(nullable:false)
        dateOfDelivery(nullable:false)
        purchasePrice(nullable:true, size:1..1000)
        vendor(nullable:true, size:1..1000)
        fundingSource(nullable:true, size:1..1000)
		maintServiceInfo(nullable:true, size:0..1000)
    }
    
    static hasMany = [deviceFields: DeviceField, studyDevices: StudyDevice, deviceManualForms:DeviceManualForm]
	
	static mapping = {deviceFields cascade: "all-delete-orphan"}
    
    String toString()
    {
        name?.trim()
    }
    
    String getName()
    {
        name?.trim()
    }
    
    void setName(String name)
    {
        this.name = name?.trim()
    }
}
