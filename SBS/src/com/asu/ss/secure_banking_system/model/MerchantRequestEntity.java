package com.asu.ss.secure_banking_system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "MER_REQ_TBL")
public class MerchantRequestEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="merchant_req_id")
	private long merchantRequestID;
	
	@Column(name="account_id", length=10)
	private String accountID;
	
	@Column(name="amount")
	private double dAmount;
	
	@Column(name="customer_user_id", length=15)
	private String customerUserID;
	
	@Column(name="merchant_user_id", length=15)
	private String merchantUserID;
	
	@Column(name="remarks", length=100)
	private String remarks;

	@Column(name="paid_flg")
	@Type(type="yes_no")
	private boolean paidFlag;
	/**
	 * @return the merchantRequestID
	 */
	public long getMerchantRequestID() {
		return merchantRequestID;
	}

	/**
	 * @param merchantRequestID the merchantRequestID to set
	 */
	public void setMerchantRequestID(long merchantRequestID) {
		this.merchantRequestID = merchantRequestID;
	}

	/**
	 * @return the accountID
	 */
	public String getAccountID() {
		return accountID;
	}

	/**
	 * @param accountID the accountID to set
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	/**
	 * @return the dAmount
	 */
	public double getdAmount() {
		return dAmount;
	}

	/**
	 * @param dAmount the dAmount to set
	 */
	public void setdAmount(double dAmount) {
		this.dAmount = dAmount;
	}

	/**
	 * @return the customerUserID
	 */
	public String getCustomerUserID() {
		return customerUserID;
	}

	/**
	 * @param customerUserID the customerUserID to set
	 */
	public void setCustomerUserID(String customerUserID) {
		this.customerUserID = customerUserID;
	}

	/**
	 * @return the merchantUserID
	 */
	public String getMerchantUserID() {
		return merchantUserID;
	}

	/**
	 * @param merchantUserID the merchantUserID to set
	 */
	public void setMerchantUserID(String merchantUserID) {
		this.merchantUserID = merchantUserID;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the paidFlag
	 */
	public boolean isPaidFlag() {
		return paidFlag;
	}

	/**
	 * @param paidFlag the paidFlag to set
	 */
	public void setPaidFlag(boolean paidFlag) {
		this.paidFlag = paidFlag;
	}
	
	

	
}
