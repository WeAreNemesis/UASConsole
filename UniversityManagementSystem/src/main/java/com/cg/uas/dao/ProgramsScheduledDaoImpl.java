package com.cg.uas.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.uas.bean.ProgramsOffered;
import com.cg.uas.bean.ProgramsScheduled;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.service.AdministrationServiceImpl;

public class ProgramsScheduledDaoImpl implements ProgramsScheduledDao {
	private static HashMap<String, ProgramsScheduled> programsScheduled = new HashMap<String, ProgramsScheduled>();
	private static final Logger logger = Logger.getLogger(ProgramsScheduledDaoImpl.class);

	static {
		PropertyConfigurator.configure("src/main/resources/log4j/log4j.properties");
	}

	@Override
	public ProgramsScheduled readProgramsScheduled(String scheduledProgramId) {
		ProgramsScheduled scheduled = programsScheduled.get(scheduledProgramId);
		return scheduled;
	}

	@Override
	public ArrayList<ProgramsScheduled> reportAll() {

		return new ArrayList<ProgramsScheduled>(programsScheduled.values());
	}

	@Override
	public boolean createProgramsScheduled(ProgramsScheduled scheduled) {
		ProgramsScheduled p = programsScheduled.putIfAbsent(scheduled.getScheduledProgramId(), scheduled);
		if (p != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updatePogramsScheduled(String scheduledProgramId, ProgramsScheduled scheduled) {
		ProgramsScheduled p = programsScheduled.put(scheduledProgramId, scheduled);
		if (p != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteProgramsScheduled(String scheduledProgramId) throws InvalidProgramException
	{
		if (programsScheduled.containsKey(scheduledProgramId)) {
			ProgramsScheduled po = programsScheduled.remove(scheduledProgramId);
			if (programsScheduled.containsKey(scheduledProgramId)) {
				logger.info("Program with name: " + scheduledProgramId + "was not deleted.");
				return false;
			} else {
				logger.info("Program with name: " + scheduledProgramId + " was deleted");
				return true;
			}
		} else {
			logger.info("Program with name: " + scheduledProgramId + " was not found.");
			throw new InvalidProgramException();
		}
	}

	public static void mockData() {
		programsScheduled.put("A1001",
				new ProgramsScheduled("A1001", "BE", "Mumbai", LocalDate.of(2020, 2, 1), LocalDate.of(2020, 3, 1), 6));
		programsScheduled.put("A1002", new ProgramsScheduled("A1002", "BCA", "Kolkata", LocalDate.of(2020, 2, 11),
				LocalDate.of(2020, 3, 1), 6));
		programsScheduled.put("A1003", new ProgramsScheduled("A1003", "MCA", "Kolkata", LocalDate.of(2020, 3, 1),
				LocalDate.of(2020, 3, 1), 6));
		programsScheduled.put("A1004",
				new ProgramsScheduled("A1004", "BA", "Pune", LocalDate.of(2020, 4, 1), LocalDate.of(2020, 3, 1), 6));
		programsScheduled.put("A1005", new ProgramsScheduled("A1005", "MBBS", "Bangalore", LocalDate.of(2020, 5, 1),
				LocalDate.of(2020, 3, 1), 6));
		programsScheduled.put("A1006", new ProgramsScheduled("A1006", "LAW", "Bangalore", LocalDate.of(2020, 5, 1),
				LocalDate.of(2020, 3, 1), 6));
	}

	public void print() {
		for (ProgramsScheduled ps : programsScheduled.values()) {
			System.out.println(ps);
		}
	}

//	public Collection<ProgramsScheduled> getAll() {
//		List<ProgramsScheduled> result = programsScheduled.values().stream().collect(Collectors.toList());
//		return new ArrayList<ProgramsScheduled>(result);
//	}
}
