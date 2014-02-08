package com.asu.ss.secure_banking_system.model;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class dbhash {
	
	public static void main(String[] args) throws NoSuchAlgorithmException
	{
		String password="admin1";
		String pass=hash(password);
		System.out.println(pass);
		
	}
	public static String hash(String str) throws NoSuchAlgorithmException
	{
		String md5;
		MessageDigest md=MessageDigest.getInstance("MD5");
		md.update(str.getBytes(),0,str.length());
		md5=new BigInteger(1, md.digest()).toString(16);
		return md5;
		
	}

}
