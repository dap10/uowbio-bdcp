package au.org.intersect.bdcp

class StudyDevice {

    Study study
    Device device
    
    static hasMany = [studyDeviceFields: StudyDeviceField]
    
    // TODO: FIX - I don't think these two methods are necessary, why? (C.Aya)
    static StudyDevice link(Study study,Device device) {
        StudyDevice studyDevice = StudyDevice.findByStudyAndDevice(study,device)
        if (!studyDevice) {
            studyDevice = new StudyDevice()
            study?.addToStudyDevices(studyDevice)
            device?.addToStudyDevices(studyDevice)
            studyDevice.save()

        }
        return studyDevice
    }
    static void unlink(Study study,Device device) {
        StudyDevice studyDevice = StudyDevice.findByStudyAndDevice(study,device)
        if (studyDevice) {
            study?.removeFromStudyDevices(studyDevice)
            device?.removeFromStudyDevices(studyDevice)
            studyDevice.delete()
        }
    }

    static mapping = {
        studyDeviceFields cascade:'all-delete-orphan'
    }

    // TODO: FIX - none shouldn't be nullable
    static constraints = {
        study(nullable: true)
        device(nullable: true)
    }
}
