package au.org.intersect.bdcp

class Project
{

	String projectTitle
	String researcherName
	String studentNumber
	String degree
	Date startDate
	Date endDate
	String description
	String supervisors
	UserStore publishingUser
	
	static hasMany = [studies: Study, collaboratorStudies: Study]
	
	static belongsTo = [owner:UserStore]
	
	String toString()
	{
		return "${projectTitle}"
	}
	
	static mapping = 
	{
		studies cascade: "all-delete-orphan"
	}
	
	static constraints =
	{
		projectTitle(blank:false, size:1..1000)
		researcherName(blank:false, size:1..1000)
		studentNumber(nullable:true, size:0..1000)
		degree(blank:false, size:1..1000)
		startDate(nullable:false)
		endDate(nullable:false)
		description(blank:false, size:1..1000)
		supervisors(blank:false, size:1..1000)
		owner(nullable:false)
		publishingUser(nullable:true)
	}
}
