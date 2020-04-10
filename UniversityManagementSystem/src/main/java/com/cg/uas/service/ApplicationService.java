package com.cg.uas.service;

import java.util.ArrayList;

import com.cg.uas.bean.Application;
import com.cg.uas.bean.ProgramsScheduled;
import com.cg.uas.exception.ApplicationAlreadyExistsException;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.NoProgramsAvailableException;
import com.cg.uas.exception.NoSuchApplication;

public interface ApplicationService {
	ArrayList<ProgramsScheduled> getScheduledProgramsList() throws NoProgramsAvailableException;

	int applyOnline(Application a) throws ApplicationAlreadyExistsException,InvalidProgramException;

	String applicationStatus(String applicationId) throws NoSuchApplication;
}
