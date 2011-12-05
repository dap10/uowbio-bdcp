package au.org.intersect.bdcp

import grails.test.*

import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Unit tests for the {@link FileService} class
 *
 * @version $Rev: 29 $
 */
class FileServiceTests extends GrailsUnitTestCase
{
    def fileService

    /**
     * Setup operations for testing the {@link FileService} class
     */
    protected void setUp()
    {
        super.setUp()
        mockLogging(FileService, true)
        fileService = new FileService()
        def filePath = new File('grails-app/conf/Config.groovy').toURI().toURL()
        def config = new ConfigSlurper(System.properties.get('grails.env')).parse(filePath)
        ConfigurationHolder.config = config
        fileService.grailsApplication = ConfigurationHolder
    }

    /**
     * Cleanup operations performed after each test
     */
    protected void tearDown()
    {
        super.tearDown()
    }

    /**
     * Unit tests to test that the createContext method returns both a temporary file path and
     * a file file path
     */
    void testCreateContext()
    {
        def contextMock = fileService.createContext( "session")
        def tmpPath = new File( fileService.grailsApplication.config.tmp.location.toString())
        def rootPath= new File( fileService.grailsApplication.config.files.session.location.toString())
        assertEquals 'Context not created properly.','[tmpPath:' + tmpPath + ', rootPath:' + rootPath + ']', contextMock.toString()
        
    }
    
}
