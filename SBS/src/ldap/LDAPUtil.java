package ldap;

import com.unboundid.ldap.sdk.AddRequest;
import com.unboundid.ldap.sdk.BindResult;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SimpleBindRequest;

public class LDAPUtil {

	public static void main(String[]password)
	{
		LDAPUtil.checkPassword("Apps");
		LDAPUtil.createUserEntry("myusername1", "myuserpassword1", "HappyTT", 1, "My User");
	}
	
	/**
	 * @author root
	 * checks a password with username entry
	 * @param password
	 * @return boolean
	 */
	public static boolean checkPassword(String password)
	{
		try{
		LDAPConnection connection = ConnectLDAPServer.getLDAPConnection();
		String dn = "cn=Sandip Nayak,ou=sales,dc=example,dc=com";
		SimpleBindRequest bindRequest = new SimpleBindRequest(dn,password);
		// exceptions ignored for this example
		BindResult bindResult = connection.bind(bindRequest);
			if(bindResult.getResultCode().equals(ResultCode.SUCCESS)) { 
				System.out.print("Success");
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return false;
	}
	
	/**
	 * creates and user entry
	 * @param userName
	 * @param password
	 * @param ogranization
	 * @param departmentNumber
	 * @param cnName
	 * @return boolean
	 */
	public static boolean createUserEntry(String userName, 
			String password, 
			String ogranization, 
			int departmentNumber,
			String cnName)
	{
		
		String[] ldifLines =
            {
                    "dn: uid="+userName+",ou="+ogranization+", dc=example,dc=com",
                    "cn: "+cnName,
                    "departmentNumber: "+departmentNumber,
                    "sn: "+cnName,
                    "uid: "+userName,
                    "userPassword: "+password,
                    "objectClass: inetOrgPerson"
                    
            };
		try{
			LDAPConnection connection = ConnectLDAPServer.getLDAPConnection();
			LDAPResult result = connection.add(new AddRequest(ldifLines));
			if(result.hasResponseControl())
			{
				return true;
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			return false;
		}
	}
}
