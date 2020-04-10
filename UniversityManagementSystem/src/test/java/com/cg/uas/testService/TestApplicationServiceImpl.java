package com.cg.uas.testService;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.uas.service.ApplicationService;
import com.cg.uas.service.ApplicationServiceImpl;

public class TestApplicationServiceImpl {
	
	public static ApplicationService asi=null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		asi=new ApplicationServiceImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		asi=null;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetScheduledProgramsList() {
		fail("Not yet implemented");
	}

	@Test
	public void testApplyOnline() {
		fail("Not yet implemented");
	}

	@Test
	public void testApplicationStatus() {
		fail("Not yet implemented");
	}

}
