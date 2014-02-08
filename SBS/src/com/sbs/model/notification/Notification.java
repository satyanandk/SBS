package com.sbs.model.notification;

import java.util.Date;

public class Notification {

	private int notificationId;
	private String requesterId;
	private String userId;
	private String notificationType; // T = transaction
	private int key1; // TransactionID
	private String status; // R = requested, A = Approved, D = Denied
	private Date approvedDate;
	private int timeoutDays;
	
	public Notification() {
		
	}
	
	public Notification(String userId, String requesterId, String type, int key) {
		this.userId = userId;
		this.requesterId = requesterId;
		this.notificationType = type;
		this.key1 = key;
		this.status = "R";
		this.timeoutDays = 2;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public int getKey1() {
		return key1;
	}

	public void setKey1(int key1) {
		this.key1 = key1;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public int getTimeoutDays() {
		return timeoutDays;
	}
	public void setTimeoutDays(int timeoutDays) {
		this.timeoutDays = timeoutDays;
	}
}
