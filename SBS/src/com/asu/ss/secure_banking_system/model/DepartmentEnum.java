package com.asu.ss.secure_banking_system.model;

public enum DepartmentEnum {


	COMPANYMANAGEMENT(6),
	HUMANRESOURCE(5),
	TRANSACTION(4),
	ITANDTECHICALSUPPORT(2),
	SALES(1),
	CUSTOMER(0);

	
	private final int value;       

    private DepartmentEnum(int s) {
    	value = s;
    }
    
    public int getValue()
    {
    	return value;
    }

}