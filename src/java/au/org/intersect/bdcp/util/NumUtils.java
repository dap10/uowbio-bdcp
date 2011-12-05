package au.org.intersect.bdcp.util;


public class NumUtils {
	
	/**
	 * Checks if value is a number
	 * 
	 * @param str string to search and confirm that it is a number
	 * @return true if the string is not empty and is a number
	 */
	public static boolean isNumber(String str) {
		if (str == null || str.length() == 0)
		{
			return false;
		}
		for (int i=0; i< str.length(); i++)
		{
		  //If we find a non-digit character we return false.
            if (!Character.isDigit(str.charAt(i)))
                return false;
		}
		
		return true;
	}

}
