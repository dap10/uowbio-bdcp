package au.org.intersect.bdcp.ldap


import gldapo.schema.annotation.GldapoNamingAttribute
import gldapo.schema.annotation.GldapoSynonymFor

 
class LdapUser{
	@GldapoNamingAttribute
	String uid
	@GldapoSynonymFor("uid")
	Set<String> username
	String cn
	String sn
	String givenName
	String title
	String mail
	String displayName

	def String getUserId()
	{
		def userId = this.username?.toArray()[1]
		if (userId != null)
		{
			return userId
		}
		return ""
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((givenName == null) ? 0 : givenName.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		LdapUser other = (LdapUser) obj;
		if (givenName == null) {
			if (other.givenName != null)
				return false;
		} else if (!givenName.equals(other.givenName))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	
}