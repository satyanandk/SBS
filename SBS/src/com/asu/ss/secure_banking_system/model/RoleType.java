package com.asu.ss.secure_banking_system.model;

public enum RoleType {

	CORPORATE_LEVEL_OFFICER(6),
	MANAGER(5),
	REGULAR_EMPLOYEE(4),
	SYSTEM_ADMIN(3),
	EXTERNAL_REGULAR_USER(2),
	EXTERNAL_MERCHANT_USER(1);
	
	private final int value;       

    private RoleType(int s) {
    	value = s;
    }
    
    public int getValue()
    {
    	return value;
    }

}
