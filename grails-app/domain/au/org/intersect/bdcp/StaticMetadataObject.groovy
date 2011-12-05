package au.org.intersect.bdcp

class StaticMetadataObject {
	
	private static final String FILEPREFIX = "static"
	
	String shortDescription
	
	/**
	 * Something to identify the static field to the user in user interface or in database.
	 */
	String description
	
	/**
	 * this is rif-cs registryObjects content, which will contain a single registryObject type and no related objects
	 */
	String xmlContent

	/**
	 * for grails timestamping	
	 */
	Date dateCreated
	Date lastUpdated
	
	static constraints =
	{
		description(blank:false, nullable:false, maxSize:1024)
		// 10485760 : Maximum size in Postgres, please keep it
		xmlContent(blank:false, nullable:false, maxSize:10485760)
	}

}
