package com.sbs.model.combined;



import com.sbs.model.user.User;

import com.sbs.model.userpassword.*;

public class UserandOTP {
	UserIDPasswordMappingEntity user;
	String otpcode;
	String comfirmpassword;


	public String getComfirmpassword() {
		return comfirmpassword;
	}
	public void setComfirmpassword(String comfirmpassword) {
		this.comfirmpassword = comfirmpassword;
	}
	public UserIDPasswordMappingEntity getUser() {
		return user;
	}
	public void setUser(UserIDPasswordMappingEntity user) {
		this.user = user;
	}
	public String getOtpcode() {
		return otpcode;
	}
	public void setOtpcode(String otpcode) {
		this.otpcode = otpcode;
	}



}
