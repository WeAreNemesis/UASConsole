package com.cg.uas.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.cg.uas.bean.Application;
import com.cg.uas.exception.NoSuchApplication;

public interface MemberOfAdmissionCommittee {
	ArrayList<Application> applicationsOfProgram(String scheduledProgramId);
	
	boolean updateApplicationStatus(String applicationId, int status) throws NoSuchApplication;
	
	boolean scheduleInterview(String applicationId, LocalDate date) throws NoSuchApplication;
	
}
