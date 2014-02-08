package com.asu.ss.secure_banking_system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_PWD_MAP")
public class UserIDPasswordMappingEntity {

	@Id 
	@Column(name = "user_id",length = 15)
	private String userID;
	
	@Column(name = "password", length = 16)
	private String password;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
