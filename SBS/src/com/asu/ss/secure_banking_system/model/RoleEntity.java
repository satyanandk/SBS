package com.asu.ss.secure_banking_system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_ROLE")
public class RoleEntity 
{

	@Id 
	@Column(name="role_id")
	private int roleID;
	
	@Column(name="role_name", length = 40)
	private String roleName;

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
