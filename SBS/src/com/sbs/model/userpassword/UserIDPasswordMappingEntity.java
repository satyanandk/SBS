package com.sbs.model.userpassword;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "USER_PWD_MAP")
public class UserIDPasswordMappingEntity {

	@Id 
	@Column(name = "user_id",length = 15)
	private String userID;
	
	@Column(name = "password", length = 50)
	private String password;
	
	@Column(name = "block", length = 16)
	private int block;
	
	@Column(name = "answertimes", length = 16)
	private int answertimes;

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getAnswertimes() {
		return answertimes;
	}

	public void setAnswertimes(int answertimes) {
		this.answertimes = answertimes;
	}

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
