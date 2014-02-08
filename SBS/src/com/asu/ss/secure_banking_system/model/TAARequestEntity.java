package com.asu.ss.secure_banking_system.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sbs.model.user.User;


	@Entity
	@DiscriminatorValue("TRE")
	public class TAARequestEntity extends RequestEntity{	
		
		
		@Column(name="description")
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}


		@ManyToOne(cascade = {CascadeType.PERSIST})
		@JoinColumn(name="assigned_to")
		private User assignedTo;
		public User getAssignedTo() {
			return assignedTo;
		}

		public void setAssignedTo(User assignedTo) {
			this.assignedTo = assignedTo;
		}


		@Column(name="is_validated")
		private boolean isValidated;
		public boolean isValidated() {
			return isValidated;
		}

		public void setValidated(boolean isValidated) {
			this.isValidated = isValidated;
		}

		@Column(name="is_assigned")
		private boolean isAssigned;
		public boolean isAssigned() {
			return isAssigned;
		}

		public void setAssigned(boolean isAssigned) {
			this.isAssigned = isAssigned;
		}	
}
