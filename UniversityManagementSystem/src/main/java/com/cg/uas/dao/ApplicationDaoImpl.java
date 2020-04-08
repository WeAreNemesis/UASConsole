package com.cg.uas.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.uas.bean.Application;
import com.cg.uas.exception.ApplicationAlreadyExistsException;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.NoSuchApplication;

public class ApplicationDaoImpl implements ApplicationDao {
	private static HashMap<String, Application> applications = new HashMap<String, Application>();
	private static final Logger logger = Logger.getLogger(ApplicationDaoImpl.class);

	static {
		PropertyConfigurator.configure("src/main/resources/log4j/log4j.properties");
	}

	@Override
	public Application readApplication(String applicationId) throws NoSuchApplication {
		Application a = applications.get(applicationId);
		if (a == null) {
			logger.info("Application with id: " + applicationId + " was not found");
			throw new NoSuchApplication();
		} else {
			logger.info("Application with id: " + a.getApplicationId() + " was read");
			return a;
		}

	}

	@Override
	public Application readApplicationByProgramId(String programId) throws NoSuchApplication {
		System.out.println("the id: " + programId);
		for (Application a : applications.values()) {
			if (a.getScheduledProgramId().equals(programId) && !a.getStatus().equalsIgnoreCase("confirmed")) {
				logger.info("Application with program id: " + programId + " was read");
				return a;
			}
		}
		logger.info("Application with program id: " + programId + " was not found");
		throw new NoSuchApplication();
	}

	@Override
	public ArrayList<Application> getAll() {
		List<Application> result = applications.values().stream().collect(Collectors.toList());
		logger.info(result.size() + " applications were read.");
		return new ArrayList<Application>(result);
	}

	@Override
	public boolean createApplication(Application a) throws ApplicationAlreadyExistsException {

		Application result = applications.putIfAbsent(a.getApplicationId(), a);

		if (result == null) {
			logger.info("Application with id: " + a.getApplicationId() + " was created");
			return true;
		} else {
			logger.info("Application with id: " + result.getApplicationId() + " already exists");
			throw new ApplicationAlreadyExistsException();
		}

	}

	@Override
	public boolean updateApplication(String applicationId, Application a) throws NoSuchApplication {
		if (applications.containsKey(applicationId)) {
			Application result = applications.put(applicationId, a);
			if (result != null) {
				logger.info("Application with id: " + applicationId + " was updated");
				return true;
			} else {
				logger.info("Application with id: " + applicationId + " was failed to be updated");
				return false;
			}
		} else {
			logger.info("Application with id: " + applicationId + " not found");
			throw new NoSuchApplication();
		}
	}

	@Override
	public boolean deleteApplication(String applicationId) throws NoSuchApplication {
		if (applications.containsKey(applicationId)) {
			Application a = applications.remove(applicationId);
			if (applications.containsKey(applicationId)) {
				logger.info("Application: " + applicationId + " delete failed");
				return false;
			} else {
				logger.info("Application: " + applicationId + " delete successful");
				return true;
			}
		} else {
			logger.info("Application with id: " + applicationId + " was not found");
			throw new NoSuchApplication();
		}

	}

	public static void mockData() {

		applications.put("1", new Application("1", "Siddharth", LocalDate.parse("1997-12-27"), "12th", 60,
				"Aim to be an Engineer", "siddharth@gmail.com", "A1001", "pending", LocalDate.parse("2018-12-27")));
		applications.put("2", new Application("2", "Siddharth", LocalDate.parse("1997-12-27"), "12th", 60,
				"Aim to be an Designer", "siddharth@gmail.com", "A1002", "Accepted", LocalDate.parse("2018-12-27")));
		applications.put("3", new Application("3", "Siddharth", LocalDate.parse("1997-12-27"), "12th", 60,
				"Aim to be a Developer", "siddharth@gmail.com", "A1003", "Confirmed", LocalDate.parse("2018-12-27")));
		applications.put("4", new Application("4", "Siddharth", LocalDate.parse("1997-12-27"), "12th", 60,
				"Aim to be a Artist", "siddharth@gmail.com", "A1004", "Accepted", LocalDate.parse("2018-12-27")));
		applications.put("5", new Application("5", "Siddharth", LocalDate.parse("1997-12-27"), "12th", 60,
				"Aim to be a Doctor ", "siddharth@gmail.com", "A1005", "Rejected", LocalDate.parse("2018-12-27")));
	}

	public void print() {
		System.out.println(applications);
	}

}