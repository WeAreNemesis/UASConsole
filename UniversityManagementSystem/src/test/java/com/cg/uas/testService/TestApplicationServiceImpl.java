package com.cg.uas.testService;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.uas.bean.Application;
import com.cg.uas.dao.ApplicationDao;
import com.cg.uas.dao.ApplicationDaoImpl;
import com.cg.uas.exception.ApplicationAlreadyExistsException;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.NoSuchApplication;
import com.cg.uas.service.ApplicationService;
import com.cg.uas.service.ApplicationServiceImpl;

public class TestApplicationServiceImpl {

	public static ApplicationService asi = null;
	public static ApplicationDao adi = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		asi = new ApplicationServiceImpl();
		adi = new ApplicationDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		asi = null;
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
	public void testGetScheduledProgramsList() {
		fail("Not yet implemented");
	}

	@Test
	public void testApplyOnlineForValidApplication() throws ApplicationAlreadyExistsException, InvalidProgramException {
		fail("Not yet implemented");
	}

	@Test
	public void testApplicationStatusForValidApplication() throws NoSuchApplication {
		Application a = adi.readApplication("1");
		assertEquals("Accepted", adi.readApplication("2").getStatus());
	}
	
	@Test(expected = NoSuchApplication.class)
	public void testApplicationStatusForInvalidApplication() throws NoSuchApplication {
		adi.readApplication("8");
	}
}
