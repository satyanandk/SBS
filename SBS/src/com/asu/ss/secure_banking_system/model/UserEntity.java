package com.asu.ss.secure_banking_system.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="USER_AND_REQUEST")
public class UserEntity {

@Id
@Column(name="USER_ID")
private String userID;
/**
 * @return the userID
 */
public String getUserID() {
	return userID;
}
/**
 * @param userID the userID to set
 */
public void setUserID(String userID) {
	this.userID = userID;
}
/**
 * @return the accounts
 */
public ArrayList<AccountEntity> getAccounts() {
	return accounts;
}
/**
 * @param accounts the accounts to set
 */
public void setAccounts(ArrayList<AccountEntity> accounts) {
	this.accounts = accounts;
}


private ArrayList<AccountEntity> accounts;

@ManyToOne
@JoinColumn(name="role_id")
private RoleEntity role;

public RoleEntity getRole() {
	return role;
}
public void setRole(RoleEntity role) {
	this.role = role;
}


}
