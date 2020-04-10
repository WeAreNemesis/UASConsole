package com.cg.uas.dao;

import java.util.ArrayList;

import com.cg.uas.bean.ProgramsScheduled;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.ProgramAlreadyExistsException;

public interface ProgramsScheduledDao
{

	ProgramsScheduled readProgramsScheduled(String scheduledProgramId) throws InvalidProgramException;
	
	ArrayList<ProgramsScheduled> reportAll();

	boolean createProgramsScheduled(ProgramsScheduled scheduled) throws ProgramAlreadyExistsException;

	boolean updatePogramsScheduled(String scheduledProgramId, ProgramsScheduled scheduled) throws InvalidProgramException;

	boolean deleteProgramsScheduled(String scheduledProgramId) throws InvalidProgramException;
}
