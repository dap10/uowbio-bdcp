package au.org.intersect.bdcp

/**
 * Represents a folder of analysed data and holds the results fields
 * @author carlos
 *
 */
class StudyAnalysedData {

    Study study
    String folder
    
    static hasMany = [studyAnalysedDataFields: StudyAnalysedDataField]
    
    static mapping = {
        studyDeviceFields cascade:'all-delete-orphan'
    }
    
    static constraints = {
        study(nullable: false)
        folder(nullable: false, blank:false)
    }
	
	public String toString() {
		return "[SAD: id=" + id + ", studyId:" + study.id + ", folder='" + folder + "']"
	}
}
