package au.org.intersect.bdcp

import java.util.Date;

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

class Study
{

	String studyTitle
	String uowEthicsNumber
	String hasAdditionalEthicsRequirements
	String additionalEthicsRequirements
	String description
	String industryPartners
	String collaborators
	String keywords
	Date startDate
	Date endDate
	String numberOfParticipants
	String inclusionExclusionCriteria
	Boolean published
    Date dateCreated
    Date lastUpdated
   


	static hasMany = [participants:Participant, components:Component, studyDevices:StudyDevice, studyCollaborators:StudyCollaborator]
	
	static belongsTo = [project:Project]
	
	
	String toString()
	{
		return "${studyTitle}"
	}
	
	
	static mapping =
	{
		participants cascade: "all-delete-orphan"
        table 'study'
            columns 
            {
                hasAdditionalEthicsRequirements column: "has_additional_ethics_reqs"
            }
        
	}
	
	
	
	static constraints =
	{
		studyTitle(blank:false, size:1..1000, validFilename:true)
		uowEthicsNumber(blank:false, unique:true,size:1..1000)
		hasAdditionalEthicsRequirements(nullable:true,inList:["No", "Yes"])
		additionalEthicsRequirements(nullable:true, size:1..1000)
		description(blank:false, size:1..1000)
		industryPartners(nullable:true, size:1..1000)
		keywords(nullable:false, blank:false, size:1..1000)
		collaborators(nullable:true, size:1..1000)
		startDate(nullable: false)
		endDate(nullable:false)
		numberOfParticipants(nullable:true, size:1..1000)
		inclusionExclusionCriteria(nullable:true, size:1..1000)
		published(nullable:true)
	}
	
	void setUowEthicsNumber(String setUowEthicsNumber)
	{
		uowEthicsNumber = setUowEthicsNumber.trim()
	}

	String getHasAdditionalEthicsRequirements()
	{
		if (hasAdditionalEthicsRequirements == null || hasAdditionalEthicsRequirements == "No")
		{
			return "No"
		}
		else
		{
				return "Yes"
		}
	}
		
	def getParticipantsList() {
		return LazyList.decorate(
			  participants,
			  FactoryUtils.instantiateFactory(Participant.class))
	}
	
	def beforeValidate() {
		keywords = keywords == null ? null : keywords.split(',').collect({it.trim()}).findAll({ it.length() > 0 }).join(', ')
	}
	
}
