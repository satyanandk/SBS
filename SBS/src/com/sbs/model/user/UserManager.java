package com.sbs.model.user;
 
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.asu.ss.secure_banking_system.model.PageAuthorizationEntity;
import com.asu.ss.secure_banking_system.util.AuthorizationUtil;


public class UserManager {
	private static Session session;
	private static void createSession(){
		session = SessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
	}

	public static String createUser( String firstname, String lastname, String userID, String emailId, String address,
			String contact, String DOB, String sq1, String sq2, String sq3, String sa1, String sa2, String sa3, String IDtype, String IDno, int deptID,int roleID )
	{
		createSession();
		User user = new User();
		user.setAddress(address);
		user.setContact(contact);
		user.setDeptID(deptID);
		user.setDOB(DOB);
		user.setEmailid(emailId);
		user.setIdNo(IDno);
		user.setIdtype(IDtype);
		user.setSecureA1(sa1);
		user.setSecureA2(sa2);
		user.setSecureA3(sa3);
		user.setSecureQ1(sq1);
		user.setSecureQ2(sq2);
		user.setSecureQ3(sq3);
		user.setUserID(userID);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setRoleID(roleID);
		session.save(user);
		session.getTransaction().commit();
		session.close();
//		
//		System.out.println(sq1);
//		System.out.println(sq2);
//		System.out.println(sq3);
//		System.out.println(sa1);
//		System.out.println(sa2);
//		System.out.println(sa3);
		return user.getUserID();

		
	}
	
	public static String emailidreturn(String userID){
		createSession();
		User user = (User) session.get(User.class, userID);
		session.close();
		return user.getEmailid();
		
	}
	
	
	public static void deleteuser(String userID){
		createSession(); 
        User user = new User();
        user.setUserID(userID);
        User pe = AuthorizationUtil.loadAuthorization(user);
        session.delete(pe.getPageAuthorization());
        session.flush();
//		session.getTransaction().commit();
//		session.close();
//		createSession();

        String hql = "delete from User where userID= :userID";
        session.createQuery(hql).setString("userID", userID).executeUpdate();
//		String q = session.createQuery("delete User where user.userID = userID");
//		session.createQuery(q).setString("userID", userID).executeUpdate(); 
		session.getTransaction().commit();
		session.close();
		
	}
	public static boolean transferuser(String userID, int deptID )
	{


//		Query query = session.createQuery("update User set deptID = :deptID where userID = :userID");
//		query.setParameter("deptID", deptID);
//		query.setParameter("userID", userID);
		User u = UserManager.queryUser(userID);
		createSession(); 
		if(u==null || u.getRoleID() == 1 || u.getRoleID() == 2)
		{
			 session.close();
			 return false;
		}
		else{
			String hql = "update User set deptID = :deptID where userID = :userID";
			session.createQuery(hql).setString("userID", userID).setInteger("deptID", deptID).executeUpdate();
			session.getTransaction().commit();
		      session.close();
		      return true;
		}
		
	}
	
	public static String  userIDCheck(String userID) throws Exception
	{
		createSession();
		Object ob = session.get(User.class, userID);
		//System.out.println(ob);
		session.close();
		if(ob ==null)
			return null;
		else
			return userID;
	}
	
	public static boolean existedUserID(String userID){
		boolean b = true; 
		createSession();
		User user = (User) session.get(User.class, userID);
		if(user==null)
		{
			b=false;
		}
		
		return b;
		
		
	}
	
	public static String forgetpasswordcheck(String userID, String DOB, String secureA1, String secureA2, String secureA3) throws Exception{
		createSession();
		String message = null;
		try{
			User user = (User) session.get(User.class, userID);
			
			if(!user.getDOB().equalsIgnoreCase(DOB))
			{
				System.out.println(user.getDOB() + "aagagga" + DOB);
				message="error:not correct DOB";
			}
			if(!user.getSecureA1().equalsIgnoreCase(secureA1))
			{
				message ="error:not correct secure answer";
			}
			if(!user.getSecureA2().equalsIgnoreCase(secureA2))
			{
				message ="error:not correct secure answer";
			}
			if(!user.getSecureA3().equalsIgnoreCase(secureA3))
			{
				message ="error:not correct secure answer";
			}
		}
		catch(Exception e){}
		//Object ob = 
		session.close();
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public static User queryUser(String userID){
		createSession();
		String hql = "from User as user where user.userID=:userID";
		Query query = session.createQuery(hql);
		query.setString("userID", userID);
		List <User>list = query.list();
		User user = null;
		java.util.Iterator<User> iter = list.iterator();
		while (iter.hasNext()) {
			user = iter.next();
		}					
		session.getTransaction().commit();
		session.close();
		return user;		
	}
	public static void changeContactFunction(String userID, String contact, String address )
	{
		createSession();
		User user = (User) session.get(User.class, userID);
		user.setAddress(address);
		user.setContact(contact);
		session.update(user);
		session.getTransaction().commit();
	    session.close();
	}
	public static boolean passwordcheck(String userID, String password)
	{
		return true;
	}
	public static User retrieiveUser(String userID){
		createSession();
		User user = (User) session.get(User.class, userID);
		session.close();
		return user;
	}
	public static String getUsername(String userID)
	{
		createSession();
		User user = (User) session.get(User.class, userID);
		String username = null;
		username = user.getFirstname() + " " + user.getLastname();
		session.close();
		return username;
	}
	
	public static List<User> getUsersByDept(int deptId)
	{
		createSession();
		String hql = "from User u where u.deptID = :deptID";
		Query query = session.createQuery(hql);
		query.setInteger("deptID", deptId);
		List<User> ulist = query.list();
		session.getTransaction().commit();
		session.close();
		return ulist;
	}
	

	
}