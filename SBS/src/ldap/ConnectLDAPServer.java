package ldap;


import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

public class ConnectLDAPServer {
   
	public static LDAPConnection getLDAPConnection()
	{
		try{
		
			LDAPConnection connection = new LDAPConnection("localhost", 389,
		     "cn=admin,dc=example,dc=com", "Admin");
			return connection;
	
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return null;
	}
}
