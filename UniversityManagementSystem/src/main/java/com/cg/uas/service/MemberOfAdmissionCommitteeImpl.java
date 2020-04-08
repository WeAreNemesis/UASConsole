package com.cg.uas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cg.uas.bean.Application;
import com.cg.uas.bean.User;
import com.cg.uas.dao.ApplicationDaoImpl;
import com.cg.uas.dao.UserDaoImpl;
import com.cg.uas.exception.NoSuchApplication;

public class MemberOfAdmissionCommitteeImpl implements MemberOfAdmissionCommittee {
	private static MemberOfAdmissionCommitteeImpl credentials = null;
	private static UserDaoImpl udi = new UserDaoImpl();
	private static ApplicationDaoImpl adi = new ApplicationDaoImpl();

	private MemberOfAdmissionCommitteeImpl() {

	}

	public static MemberOfAdmissionCommitteeImpl getMemberService(String loginId, String password) {
		ValidationService val = (user, pass) -> {
			User u = udi.readUser(user);
			if (u != null && u.getPassword().equals(pass) && u.getRole().equalsIgnoreCase("mac")) {
				return true;
			}
			return false;
		};

		boolean auth = val.authenticate(loginId, password);
		if (auth) {
			credentials = new MemberOfAdmissionCommitteeImpl();
		}
		return credentials;
	}

	@Override
	public ArrayList<Application> applicationsOfProgram(String scheduledProgramId) {
		List<Application> applications = adi.getAll().stream().filter((a) -> {
			return a.getScheduledProgramId().equalsIgnoreCase(scheduledProgramId);
		}).collect(Collectors.toList());
		return new ArrayList<Application>(applications);
	}

	@Override
	public boolean updateApplicationStatus(String applicationId, int status) throws NoSuchApplication {

		Application a = adi.readApplication(applicationId);
		if (a == null) {
			throw new NoSuchApplication();
		}
		switch (status) {
		case 1:
			if (a.getStatus().equalsIgnoreCase("Accepted")) {
				a.setStatus("Confirmed");
			}
			break;
		case 2:
			a.setStatus("Rejected");
			break;
		case 3:
			if (a.getStatus().equalsIgnoreCase("Pending")) {
				a.setStatus("Accepted");
			}
			break;
		default:
			return false;
		}
		
		try {
			return adi.updateApplication(applicationId, a);
		} catch (NoSuchApplication e) {
			throw e;
		}
	}

	@Override
	public boolean scheduleInterview(String applicationId, LocalDate date) throws NoSuchApplication {
		try {
			Application ap = adi.readApplication(applicationId);
			if (ap.getStatus().equalsIgnoreCase("Pending")) {
				boolean result = this.updateApplicationStatus(applicationId, 3);
				if (result) {
					ap.setDateOfInterview(date);
					return true;
				}
			}
		} catch (NullPointerException | NoSuchApplication e) {
			throw new NoSuchApplication();
		}
		return false;
	}

}
