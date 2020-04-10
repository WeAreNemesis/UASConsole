package com.cg.uas.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.cg.uas.bean.Application;
import com.cg.uas.bean.ProgramsOffered;
import com.cg.uas.bean.ProgramsScheduled;
import com.cg.uas.exception.InvalidDateException;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.ProgramAlreadyExistsException;

public interface AdministrationService {

	int addProgram(ProgramsOffered po) throws ProgramAlreadyExistsException;

	int updateOfferedProgram(ProgramsOffered po) throws InvalidProgramException;

	int removeProgram(String program) throws InvalidProgramException;

	ArrayList<Application> applicationsByStatus(String status);

	ArrayList<ProgramsScheduled> reportOfScheduledProgramsByDate(LocalDate s, LocalDate e);

	void displayAllPrograms();

	boolean addScheduledProgram(ProgramsScheduled ps)
			throws ProgramAlreadyExistsException, InvalidDateException, InvalidProgramException;

}
