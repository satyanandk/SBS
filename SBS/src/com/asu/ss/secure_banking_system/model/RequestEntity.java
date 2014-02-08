package com.asu.ss.secure_banking_system.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbs.model.user.User;

@Entity
@Table(name="REQUEST")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",
discriminatorType = DiscriminatorType.STRING
)

@DiscriminatorValue("RE")
public class RequestEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "request_id")
	private int requestID;
	
	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}	
	
	@Column(name = "request_time")
	private Date requestTime;



	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name="requested_by")
	private User requestedBy;
	

	public User getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(User requestedBy) {
		this.requestedBy = requestedBy;
	}
}