package com.cg.uas.service;

import java.util.ArrayList;

import com.cg.uas.bean.Application;
import com.cg.uas.bean.ProgramsScheduled;
import com.cg.uas.dao.ApplicationDaoImpl;
import com.cg.uas.dao.ProgramsScheduledDaoImpl;
import com.cg.uas.exception.ApplicationAlreadyExistsException;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.NoProgramsAvailableException;
import com.cg.uas.exception.NoSuchApplication;

public class ApplicationServiceImpl implements ApplicationService {

	private static int applicationId = 6;

	private static ProgramsScheduledDaoImpl psdi = new ProgramsScheduledDaoImpl();
	private static ApplicationDaoImpl adi = new ApplicationDaoImpl();

	@Override
	public ArrayList<ProgramsScheduled> getScheduledProgramsList() throws NoProgramsAvailableException {
		ArrayList<ProgramsScheduled> result = psdi.reportAll();
		if (result == null) {
			throw new NoProgramsAvailableException();
		} else {
			return result;
		}
	}

	@Override
	public int applyOnline(Application a)
			throws ApplicationAlreadyExistsException, InvalidProgramException {
		ProgramsScheduled ps = psdi.readProgramsScheduled(a.getScheduledProgramId());

		if (ps == null) {
			throw new InvalidProgramException();
		}

		a.setApplicationId(Integer.toString(applicationId));
		System.out.println("application started.");
		try {
			boolean result = adi.createApplication(a);
			applicationId++;
			return applicationId - 1;
		} catch (ApplicationAlreadyExistsException e) {
			throw e;
		}

	}

	@Override
	public String applicationStatus(String applicationId) throws NoSuchApplication {
		try {
			Application a = adi.readApplication(applicationId);
			return a.getStatus();
		} catch (NoSuchApplication e) {
			throw e;
		}
	}
}
