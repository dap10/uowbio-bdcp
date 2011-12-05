package au.org.intersect.bdcp

import java.io.File

import org.apache.commons.io.FileUtils

class FileService
{

    static transactional = true

    def grailsApplication
	
    def createContext( String property)
    {
        def tmpPath = new File( grailsApplication.config.tmp.location.toString())
        def rootPath = new File( grailsApplication.config.files."${property}".location.toString())
        if (!tmpPath.exists())
        {
            def ok = tmpPath.mkdirs()
            log.info("Creating directories: " + tmpPath + " ok:" + ok)
        }
        if (!rootPath.exists())
        {
            def ok = rootPath.mkdirs()
            log.info("Creating directories: " + rootPath + " ok:" + ok)
        }
        return ["tmpPath":tmpPath, "rootPath":rootPath]
    }

    def listFiles( String rootFilePath, def path)
    {
        def top = new File( rootFilePath, path)
        return [files: top.listFiles(), sessionRoot: top]
    }

    public boolean createAllFolders( String rootFilePath, def json, def destination)
    {
        return json.every { topLevel -> 
			return topLevel.findAll({
	            p,q ->
	            p.startsWith("folder")
	        }).every {
	            key, val ->
	            def path = val.replace('\\','/')
	            def tmpDir = new File( grailsApplication.config.tmp.location, destination)
	            def directory = new File(tmpDir, path)
	            return checkPathIsValid( grailsApplication.config.tmp.location, directory) && ((directory.exists() && directory.isDirectory()) || directory.mkdirs());
	        }
        }
    }

    public boolean createAllFiles( def json, def destination, def parameters)
    {
        if (parameters == null)
        {
            return false
        }
        return json.every { topLevel -> 
			return topLevel.findAll({
	            p,q ->
	            p.startsWith("file")
	        }).every{
	            key, val ->
	            def path = val.replace('\\','/')
	            def file = parameters[key]
	            def tmpDir = new File( grailsApplication.config.tmp.location, destination )
	            def filepath = new File(tmpDir, path)
				if (!checkPathIsValid( grailsApplication.config.tmp.location, filepath))
				{
					return false;
				}
	            filepath.mkdirs()
	            file.transferTo(filepath)
	            return true
	        }
        }
    }

    private boolean moveDirectoryFromTmp( String rootPath, def previous, def destination)
    {
        File oldDir = new File( grailsApplication.config.tmp.location, previous)
		if (!checkPathIsValid( grailsApplication.config.tmp.location, oldDir))
		{
			return false
		}
        File newDir = new File( rootPath, destination).getParentFile()
		if (!checkPathIsValid( rootPath, newDir))
		{
			return false;
		}
		
        try
        {
            FileUtils.copyDirectoryToDirectory(oldDir, newDir)
            FileUtils.deleteDirectory(oldDir)
            return true
        }
        catch (IOException ioException)
        {
			log.error("Unable to move directory from tmp: " + grailsApplication.config.tmp.location)
			log.error("Unable to move directory from tmp, Exception: " + ioException.toString())
        }
		finally 
		{
			return false
		}
    }

    def boolean checkIfDirectoryExists( String rootPath, String name, String path)
    {
        File directoryPath = new File(rootPath, path)
        File directory = new File(directoryPath, name)
        return directory.exists() || ((directory?.listFiles() == null || directory?.listFiles()?.isEmpty())? false: directoryPath?.listFiles().every {
            it.getName().compareToIgnoreCase(name) == 0
        })
    }

    def boolean createDirectory(String rootPath, String name, String path)
    {
        File directoryPath = new File(rootPath, path)
        File directory = new File(directoryPath, name)

        return checkPathIsValid( rootPath, directory) && (directory.getParentFile().exists() && directory.mkdir())
    }
	
	def boolean checkPathIsValid(String rootPath, File path)
	{
		File fileRootPath = new File(rootPath + "/")
		def bool = fileRootPath.isDirectory() 
		def bool1 = path.getAbsolutePath().startsWith(fileRootPath.getAbsolutePath())
		return bool && bool1
	}
	
	def File getFileReference( String rootPath, String path)
	{
        return new File( rootPath, path)
	}
}
