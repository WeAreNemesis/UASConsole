package com.cg.uas.dao;

import com.cg.uas.bean.ProgramsOffered;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.ProgramAlreadyExistsException;


public interface ProgramsOfferedDao {

	ProgramsOffered readProgramsOffered(String programName) throws InvalidProgramException;

	boolean createProgramsOffered(ProgramsOffered programsOffered) throws ProgramAlreadyExistsException;

	boolean updateProgramsOffered(String programName, ProgramsOffered programsOffered) throws InvalidProgramException;

	boolean deleteProgramsOffered(String programName) throws InvalidProgramException;
}
