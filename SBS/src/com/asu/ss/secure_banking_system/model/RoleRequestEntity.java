package com.asu.ss.secure_banking_system.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbs.model.user.User;

@Entity
@DiscriminatorValue("RRE")
public class RoleRequestEntity extends RequestEntity{	
	
	@Column(name="role_id")
	private int roleID;
	
	public int getRole() {
		return roleID;
	}

	public void setRole(int roleID) {
		this.roleID = roleID;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="request_for_user")
	private User requestForUser;
	public User getRequestForUser() {
		return requestForUser;
	}

	public void setUser(User requestForUser) {
		this.requestForUser = requestForUser;
	}


	@Column(name="is_validated")
	private boolean isValidated;
	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	@Column(name="is_assigned")
	private boolean isAssigned;
	public boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}	

}
