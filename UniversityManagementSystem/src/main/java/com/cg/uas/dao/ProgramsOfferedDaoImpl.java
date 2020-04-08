package com.cg.uas.dao;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.uas.bean.ProgramsOffered;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.ProgramAlreadyExistsException;

public class ProgramsOfferedDaoImpl implements ProgramsOfferedDao {

	private static HashMap<String, ProgramsOffered> progOffered = new HashMap<String, ProgramsOffered>();
	private static final Logger logger = Logger.getLogger(ProgramsOffered.class);

	static {
		PropertyConfigurator.configure("src/main/resources/log4j/log4j.properties");
	}

	@Override
	public ProgramsOffered readProgramsOffered(String programName) throws InvalidProgramException {
		ProgramsOffered programsOffered = progOffered.get(programName);
		if (programsOffered != null) {
			logger.info("Program with name: " + programName + " was found.");
			return programsOffered;
		} else {
			logger.info("Program with name: " + programName + " was not found.");
			throw new InvalidProgramException();
		}

	}

	@Override
	public boolean createProgramsOffered(ProgramsOffered programOffered) throws ProgramAlreadyExistsException {
		ProgramsOffered p = progOffered.putIfAbsent(programOffered.getProgramName(), programOffered);
		if (p == null) {
			logger.info("Program with name: " + programOffered.getProgramName() + " was created.");
			return true;
		} else {
			logger.info("Program with name: " + programOffered.getProgramName() + " already exists.");
			throw new ProgramAlreadyExistsException();
		}

	}

	@Override
	public boolean updateProgramsOffered(String programName, ProgramsOffered programOffered)
			throws InvalidProgramException {
		if (progOffered.containsKey(programName)) {
			ProgramsOffered po = progOffered.put(programName, programOffered);
			if (po != null) {
				logger.info("Program with name: " + programName + " was updated");
				return true;
			} else {
				logger.info("Program with name: " + programName + " update failed");
				return false;
			}
		} else {
			logger.info("Program with name: " + programName + " was not found.");
			throw new InvalidProgramException();
		}
	}

	@Override
	public boolean deleteProgramsOffered(String programName) throws InvalidProgramException {
		if (progOffered.containsKey(programName)) {
			ProgramsOffered po = progOffered.remove(programName);
			if (progOffered.containsKey(programName)) {
				logger.info("Program with name: " + programName + "was not deleted.");
				return false;
			} else {
				logger.info("Program with name: " + programName + " was deleted");
				return true;
			}
		} else {
			logger.info("Program with name: " + programName + " was not found.");
			throw new InvalidProgramException();
		}
	}

	public static void mockdata() {
		progOffered.put("BE",
				new ProgramsOffered("BE", "Bachelors in Engg", "passed 10+2 (PCM) with 60% or above", 4, "NA"));
		progOffered.put("MCA",
				new ProgramsOffered("MCA", "Masters in Comp. App.", "passed BCA with 60% or above", 3, "NA"));
		progOffered.put("BCA",
				new ProgramsOffered("BCA", "Bachelors in Comp. App.", "passed 10+2 with 60% or above", 3, "NA"));
		progOffered.put("BA", new ProgramsOffered("BA", "Bachelors in Arts", "passed 10+2 with 60% or above", 3, "NA"));
		progOffered.put("MBBS",
				new ProgramsOffered("MBBS", "Medical Science", "passed 10+2 (PCB) with 60% or above", 5, "NA"));

	}

	public void print() {
		for (ProgramsOffered po : progOffered.values()) {
			System.out.println(po);
		}
	}

}
