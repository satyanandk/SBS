package com.sbs.model.message;


import com.asu.ss.secure_banking_system.model.OneTimePasswd;
import com.sbs.model.user.User;

public class userandpassword {
	private User user;
	private OneTimePasswd otp;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public OneTimePasswd getOtp() {
		return otp;
	}
	public void setOtp(OneTimePasswd otp) {
		this.otp = otp;
	}

}
