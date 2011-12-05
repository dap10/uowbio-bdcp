package au.org.intersect.bdcp

class FileUtils
{
	public boolean renameDirectory(String fromDir, String toDir) {
		
			File from = new File(fromDir);
		
			if (!from.exists() || !from.isDirectory()) 
			{
		
			  return false;
			}
		
			File to = new File(toDir);
		
			//Rename
			if (from.renameTo(to))
			{
			  return true
			}
			else
			{
			  return false
			}  
		
		  }
}
