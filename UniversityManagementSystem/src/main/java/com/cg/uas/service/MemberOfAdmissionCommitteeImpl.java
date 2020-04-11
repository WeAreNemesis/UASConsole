package com.cg.uas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cg.uas.bean.Application;
import com.cg.uas.bean.Participant;
import com.cg.uas.bean.User;
import com.cg.uas.dao.ApplicationDaoImpl;
import com.cg.uas.dao.ParticipantDaoImpl;
import com.cg.uas.dao.UserDaoImpl;
import com.cg.uas.exception.AuthenticationfailedException;
import com.cg.uas.exception.InvalidUserException;
import com.cg.uas.exception.NoSuchApplication;
import com.cg.uas.exception.NoSuchParticipant;
import com.cg.uas.exception.ParticipantAlreadyExistsException;

public class MemberOfAdmissionCommitteeImpl implements MemberOfAdmissionCommittee {
	private static MemberOfAdmissionCommitteeImpl credentials = null;
	private static UserDaoImpl udi = new UserDaoImpl();
	private ApplicationDaoImpl adi = new ApplicationDaoImpl();
	private ParticipantDaoImpl pdi = new ParticipantDaoImpl();
	private static int rollNo = 1003;

	private MemberOfAdmissionCommitteeImpl() {

	}

	public static void setUdi(UserDaoImpl udi) {
		MemberOfAdmissionCommitteeImpl.udi = udi;
	}

	public void setAdi(ApplicationDaoImpl adi) {
		this.adi = adi;
	}

	public void setPdi(ParticipantDaoImpl pdi) {
		this.pdi = pdi;
	}

	public static MemberOfAdmissionCommitteeImpl getMemberService(String loginId, String password)
			throws AuthenticationfailedException {
		ValidationService val = (user, pass) -> {
			try {
				User u = udi.readUser(user);
				if (u.getPassword().equals(pass) && u.getRole().equalsIgnoreCase("mac")) {
					return true;
				} else {
					throw new AuthenticationfailedException();
				}
			} catch (InvalidUserException e) {
				throw new AuthenticationfailedException();
			}
		};

		boolean auth;
		try {
			auth = val.authenticate(loginId, password);
			if (auth) {
				credentials = new MemberOfAdmissionCommitteeImpl();
			}
			return credentials;
		} catch (AuthenticationfailedException e) {
			throw e;
		}

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
			} else {
				return false;
			}
			break;
		case 2:
			a.setStatus("Rejected");
			break;
		case 3:
			if (a.getStatus().equalsIgnoreCase("Pending")) {
				a.setStatus("Accepted");
			} else {
				return false;
			}
			break;
		default:
			return false;
		}

		try {
			boolean result = adi.updateApplication(applicationId, a);
			if (result && a.getStatus().equalsIgnoreCase("confirmed")) {
				String rollNum = Integer.toString(rollNo);
				Participant p = new Participant(rollNum, a.getEmailId(), a.getApplicationId(),
						a.getScheduledProgramId());
				try {
					result = pdi.createParticipant(p);
					if (result) {
						rollNo++;
						return true;
					} else {
						return false;
					}
				} catch (ParticipantAlreadyExistsException e) {
					try {
						return pdi.updateParticipant(p.getRollNo(), p);
					} catch (NoSuchParticipant e1) {
						return false;
					}

				}
			} else if (result) {
				return true;
			} else {
				return false;
			}
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

	@Override
	public ArrayList<Participant> getParticipants(String scheduledProgramId) {
		List<Participant> result = pdi.getAll().stream().filter((p) -> {
			return p.getScheduledProgramId().equalsIgnoreCase(scheduledProgramId);
		}).collect(Collectors.toList());
		return new ArrayList<Participant>(result);
	}

}
