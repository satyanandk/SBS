package com.sbs.model.user;

import com.asu.ss.secure_banking_system.model.PageAuthorizationEntity;

public class User {
	private String firstname;
	private String lastname;
	private String userID;
	private String address;
	private String contact;
	private String emailid;
	private String DOB;
	private String idtype;
	private String idNo;
	private String secureQ1;
	private String secureQ2;
	private String secureQ3;
	private String secureA1;
	private String secureA2;
	private String secureA3;
	private int roleID;//for distinguish internal or external user
	private int deptID;//for distinguaish the department (only needed for internal user)
	private PageAuthorizationEntity pageAuthorization;
	
	public PageAuthorizationEntity getPageAuthorization() {
		return pageAuthorization;
	}
	public void setPageAuthorization(PageAuthorizationEntity pageAuthorization) {
		this.pageAuthorization = pageAuthorization;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getIdtype() {
		return idtype;
	}
	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getSecureQ1() {
		return secureQ1;
	}
	public void setSecureQ1(String secureQ1) {
		this.secureQ1 = secureQ1;
	}
	public String getSecureQ2() {
		return secureQ2;
	}
	public void setSecureQ2(String secureQ2) {
		this.secureQ2 = secureQ2;
	}
	public String getSecureQ3() {
		return secureQ3;
	}
	public void setSecureQ3(String secureQ3) {
		this.secureQ3 = secureQ3;
	}
	public String getSecureA1() {
		return secureA1;
	}
	public void setSecureA1(String secureA1) {
		this.secureA1 = secureA1;
	}
	public String getSecureA2() {
		return secureA2;
	}
	public void setSecureA2(String secureA2) {
		this.secureA2 = secureA2;
	}
	public String getSecureA3() {
		return secureA3;
	}
	public void setSecureA3(String secureA3) {
		this.secureA3 = secureA3;
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public int getDeptID() {
		return deptID;
	}
	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}


	
	
}
