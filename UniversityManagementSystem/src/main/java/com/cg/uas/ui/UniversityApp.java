package com.cg.uas.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.cg.uas.bean.Application;
import com.cg.uas.bean.ProgramsOffered;
import com.cg.uas.bean.ProgramsScheduled;
import com.cg.uas.dao.ApplicationDaoImpl;
import com.cg.uas.dao.ProgramsOfferedDaoImpl;
import com.cg.uas.dao.ProgramsScheduledDaoImpl;
import com.cg.uas.dao.UserDaoImpl;
import com.cg.uas.exception.ApplicationAlreadyExistsException;
import com.cg.uas.exception.AuthenticationfailedException;
import com.cg.uas.exception.InvalidDateException;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.NoProgramsAvailableException;
import com.cg.uas.exception.NoSuchApplication;
import com.cg.uas.exception.ProgramAlreadyExistsException;
import com.cg.uas.service.AdministrationService;
import com.cg.uas.service.AdministrationServiceImpl;
import com.cg.uas.service.ApplicationServiceImpl;
import com.cg.uas.service.MemberOfAdmissionCommittee;
import com.cg.uas.service.MemberOfAdmissionCommitteeImpl;

public class UniversityApp {

	private static ApplicationServiceImpl asi = new ApplicationServiceImpl();

	public static void main(String[] args) {
		UserDaoImpl.mockData();
		ProgramsOfferedDaoImpl.mockdata();
		ProgramsScheduledDaoImpl.mockData();
		ApplicationDaoImpl.mockData();

		Scanner s = new Scanner(System.in);
		boolean run = true;

		while (run) {

			System.out.println("Welcome to Hogwarts University  ");
			System.out.println("+-----------------------------------+");
			System.out.println("|	1.View Scheduled Programs:  |");
			System.out.println("|	2.Apply Online:		|");
			System.out.println("|	3.View Application Status:		|");
			System.out.println("|	4.Login as admin: 			|");
			System.out.println("|	5.Login as MAC: 			|");
			System.out.println("|	6.exit                       		|");
			System.out.println("+-----------------------------------+");
			int choice;
			try {
				choice = s.nextInt();
			} catch (InputMismatchException e) {
				choice = 0;
			}

			switch (choice) {
			case 1:
				viewScheduledPrograms();
				break;
			case 2:
				applyOnline(s);
				break;
			case 3:
				System.out.println("Enter the applicationId");
				String applicationId = s.next();
				try {
					String status = asi.applicationStatus(applicationId);
					System.out.println("Your application is : " + status);
				} catch (NoSuchApplication e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				authenticationAdmin: {
					System.out.println("Enter the login id:");
					String loginId = s.next();
					System.out.println("Enter the password:");
					String password = s.next();
					AdministrationService as;
					try {
						as = AdministrationServiceImpl.getAdminService(loginId, password);
						AdminServicesFunction(as, s);
					} catch (AuthenticationfailedException e) {
						System.out.println(e.getMessage());
						break authenticationAdmin;
					}
				}
				break;
			case 5:
				authenticationMac: {
					System.out.println("Enter the login id:");
					String loginId = s.next();
					System.out.println("Enter the password:");
					String password = s.next();
					MemberOfAdmissionCommittee moac;
					try {
						moac = MemberOfAdmissionCommitteeImpl.getMemberService(loginId, password);
						macServices(moac, s);
					} catch (AuthenticationfailedException e) {
						System.out.println(e.getMessage());
						break authenticationMac;
					}
				}
				break;
			case 6:
				run = false;
				System.out.println("Thank you very much for visiting.");
				break;
			default:
				System.out.println("Invalid choice");
			}

		}
		s.close();
	}

	private static void macServices(MemberOfAdmissionCommittee moac, Scanner s) {
		boolean run = true;
		while (run) {
			System.out.println("Welcome to authorized committee section:");
			System.out.println("----------------------------------------");
			System.out.println("1.View applications of a specific program:");
			System.out.println("2.Accept and schedule date of interview:");
			System.out.println("3.Confirm or Reject the application:");
			System.out.println("4.View Participants of a program:");
			System.out.println("5.exit");
			System.out.println("----------------------------------------");
			int choice;

			try {
				choice = s.nextInt();
			} catch (InputMismatchException e) {
				choice = 0;
			}

			switch (choice) {
			case 1:
				System.out.println("Enter the scheduled program id:");
				String scheduledProgramId = s.next();
				ArrayList<Application> a = moac.applicationsOfProgram(scheduledProgramId);
				for (Application temp : a) {
					System.out.println(temp);
				}
				break;
			case 2:
				try {
					System.out.println("Enter the application id to accept:");
					String applicationId = s.next();
					System.out.println("Enter the date of interview in yyyy-mm-dd:");
					String date = s.next();

					boolean result = moac.scheduleInterview(applicationId, LocalDate.parse(date));
					if (result) {
						System.out.println("Application : " + applicationId + " update successful.");
					} else {
						System.out.println("Application : " + applicationId + " interview already scheduled.");
					}
				} catch (NoSuchApplication e) {
					System.out.println(e.getMessage());
				} catch (DateTimeParseException e) {
					System.out.println("Invalid date entered.");
				}
				break;
			case 3:
				System.out.println("Enter the application id to confirm or reject:");
				String applicationIdToUpdateStatus = s.next();
				System.out.println("1.Confirm                            2.Reject ");

				try {
					int status = s.nextInt();
					boolean resultOfUpdate = moac.updateApplicationStatus(applicationIdToUpdateStatus, status);
					if (resultOfUpdate) {
						System.out.println("Application : " + applicationIdToUpdateStatus + " update successful.");
					} else {
						System.out.println("Application : " + applicationIdToUpdateStatus + " update unauthorized");
					}
				} catch (NoSuchApplication e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("Invalid choice entered.");
				}
				break;
			case 4:
				System.out.println("Enter the program id:");
				String scheduledProgram = s.next();
				moac.getParticipants(scheduledProgram).forEach((p) -> System.out.println(p));
				break;
			case 5:
				run = false;
				break;
			default:
				System.out.println("Invalid Choice");
				break;
			}
		}
		moac = null;
	}

	private static void viewScheduledPrograms() {
		System.out.println("-----------------------------");
		try {
			ArrayList<ProgramsScheduled> result = asi.getScheduledProgramsList();
			for (ProgramsScheduled ps : result) {
				System.out.println(ps);
			}
		} catch (NoProgramsAvailableException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("-----------------------------");
	}

	private static void applyOnline(Scanner s) {
		Application app = null;
		try {
			s.nextLine();

			System.out.println("Enter your fullName: ");
			String fullName = s.nextLine();
			System.out.println("Enter your date of birth in yyyy-mm--dd: ");
			String dateOfBirth = s.next();
			s.nextLine();
			System.out.println("Enter the highest Qualication: ");
			String highestQualification = s.nextLine();
			System.out.println("Enter Marks Obtained: ");
			int marksObtained = s.nextInt();
			s.nextLine();
			System.out.println("Enter your goals:");
			String goals = s.nextLine();
			System.out.println("Enter the email id: ");
			String emailId = s.nextLine();
			System.out.println("Enter the scheduled program id:");
			String scheduledProgramId = s.nextLine();
			String status = "pending";

			app = new Application("", fullName, LocalDate.parse(dateOfBirth), highestQualification, marksObtained,
					goals, emailId, scheduledProgramId, status, null);

			int id = asi.applyOnline(app);
			System.out.println("Your application is successful : " + id);
		} catch (ApplicationAlreadyExistsException | InvalidProgramException e) {
			System.out.println(e);
			app = null;
		} catch (DateTimeParseException e) {
			System.out.println("Invalid date entered");
		} catch (InputMismatchException e) {
			System.out.println("Invalid data entered");
		}
	}

	private static void AdminServicesFunction(AdministrationService as, Scanner s) {
		if (as == null) {
			System.out.println("admin functions facing issues");
			return;
		}
		boolean run = true;
		while (run) {
			System.out.println("1.Add/update a new Offered Program:");
			System.out.println("2.View Offered & Scheduled Programs:");
			System.out.println("3.Delete an Offered/Scheduled program:");
			System.out.println("4.Generate report by time period:");
			System.out.println("5.Generate report by status:");
			System.out.println("6.Add a scheduled program:");
			System.out.println("7.logout");

			int choice;

			try {
				choice = s.nextInt();
			} catch (InputMismatchException e) {
				choice = 0;
			}

			s.nextLine();
			switch (choice) {
			case 1:
				System.out.println("Enter the program name:");
				String programName = s.nextLine();
				System.out.println("Enter the description:");
				String description = s.nextLine();
				System.out.println("Enter the eligibility criteria:");
				String applicantEligibility = s.nextLine();
				System.out.println("Enter the degree certificate offered:");
				String degreeCertificateOffered = s.nextLine();
				System.out.println("Enter the duration in months:");
				try {
					int duration = s.nextInt();
					ProgramsOffered po = new ProgramsOffered(programName.trim(), description, applicantEligibility,
							duration, degreeCertificateOffered);
					as.addProgram(po);

				} catch (InputMismatchException e) {
					System.out.println("Enter duration only in months only.");
				}

				as.displayAllPrograms();
				break;
			case 2:
				as.displayAllPrograms();
				break;
			case 3:
				System.out.println("Enter the program name or id to delete:");
				String program = s.next();
				try {
					as.removeProgram(program);
				} catch (InvalidProgramException e1) {
					System.out.println(e1.getMessage());
				}
				as.displayAllPrograms();
				break;
			case 4:
				System.out.println("Enter the starting date in yyyy-mm-dd");
				String startDate = s.next();
				System.out.println("Enter the ending date in yyyy-mm-dd");
				String endDate = s.next();
				try {
					ArrayList<ProgramsScheduled> ps = as.reportOfScheduledProgramsByDate(LocalDate.parse(startDate),
							LocalDate.parse(endDate));
					System.out.println("Report of programs from: " + startDate + " to : " + endDate);
					for (ProgramsScheduled p : ps) {
						System.out.println(p);
					}
				} catch (DateTimeParseException e) {
					System.out.println("Invalid Date Entered.");
				}
				break;
			case 5:
				System.out.println("Select One choice:");
				System.out.println("1.Accepted");
				System.out.println("2.Rejected");
				System.out.println("3.Confirmed");
				int status;
				try {
					status = s.nextInt();
				} catch (InputMismatchException e) {
					status = 0;
				}
				switch (status) {
				case 1:
					as.applicationsByStatus("Accepted").forEach((temp) -> System.out.println(temp));
					break;
				case 2:
					as.applicationsByStatus("Rejected").forEach((temp) -> System.out.println(temp));
					break;
				case 3:
					as.applicationsByStatus("Confirmed").forEach((temp) -> System.out.println(temp));
					break;
				default:
					System.out.println("Invalid Choice");
					break;
				}
				break;
			case 6:
				loop: {
					try {
						System.out.println("Enter the scheduled program id:");
						String scheduledProgramId = s.next();
						System.out.println("Enter the program name:");
						String scheduledProgramName = s.next();
						System.out.println("Enter the city:");
						String city = s.next();
						System.out.println("Enter the start date:");
						String startDateSc = s.next();
						System.out.println("Enter the start date:");
						String endDateSc = s.next();
						System.out.println("Enter sessions per week:");
						int sessions = s.nextInt();
						boolean result = as
								.addScheduledProgram(new ProgramsScheduled(scheduledProgramId, scheduledProgramName,
										city, LocalDate.parse(startDateSc), LocalDate.parse(endDateSc), sessions));
						if (result) {
							System.out.println("Program scheduled successfully");
						}
					} catch (InvalidDateException e) {
						System.out.println(e.getMessage());
						break loop;
					} catch (ProgramAlreadyExistsException | InputMismatchException e) {
						System.out.println(e.getMessage());
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				break;
			case 7:
				run = false;
				break;
			default:
				System.out.println("Invalid Choice");
				break;
			}
		}

		as = null;
	}

}
