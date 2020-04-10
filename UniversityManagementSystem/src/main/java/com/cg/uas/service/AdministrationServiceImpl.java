package com.cg.uas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.uas.bean.Application;
import com.cg.uas.bean.ProgramsOffered;
import com.cg.uas.bean.ProgramsScheduled;
import com.cg.uas.bean.User;
import com.cg.uas.dao.ApplicationDaoImpl;
import com.cg.uas.dao.ProgramsOfferedDaoImpl;
import com.cg.uas.dao.ProgramsScheduledDaoImpl;
import com.cg.uas.dao.UserDaoImpl;
import com.cg.uas.exception.AuthenticationfailedException;
import com.cg.uas.exception.InvalidDateException;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.InvalidUserException;
import com.cg.uas.exception.NoSuchApplication;
import com.cg.uas.exception.ProgramAlreadyExistsException;

public class AdministrationServiceImpl implements AdministrationService {

	private static AdministrationServiceImpl asi = null;
	private static UserDaoImpl udi = new UserDaoImpl();
	private static ProgramsOfferedDaoImpl podi = new ProgramsOfferedDaoImpl();
	private static ProgramsScheduledDaoImpl psdi = new ProgramsScheduledDaoImpl();
	private static ApplicationDaoImpl adi = new ApplicationDaoImpl();
	private static final Logger logger = Logger.getLogger(AdministrationServiceImpl.class);

	static {
		PropertyConfigurator.configure("src/main/resources/log4j/log4j.properties");
	}

	private AdministrationServiceImpl() {
	}

	private static ValidationService val = (user, pass) -> {
		try {
			User u = udi.readUser(user);
			if (u.getPassword().equals(pass) && u.getRole().equalsIgnoreCase("admin")) {
				logger.info("authenticated");
				return true;
			} else {
				logger.info("admin auth error.");
				throw new AuthenticationfailedException();
			}
		} catch (InvalidUserException e) {
			logger.info("admin auth error.");
			throw new AuthenticationfailedException();
		}

	};

	public static AdministrationServiceImpl getAdminService(String loginId, String password)
			throws AuthenticationfailedException {
		PropertyConfigurator.configure("src/main/resources/log4j/log4j.properties");
		try {
			boolean auth = val.authenticate(loginId, password);
			if (auth) {
				asi = new AdministrationServiceImpl();
			}
			return asi;
		} catch (AuthenticationfailedException e) {
			throw e;
		}
	}

	@Override
	public int addProgram(ProgramsOffered po) throws ProgramAlreadyExistsException {
		boolean result = false;
		try {
			result = podi.createProgramsOffered(po);
			if (result) {
				return 1;
			} else {
				return 0;
			}
		} catch (ProgramAlreadyExistsException e) {
			throw e;
		}
	}

	@Override
	public int updateOfferedProgram(ProgramsOffered po) throws InvalidProgramException {
		try {
			boolean result = podi.updateProgramsOffered(po.getProgramName(), po);
			if (result) {
				return 1;
			} else {
				return 0;
			}
		} catch (InvalidProgramException e) {
			throw e;
		}
	}

	@Override
	public int removeProgram(String program) throws InvalidProgramException {
		try {
			boolean result = podi.deleteProgramsOffered(program);
			if (result) {
				logger.info("offered program deleted.");
				return 1;
			} else {
				logger.info("offered program deletion failed.");
				return 0;
			}
		} catch (InvalidProgramException e) {
			try {
				Application a = adi.readApplicationByProgramId(program);
				if (a != null) {
					logger.info("program has 1 or more applications, delete failed.");
					return 0;
				}
			} catch (NoSuchApplication e1) {
				try {
					boolean result = psdi.deleteProgramsScheduled(program);
					if (result) {
						logger.info("scheduled program deleted successfully.");
						return 1;
					} else {
						logger.info("scheduled program failed to delete.");
						return 0;
					}
				} catch (InvalidProgramException e2) {
					throw e2;
				}
			}
		}

		return 0;
	}

	@Override
	public ArrayList<Application> applicationsByStatus(String status) {
		List<Application> result = adi.getAll().stream().filter((a) -> {
			return a.getStatus().equalsIgnoreCase(status);
		}).collect(Collectors.toList());
		logger.info(result.size() + " applications have " + status + " status");
		return new ArrayList<Application>(result);
	}

	@Override
	public void displayAllPrograms() {
		System.out.println("-------------------------------");
		podi.print();
		System.out.println("-------------------------------");
		psdi.print();
		System.out.println("-------------------------------");
	}

	@Override
	public ArrayList<ProgramsScheduled> reportOfScheduledProgramsByDate(LocalDate s, LocalDate e) {
		List<ProgramsScheduled> result = psdi.reportAll().stream().filter((ProgramsScheduled ps) -> {
			int t1 = ps.getStartDate().compareTo(s); // ps-s
			int t2 = ps.getStartDate().compareTo(e); // ps-e
			if (t1 >= 0 && t2 <= 0) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		logger.info(result.size() + " scheduled programs start between :" + s + " and " + e);
		return new ArrayList<ProgramsScheduled>(result);
	}

	@Override
	public boolean addScheduledProgram(ProgramsScheduled ps)
			throws ProgramAlreadyExistsException, InvalidDateException, InvalidProgramException {
		LocalDate d1 = ps.getStartDate();
		LocalDate d2 = ps.getEndDate();
		int t1 = d2.compareTo(d1);
		int t2 = d1.compareTo(LocalDate.now());
		int t3 = d2.compareTo(LocalDate.now());
		if (t1 < 0 || t2 < 0 || t3 < 0) {
			logger.info("Wrong pair of date was entered.");
			throw new InvalidDateException();
		}
		try {
			ProgramsScheduled temp = psdi.readProgramsScheduled(ps.getScheduledProgramId());
			logger.info("the program :" + ps.getScheduledProgramId() + "is already scheduled.");
			throw new ProgramAlreadyExistsException();
		} catch (InvalidProgramException e) {
			logger.info("the program :" + ps.getScheduledProgramId() + "is not yet scheduled.");
			try {
				ProgramsOffered po = podi.readProgramsOffered(ps.getProgramName());
				if (po != null) {
					logger.info("the program : " + ps.getProgramName() + " is available in offered programs.");
					boolean result = psdi.createProgramsScheduled(ps);
					if (result) {
						logger.info("The program : " + ps.getScheduledProgramId() + " was scheduled.");
						return true;
					} else {
						logger.info("The program : " + ps.getScheduledProgramId() + " failed to schedule.");
						return false;
					}
				} else {
					logger.info("the program : " + ps.getProgramName() + " is unavailable in offered programs.");
					throw new InvalidProgramException();
				}
			} catch (InvalidProgramException e1) {
				throw e1;
			}

		}
	}
}
