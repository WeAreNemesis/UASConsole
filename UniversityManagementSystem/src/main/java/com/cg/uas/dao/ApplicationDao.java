package com.cg.uas.dao;

import java.util.ArrayList;

import com.cg.uas.bean.Application;
import com.cg.uas.exception.ApplicationAlreadyExistsException;
import com.cg.uas.exception.NoSuchApplication;

//Sidd : Interface for CRUD operations for Application Database

public interface ApplicationDao {
	Application readApplication(String applicationId) throws NoSuchApplication;

	ArrayList<Application> getAll();

	Application readApplicationByProgramId(String programId) throws NoSuchApplication;

	boolean createApplication(Application a) throws ApplicationAlreadyExistsException;

	boolean updateApplication(String applicationId, Application a) throws NoSuchApplication;

	boolean deleteApplication(String applicationId) throws NoSuchApplication;
}
