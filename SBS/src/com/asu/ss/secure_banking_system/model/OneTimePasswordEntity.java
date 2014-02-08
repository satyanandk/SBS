package com.asu.ss.secure_banking_system.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Embeddable
class OTPKey implements Serializable
{

	
	@Column(name = "user_id",length=15)
	private String userID;
	@Column(name="otp_code",length=7)
	private String otpCode;
	public OTPKey(String userID, String otpCode) {
		super();
		this.userID = userID;
		this.otpCode = otpCode;
	}
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
	 * @return the otpCode
	 */
	public String getOtpCode() {
		return otpCode;
	}
	/**
	 * @param otpCode the otpCode to set
	 */
	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}
	
}

@Entity
@Table(name="USER_OTP_TBL")
public class OneTimePasswordEntity {

	@EmbeddedId
	private OTPKey otpKey;
	@Column(name="otp_generated_date")
	private Date otpGeneratedDate;
	/**
	 * @return the otpKey
	 */
	public OTPKey getOtpKey() {
		return otpKey;
	}
	/**
	 * @param otpKey the otpKey to set
	 */
	public void setOtpKey(OTPKey otpKey) {
		this.otpKey = otpKey;
	}
	/**
	 * @return the otpGeneratedDate
	 */
	public Date getOtpGeneratedDate() {
		return otpGeneratedDate;
	}
	/**
	 * @param otpGeneratedDate the otpGeneratedDate to set
	 */
	public void setOtpGeneratedDate(Date otpGeneratedDate) {
		this.otpGeneratedDate = otpGeneratedDate;
	}
	
}
