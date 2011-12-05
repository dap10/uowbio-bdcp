package au.org.intersect.bdcp

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

class Participant
{
	
	String identifier
	
	String toString()
	{
		return "${identifier}"
	}

	
	static hasMany = [participantForms:ParticipantForm]
	
	static mapping =
	{
		participantForms cascade:"all-delete-orphan"
	}

	static belongsTo = [study:Study]
	
	static constraints =
	{ 
		identifier(blank:false, unique:true, size:1..1000)
	}
		
	def getParticipantFormsList() {
		return LazyList.decorate(
			  participantForms,
			  FactoryUtils.instantiateFactory(ParticipantForm.class))
	}
	
}
