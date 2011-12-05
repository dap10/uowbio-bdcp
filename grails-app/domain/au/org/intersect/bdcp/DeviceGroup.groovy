package au.org.intersect.bdcp



class DeviceGroup {
    
    String groupingName

    static hasMany = [devices:Device]
        
    static constraints = 
    {
        groupingName(blank:false, size:1..1000, uniqueIgnoreCase:[scope:"none"])
    }
    
    void setGroupingName(String groupingName)
    {
        this.groupingName = groupingName?.trim()
    }
    
    String toString()
    {
        groupingName?.trim()
    }
    
    def getDevicesList() {
        
        def sortedDeviceList = devices.sort {x,y -> x.name <=> y.name}
        return sortedDeviceList
    }
}
