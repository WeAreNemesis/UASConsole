package com.cg.uas.bean;

public class Participant {// shailesh
	private String rollNo;
	private String emailId;
	private String ApplicationId;
	private String scheduledProgramId;

	public Participant() {

	}

	public Participant(String rollNo, String emailId, String applicationId, String scheduledProgramId) {
		super();
		this.rollNo = rollNo;
		this.emailId = emailId;
		ApplicationId = applicationId;
		this.scheduledProgramId = scheduledProgramId;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getApplicationId() {
		return ApplicationId;
	}

	public void setApplicationId(String applicationId) {
		ApplicationId = applicationId;
	}

	public String getScheduledProgramId() {
		return scheduledProgramId;
	}

	public void setScheduledProgramId(String scheduledProgramId) {
		this.scheduledProgramId = scheduledProgramId;
	}

	@Override
	public String toString() {
		return "Participant [rollNo=" + rollNo + ", emailId=" + emailId + ", ApplicationId=" + ApplicationId
				+ ", scheduledProgramId=" + scheduledProgramId + "]";
	}

}
