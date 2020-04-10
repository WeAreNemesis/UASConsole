package com.cg.uas.testDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.uas.bean.Application;
import com.cg.uas.bean.User;
import com.cg.uas.dao.ApplicationDao;
import com.cg.uas.dao.ApplicationDaoImpl;
import com.cg.uas.exception.ApplicationAlreadyExistsException;
import com.cg.uas.exception.InvalidUserException;
import com.cg.uas.exception.NoSuchApplication;

public class TestApplicationDaoImpl {
	private static ApplicationDao adi = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		adi = new ApplicationDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		adi = null;
	}
	
	@Before
	public void setUp() throws Exception {
		ApplicationDaoImpl.mockData();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testReadApplicationForValidApplication() throws NoSuchApplication {
		assertEquals("Siddharth", adi.readApplication("1").getFullName());
	}
	
	@Test(expected = NoSuchApplication.class)
	public void testReadUserForInValidUser() throws NoSuchApplication {
		adi.readApplication("6");
	}
	
	@Test
	public void testCreateApplicationForSuccessfulApplicationCreation() throws ApplicationAlreadyExistsException {
		assertTrue(adi.createApplication(new Application("6", "Siddharth", LocalDate.parse("1997-12-27"), "12th", 60, 
								"Aim to be an Pilot", "siddharth@gmail.com", "A1006", "pending", LocalDate.parse("2018-12-27"))));
	}
	
	@Test(expected = ApplicationAlreadyExistsException.class)
	public void testCreateApplicationforApplicationDuplicateCreationFailure() throws ApplicationAlreadyExistsException {
		assertTrue(adi.createApplication(new Application("1", "Siddharth", LocalDate.parse("1997-12-27"), "12th", 60, 
				"Aim to be an Engineer", "siddharth@gmail.com", "A1001", "pending", LocalDate.parse("2018-12-27"))));
	}
	
	@Test
	public void testUpdateApplicationForAvailableApplication() throws NoSuchApplication {
		assertTrue(adi.updateApplication("1", new Application("1", "Siddharth", LocalDate.parse("1997-12-27"), "12th", 60, 
				"Aim to be an Engineer", "siddharth@gmail.com", "A1001", "Accepted", LocalDate.parse("2018-12-27"))));
	}
	
	@Test(expected = NoSuchApplication.class)
	public void testUpdateApplicationForUnavailableApplication() throws NoSuchApplication {
		assertTrue(adi.updateApplication("7", new Application("7", "Siddharth", LocalDate.parse("1997-12-27"), "12th", 60, 
				"Aim to be an Scientist", "siddharth@gmail.com", "A1007", "pending", LocalDate.parse("2018-12-27"))));
	}
	
	@Test
	public void testDeleteForValidApplication() throws NoSuchApplication {
		assertTrue(adi.deleteApplication("1"));
	}

	@Test(expected = NoSuchApplication.class)
	public void testDeleteFOrInvalidApplication() throws NoSuchApplication {
		assertTrue(adi.deleteApplication("7"));
	}
}