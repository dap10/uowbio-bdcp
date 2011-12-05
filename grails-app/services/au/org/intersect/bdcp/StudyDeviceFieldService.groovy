package au.org.intersect.bdcp

class StudyDeviceFieldService {

    static transactional = false

    def save(def params) 
    {
        StudyDevice.withTransaction { status ->
            
            def studyDeviceInstance = StudyDevice.link(Study.findById(params.study.id),Device.findById(params.device.id))
            def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
            def allValid = true
            def studyDeviceFieldInstance = []
            for (int i=0; i < deviceFields.size(); i++)
            {
                studyDeviceFieldInstance[i] = new StudyDeviceField(params["studyDeviceFields["+i+"]"])
                studyDeviceInstance?.addToStudyDeviceFields(studyDeviceFieldInstance[i])
                deviceFields[i]?.addToStudyDeviceFields(studyDeviceFieldInstance[i])
                if (!studyDeviceFieldInstance[i].validate())
                {
                    allValid = false
                }
        
            }
                    
            if (allValid)
            {
                if (saveAllStudyDeviceFields(studyDeviceFieldInstance))
                {
                    return [successful: true, studyDeviceFields:studyDeviceFieldInstance, studyDeviceInstance:studyDeviceInstance]
                }
                else
                {
                    
                    status.setRollbackOnly()
                    return [successful: false, studyDeviceFields:studyDeviceFieldInstance, studyDeviceInstance:studyDeviceInstance]
                }
            }
            else 
            {
                    status.setRollbackOnly()
                    return [successful: false, studyDeviceFields:studyDeviceFieldInstance, studyDeviceInstance:studyDeviceInstance]
            }
        }
    }
    
    def update(def params)
    {
        
        StudyDevice.withTransaction { status ->
            
            def studyDeviceInstance = StudyDevice.findByStudyAndDevice(Study.findById(params.study.id),Device.findById(params.device.id))
            def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
            def allValid = true
            def studyDeviceFields = []
            
            for (int i=0; i < deviceFields.size(); i++)
            {
                
                if (params["studyDeviceFields["+i+"]"] != null)
                {
                    studyDeviceFields[i] = StudyDeviceField.get(params["studyDeviceFields["+i+"]"]?.id)
                    if (studyDeviceFields[i])
                    {
                        studyDeviceFields[i].properties = params["studyDeviceFields["+i+"]"]
                    }
                    else
                    {
                        studyDeviceFields[i] = new StudyDeviceField(params["studyDeviceFields["+i+"]"])
                        studyDeviceInstance?.addToStudyDeviceFields(studyDeviceFields[i])
                        deviceFields[i]?.addToStudyDeviceFields(studyDeviceFields[i])
                    }
                    
                }
            }
            
             if (validStudyDeviceFields(studyDeviceFields, params)) {
                if (laterVersion(studyDeviceFields, params))
                {
                            studyDeviceFields.sort {x,y -> x.deviceField.dateCreated <=> y.deviceField.dateCreated}
                            status.setRollbackOnly()
                            return [successful:false, studyDeviceFields: studyDeviceFields, deviceFields: deviceFields, studyDeviceInstance: studyDeviceInstance]
                 }
                
                if (saveAllStudyDeviceFields(studyDeviceFields)) {
                    return [successful:true, studyDeviceFields: studyDeviceFields, deviceFields: deviceFields, studyDeviceInstance: studyDeviceInstance]
                }
                else {
                    studyDeviceFields.sort {x,y -> x.deviceField.dateCreated <=> y.deviceField.dateCreated}
                    return [successful:false, studyDeviceFields: studyDeviceFields, deviceFields: deviceFields, studyDeviceInstance: studyDeviceInstance]
                 }
            }
            else {
                    studyDeviceFields.sort {x,y -> x?.deviceField?.dateCreated <=> y?.deviceField?.dateCreated}
                    return [successful:false, studyDeviceFields: studyDeviceFields, deviceFields: deviceFields, studyDeviceInstance: studyDeviceInstance]
            }
        }
    }
    
    def validStudyDeviceFields(studyDeviceFields, params)
    {
        def studyDeviceInstance = StudyDevice.link(Study.findById(params.study.id),Device.findById(params.device.id))
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        def allValid = true
        
        for (int i=0; i < deviceFields.size(); i++)
        {
            if (!studyDeviceFields[i].validate())
            {
                allValid = false
            }
        }
        
        return allValid
    }
    
    def laterVersion(studyDeviceFields, params)
    {
        def isLaterVersion = false
        def deviceFields = DeviceField.findAllByDevice(Device.findById(params.device.id))
        for (int i=0; i < deviceFields.size(); i++)
                        {
                            
                            if (params["studyDeviceFields["+i+"]"].version) {
                                def version = params["studyDeviceFields["+i+"]"].version.toLong()
                                if (studyDeviceFields[i].version > version)
                                {
                                    studyDeviceFields[i].errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'studyDeviceField.label', default: 'StudyDeviceField')] as Object[], "Another user has updated this StudyDeviceField while you were editing")
                                    studyDeviceFields[i].hasErrors()
                                    isLaterVersion = true
                                }
                            }
                        }
        return isLaterVersion
    }
    
    
    def saveAllStudyDeviceFields(studyDeviceFields)
    {
        studyDeviceFields.every {
           if (!it.save())
           {
               return false
           }
        }
        return true
    }
}
