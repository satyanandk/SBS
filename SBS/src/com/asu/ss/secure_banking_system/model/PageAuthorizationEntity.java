package com.asu.ss.secure_banking_system.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbs.model.user.User;

@Entity
@Table(name="PAGE_AUTHORIZATION")
public class PageAuthorizationEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "page_authorization_id")
	private int pageAuthorizationID;
	
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name = "create_account_authorized")
	private boolean isCreateAccountAuthorized;
	
	@Column(name = "credit_authorized")
	private boolean isCreditAuthorized;
	
	@Column(name = "debit_authorized")
	private boolean isDebitAuthorized;
	
	@Column(name = "financial_otp_authorized")
	private boolean isFinancialOTPAuthorized;
	
	@Column(name = "payment_conf_authorized")
	private boolean isPaymentConfAuthorized;
	
	@Column(name = "request_payment_authorized")
	private boolean isRequestPaymentAuthorized;
	
	@Column(name = "result_page_authorized")
	private boolean isResultPageAuthorized;
	
	@Column(name = "transfer_authorized")
	private boolean isTransferAuthorized;
	
	@Column(name = "admin_notification_authorized")
	private boolean isAdminNotificationAuthorized;
	
	@Column(name = "assign_role_authorized")
	private boolean isAssignRoleAuthorized;
	
	@Column(name = "assign_taa_authorized")
	private boolean isAssignTAAAuthorized;
	
	@Column(name = "request_role_authorized")
	private boolean isRequestRoleAuthorized;
	
	@Column(name = "request_taa_authorized")
	private boolean isRequestTAAAuthorized;
	
	@Column(name = "taa_notification_authorized")
	private boolean isTAANotificationAuthorized;
	
	@Column(name = "welcome_admin_authorized")
	private boolean isWelcomeAdminAuthorized;
	


	@Column(name = "system_log_authorized")
	private boolean isSystemLogAuthorized;
	
	@Column(name = "process_taa_authorized")
	private boolean isProcessTAAAuthorized;
	//plz add here generate setter and getters
	
	@Column(name = "login_authorized")
	private boolean isLoginAuthorized;
	
	@Column(name = "forget_password_authorized")
	private boolean isForgetPasswordAuthorized;
	
	@Column(name = "new_password_setup_authorized")
	private boolean isNewPasswordSetupAuthorized;
	
	@Column(name = "profile_page_authorized")
	private boolean isProfilePageAuthorized;
	
	@Column(name = "add_user_authorized")
	private boolean isAddUserAuthorized;
	
	@Column(name = "internal_txn_authorized")
	private boolean isInternalTransactionAuthorized;
	
	@Column(name = "external_txn_authorized")
	private boolean isExternalTransactionAuthorized;
	
	@Column(name = "notification_authorized")
	private boolean isNotificationAuthorized;
	
	public boolean isNotificationAuthorized() {
		return isNotificationAuthorized;
	}

	public void setNotificationAuthorized(boolean isNotificationAuthorized) {
		this.isNotificationAuthorized = isNotificationAuthorized;
	}

	public boolean isExternalTransactionAuthorized() {
		return isExternalTransactionAuthorized;
	}

	public void setExternalTransactionAuthorized(
			boolean isExternalTransactionAuthorized) {
		this.isExternalTransactionAuthorized = isExternalTransactionAuthorized;
	}

	public boolean isInternalTransactionAuthorized() {
		return isInternalTransactionAuthorized;
	}

	public void setInternalTransactionAuthorized(
			boolean isInternalTransactionAuthorized) {
		this.isInternalTransactionAuthorized = isInternalTransactionAuthorized;
	}

	public boolean isLoginAuthorized() {
		return isLoginAuthorized;
	}

	public void setLoginAuthorized(boolean isLoginAuthorized) {
		this.isLoginAuthorized = isLoginAuthorized;
	}

	public boolean isForgetPasswordAuthorized() {
		return isForgetPasswordAuthorized;
	}

	public void setForgetPasswordAuthorized(boolean isForgetPasswordAuthorized) {
		this.isForgetPasswordAuthorized = isForgetPasswordAuthorized;
	}

	public boolean isNewPasswordSetupAuthorized() {
		return isNewPasswordSetupAuthorized;
	}

	public void setNewPasswordSetupAuthorized(boolean isNewPasswordSetupAuthorized) {
		this.isNewPasswordSetupAuthorized = isNewPasswordSetupAuthorized;
	}

	public boolean isProfilePageAuthorized() {
		return isProfilePageAuthorized;
	}

	public void setProfilePageAuthorized(boolean isProfilePageAuthorized) {
		this.isProfilePageAuthorized = isProfilePageAuthorized;
	}

	public boolean isAddUserAuthorized() {
		return isAddUserAuthorized;
	}

	public void setAddUserAuthorized(boolean isAddUserAuthorized) {
		this.isAddUserAuthorized = isAddUserAuthorized;
	}

	public boolean isDeleteUserPageAuthorized() {
		return isDeleteUserPageAuthorized;
	}

	public void setDeleteUserPageAuthorized(boolean isDeleteUserPageAuthorized) {
		this.isDeleteUserPageAuthorized = isDeleteUserPageAuthorized;
	}

	public boolean isTransferUserAuthorized() {
		return isTransferUserAuthorized;
	}

	public void setTransferUserAuthorized(boolean isTransferUserAuthorized) {
		this.isTransferUserAuthorized = isTransferUserAuthorized;
	}

	@Column(name = "delete_user_authorized")
	private boolean isDeleteUserPageAuthorized;
	
	
	@Column(name = "transfer_user_authorized")
	private boolean isTransferUserAuthorized;
		
	
	@Column(name = "contact_change_authorized")
	private boolean isContactChangeAuthorized;
	

	
	public boolean isProcessTAAAuthorized() {
		return isProcessTAAAuthorized;
	}

	public boolean isContactChangeAuthorized() {
		return isContactChangeAuthorized;
	}

	public void setContactChangeAuthorized(boolean isContactChangeAuthorized) {
		this.isContactChangeAuthorized = isContactChangeAuthorized;
	}

	public void setProcessTAAAuthorized(boolean isProcessTAAAuthorized) {
		this.isProcessTAAAuthorized = isProcessTAAAuthorized;
	}

	public boolean isSystemLogAuthorized() {
		return isSystemLogAuthorized;
	}

	public void setSystemLogAuthorized(boolean isSystemLogAuthorized) {
		this.isSystemLogAuthorized = isSystemLogAuthorized;
	}	
	
	public int getPageAuthorizationID() {
		return pageAuthorizationID;
	}

	public void setPageAuthorizationID(int pageAuthorizationID) {
		this.pageAuthorizationID = pageAuthorizationID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isCreateAccountAuthorized() {
		return isCreateAccountAuthorized;
	}

	public void setCreateAccountAuthorized(boolean isCreateAccountAuthorized) {
		this.isCreateAccountAuthorized = isCreateAccountAuthorized;
	}

	public boolean isCreditAuthorized() {
		return isCreditAuthorized;
	}

	public void setCreditAuthorized(boolean isCreditAuthorized) {
		this.isCreditAuthorized = isCreditAuthorized;
	}

	public boolean isDebitAuthorized() {
		return isDebitAuthorized;
	}

	public void setDebitAuthorized(boolean isDebitAuthorized) {
		this.isDebitAuthorized = isDebitAuthorized;
	}

	public boolean isFinancialOTPAuthorized() {
		return isFinancialOTPAuthorized;
	}

	public void setFinancialOTPAuthorized(boolean isFinancialOTPAuthorized) {
		this.isFinancialOTPAuthorized = isFinancialOTPAuthorized;
	}

	public boolean isPaymentConfAuthorized() {
		return isPaymentConfAuthorized;
	}

	public void setPaymentConfAuthorized(boolean isPaymentConfAuthorized) {
		this.isPaymentConfAuthorized = isPaymentConfAuthorized;
	}

	public boolean isRequestPaymentAuthorized() {
		return isRequestPaymentAuthorized;
	}

	public void setRequestPaymentAuthorized(boolean isRequestPaymentAuthorized) {
		this.isRequestPaymentAuthorized = isRequestPaymentAuthorized;
	}

	public boolean isResultPageAuthorized() {
		return isResultPageAuthorized;
	}

	public void setResultPageAuthorized(boolean isResultPageAuthorized) {
		this.isResultPageAuthorized = isResultPageAuthorized;
	}

	public boolean isTransferAuthorized() {
		return isTransferAuthorized;
	}

	public void setTransferAuthorized(boolean isTransferAuthorized) {
		this.isTransferAuthorized = isTransferAuthorized;
	}

	public boolean isAdminNotificationAuthorized() {
		return isAdminNotificationAuthorized;
	}

	public void setAdminNotificationAuthorized(boolean isAdminNotificationAuthorized) {
		this.isAdminNotificationAuthorized = isAdminNotificationAuthorized;
	}

	public boolean isAssignRoleAuthorized() {
		return isAssignRoleAuthorized;
	}

	public void setAssignRoleAuthorized(boolean isAssignRoleAuthorized) {
		this.isAssignRoleAuthorized = isAssignRoleAuthorized;
	}

	public boolean isAssignTAAAuthorized() {
		return isAssignTAAAuthorized;
	}

	public void setAssignTAAAuthorized(boolean isAssignTAAAuthorized) {
		this.isAssignTAAAuthorized = isAssignTAAAuthorized;
	}

	public boolean isRequestRoleAuthorized() {
		return isRequestRoleAuthorized;
	}

	public void setRequestRoleAuthorized(boolean isRequestRoleAuthorized) {
		this.isRequestRoleAuthorized = isRequestRoleAuthorized;
	}

	public boolean isRequestTAAAuthorized() {
		return isRequestTAAAuthorized;
	}

	public void setRequestTAAAuthorized(boolean isRequestTAAAuthorized) {
		this.isRequestTAAAuthorized = isRequestTAAAuthorized;
	}

	public boolean isTAANotificationAuthorized() {
		return isTAANotificationAuthorized;
	}

	public void setTAANotificationAuthorized(boolean isTAANotificationAuthorized) {
		this.isTAANotificationAuthorized = isTAANotificationAuthorized;
	}

	public boolean isWelcomeAdminAuthorized() {
		return isWelcomeAdminAuthorized;
	}

	public void setWelcomeAdminAuthorized(boolean isWelcomeAdminAuthorized) {
		this.isWelcomeAdminAuthorized = isWelcomeAdminAuthorized;
	}


}
