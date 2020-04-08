package com.cg.uas.dao;

import java.util.ArrayList;

import com.cg.uas.bean.ProgramsScheduled;
import com.cg.uas.exception.InvalidProgramException;

public interface ProgramsScheduledDao
{

	ProgramsScheduled readProgramsScheduled(String scheduledProgramId);
	
	ArrayList<ProgramsScheduled> reportAll();

	boolean createProgramsScheduled(ProgramsScheduled scheduled);

	boolean updatePogramsScheduled(String scheduledProgramId, ProgramsScheduled scheduled);

	boolean deleteProgramsScheduled(String scheduledProgramId) throws InvalidProgramException;
}
