package com.cg.uas.testAdmin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
import com.cg.uas.exception.NoSuchApplication;
import com.cg.uas.exception.ProgramAlreadyExistsException;
import com.cg.uas.service.AdministrationServiceImpl;
import com.cg.uas.service.ValidationService;

@RunWith(MockitoJUnitRunner.class)
public class TestAdminService {

	private AdministrationServiceImpl asi;
	@Mock
	UserDaoImpl udi;

	@Mock
	ValidationService val;

	@Mock
	Application a;

	@Mock
	ArrayList<Application> applications;

	@Mock
	ProgramsOfferedDaoImpl podi;

	@Mock
	ProgramsScheduledDaoImpl psdi;

	@Mock
	ApplicationDaoImpl adi;

	@Mock
	ProgramsOffered po;

	@Mock
	ProgramsScheduled ps;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		when(udi.readUser("user")).thenReturn(new User("user", "pass", "admin"));
		AdministrationServiceImpl.setUdi(udi);
		asi = AdministrationServiceImpl.getAdminService("user", "pass");
		asi.setAdi(adi);
		asi.setPodi(podi);
		asi.setPsdi(psdi);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAdminServiceAuthenticationSuccess() throws AuthenticationfailedException {
		AdministrationServiceImpl service = AdministrationServiceImpl.getAdminService("user", "pass");
		assertNotNull(service);
	}

	@Test(expected = AuthenticationfailedException.class)
	public void testGetAdminServiceAuthenticationFailure() throws AuthenticationfailedException {
		AdministrationServiceImpl service = AdministrationServiceImpl.getAdminService("user", "wrongpass");
		assertNull(service);
	}

	@Test
	public void testAddProgramOfferedForSuccess() throws ProgramAlreadyExistsException {
		when(podi.createProgramsOffered(po)).thenReturn(true);
		int i = asi.addProgram(po);
		assertEquals(1, i);
	}

	@Test(expected = ProgramAlreadyExistsException.class)
	public void testAddProgramOfferedForAlreadyExistingProgram() throws ProgramAlreadyExistsException {
		when(podi.createProgramsOffered(po)).thenThrow(ProgramAlreadyExistsException.class);
		asi.addProgram(po);
	}

	@Test
	public void testAddProgramOfferedForFailure() throws ProgramAlreadyExistsException {
		when(podi.createProgramsOffered(po)).thenReturn(false);
		int i = asi.addProgram(po);
		assertEquals(0, i);
	}

	@Test
	public void testUpdateOfferedProgramForSuccess() throws InvalidProgramException {
		when(podi.updateProgramsOffered("name", po)).thenReturn(true);
		when(po.getProgramName()).thenReturn("name");
		assertEquals(1, asi.updateOfferedProgram(po));
	}

	@Test(expected = InvalidProgramException.class)
	public void testUpdateOfferedProgramForUnavailableProgram() throws InvalidProgramException {
		when(po.getProgramName()).thenReturn("name");
		when(podi.updateProgramsOffered("name", po)).thenThrow(InvalidProgramException.class);
		asi.updateOfferedProgram(po);
	}

	@Test
	public void testUpdateOfferedProgramForFailure() throws InvalidProgramException {
		when(podi.updateProgramsOffered("name", po)).thenReturn(false);
		when(po.getProgramName()).thenReturn("name");
		assertEquals(0, asi.updateOfferedProgram(po));
	}

	@Test
	public void testRemoveProgramForValidOfferedProgramSuccess() throws InvalidProgramException {
		when(podi.deleteProgramsOffered("name")).thenReturn(true);
		assertEquals(1, asi.removeProgram("name"));
	}

	@Test
	public void testRemoveProgramForValidOfferedProgramFailure() throws InvalidProgramException {
		when(podi.deleteProgramsOffered("name")).thenReturn(false);
		assertEquals(0, asi.removeProgram("name"));
	}

	@Test
	public void testRemoveProgramForScheduledProgramForAvailableApplication()
			throws InvalidProgramException, NoSuchApplication {
		when(podi.deleteProgramsOffered("id")).thenThrow(InvalidProgramException.class);
		when(adi.readApplicationByProgramId("id")).thenReturn(a);
		assertEquals(0, asi.removeProgram("id"));
	}

	@Test(expected = InvalidProgramException.class)
	public void testRemoveProgramForInvalidScheduledProgramForNoAvailableApplication()
			throws InvalidProgramException, NoSuchApplication {
		when(podi.deleteProgramsOffered("id")).thenThrow(InvalidProgramException.class);
		when(adi.readApplicationByProgramId("id")).thenThrow(NoSuchApplication.class);
		when(psdi.deleteProgramsScheduled("id")).thenThrow(InvalidProgramException.class);
		asi.removeProgram("id");
	}

	@Test
	public void testRemoveProgramForvalidScheduledProgramForNoAvailableApplicationSuccess()
			throws InvalidProgramException, NoSuchApplication {
		when(podi.deleteProgramsOffered("id")).thenThrow(InvalidProgramException.class);
		when(adi.readApplicationByProgramId("id")).thenThrow(NoSuchApplication.class);
		when(psdi.deleteProgramsScheduled("id")).thenReturn(true);
		assertEquals(1, asi.removeProgram("id"));
	}

	@Test
	public void testRemoveProgramForvalidScheduledProgramForNoAvailableApplicationFailure()
			throws InvalidProgramException, NoSuchApplication {
		when(podi.deleteProgramsOffered("id")).thenThrow(InvalidProgramException.class);
		when(adi.readApplicationByProgramId("id")).thenThrow(NoSuchApplication.class);
		when(psdi.deleteProgramsScheduled("id")).thenReturn(false);
		assertEquals(0, asi.removeProgram("id"));
	}

	@Test
	public void testApplicationsByStatusForSuccess() {
		when(adi.getAll()).thenReturn(new ArrayList<Application>());
		assertNotNull(asi.applicationsByStatus("confirmed"));
	}

	@Test(expected = NullPointerException.class)
	public void testApplicationsByStatusForEmptySet() {
		when(adi.getAll()).thenReturn(null);
		asi.applicationsByStatus("confirmed");
	}

	@Test
	public void testReportOfScheduledProgramsByDateForSuccess() {
		when(psdi.reportAll()).thenReturn(new ArrayList<ProgramsScheduled>());
		assertNotNull(asi.reportOfScheduledProgramsByDate(LocalDate.now(), LocalDate.now()));
	}

	@Test(expected = NullPointerException.class)
	public void testReportOfScheduledProgramsByDateForEmptySet() {
		when(psdi.reportAll()).thenReturn(null);
		assertNotNull(asi.reportOfScheduledProgramsByDate(LocalDate.now(), LocalDate.now()));
	}

	@Test(expected = InvalidDateException.class)
	public void testAddScheduledProgramForInvalidDate()
			throws InvalidProgramException, ProgramAlreadyExistsException, InvalidDateException {
		when(ps.getStartDate()).thenReturn(LocalDate.now());
		when(ps.getEndDate()).thenReturn(LocalDate.now().minusDays(1));
		asi.addScheduledProgram(ps);
	}

	@Test(expected = ProgramAlreadyExistsException.class)
	public void testAddScheduledProgramForAlreadyExistingSchedule()
			throws InvalidProgramException, ProgramAlreadyExistsException, InvalidDateException {
		when(ps.getStartDate()).thenReturn(LocalDate.now());
		when(ps.getEndDate()).thenReturn(LocalDate.now());
		when(ps.getScheduledProgramId()).thenReturn("id");
		when(psdi.readProgramsScheduled("id")).thenReturn(ps);
		asi.addScheduledProgram(ps);
	}

	@Test(expected = InvalidProgramException.class)
	public void testAddScheduledProgramForInvalidOfferedProgram()
			throws InvalidProgramException, ProgramAlreadyExistsException, InvalidDateException {
		when(ps.getStartDate()).thenReturn(LocalDate.now());
		when(ps.getEndDate()).thenReturn(LocalDate.now());
		when(ps.getScheduledProgramId()).thenReturn("id");
		when(ps.getProgramName()).thenReturn("name");
		when(psdi.readProgramsScheduled("id")).thenThrow(InvalidProgramException.class);
		when(podi.readProgramsOffered("name")).thenThrow(InvalidProgramException.class);
		asi.addScheduledProgram(ps);
	}

	@Test
	public void testAddScheduledProgramForValidOfferedProgramSuccess()
			throws ProgramAlreadyExistsException, InvalidDateException, InvalidProgramException {
		when(ps.getStartDate()).thenReturn(LocalDate.now());
		when(ps.getEndDate()).thenReturn(LocalDate.now());
		when(ps.getScheduledProgramId()).thenReturn("id");
		when(ps.getProgramName()).thenReturn("name");
		when(psdi.readProgramsScheduled("id")).thenThrow(InvalidProgramException.class);
		when(podi.readProgramsOffered("name")).thenReturn(po);
		when(psdi.createProgramsScheduled(ps)).thenReturn(true);
		assertEquals(true, asi.addScheduledProgram(ps));
	}

	@Test
	public void testAddScheduledProgramForValidOfferedProgramFailure()
			throws ProgramAlreadyExistsException, InvalidDateException, InvalidProgramException {
		when(ps.getStartDate()).thenReturn(LocalDate.now());
		when(ps.getEndDate()).thenReturn(LocalDate.now());
		when(ps.getScheduledProgramId()).thenReturn("id");
		when(ps.getProgramName()).thenReturn("name");
		when(psdi.readProgramsScheduled("id")).thenThrow(InvalidProgramException.class);
		when(podi.readProgramsOffered("name")).thenReturn(po);
		when(psdi.createProgramsScheduled(ps)).thenReturn(false);
		assertEquals(false, asi.addScheduledProgram(ps));
	}

}
