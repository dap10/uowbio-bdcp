package au.org.intersect.bdcp.util;

public class TextUtils {
	
	/**
	 * Removes html markup (tags) and checks if not empty
	 * 
	 * @param html string to search and remove in
	 * @return true if after removing markup and &nbsp; the string is not empty
	 */
	public static boolean isNotEmpty(String html) {
		if (html == null)
		{
			return false;
		}
		StringBuffer sb = new StringBuffer();
		int pos = 0;
		int pos2 = html.indexOf('<', pos);
		while (pos2 >= 0)
		{
			sb.append(html.substring(pos, pos2));
			int i = html.indexOf('>', pos2);
			if (i >= 0)
			{
				pos = i + 1;
				pos2 = html.indexOf('<', i);
			}
			else
			{
				break;
			}
		}
		sb.append(html.substring(pos));
		String noHtml = sb.toString();
		return noHtml.replace("&nbsp;","").trim().length() > 0;
	}

}
