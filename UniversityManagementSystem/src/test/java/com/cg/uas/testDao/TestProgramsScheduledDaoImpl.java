package com.cg.uas.testDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.uas.bean.ProgramsScheduled;
import com.cg.uas.dao.ProgramsScheduledDao;
import com.cg.uas.dao.ProgramsScheduledDaoImpl;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.ProgramAlreadyExistsException;

public class TestProgramsScheduledDaoImpl
{
	private static ProgramsScheduledDao psdi = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		psdi = new ProgramsScheduledDaoImpl();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		psdi = null;
	}

	@Before
	public void setUp() throws Exception {
		ProgramsScheduledDaoImpl.mockData();
	}
	
	@After
	public void tearDown() throws Exception {	
	}
	
	@Test
	public void testReadProgramsScheduled() throws InvalidProgramException
	{
		assertEquals("BE", psdi.readProgramsScheduled("A1001").getProgramName());
	}
	
	@Test(expected = InvalidProgramException.class)
	public void testReadInvalidProgramsScheduled() throws InvalidProgramException
	{
		psdi.readProgramsScheduled("A1008");
	}
	
	@Test
	public void testCreateProgramsScheduled() throws ProgramAlreadyExistsException
	{
		assertTrue(psdi.createProgramsScheduled(new ProgramsScheduled("A1007", "B.Sc", "Mumbai",LocalDate.of(2020,8,10),LocalDate.of(2020,10,2), 2)));
	}
	
	@Test(expected = ProgramAlreadyExistsException.class)
	public void testCreateAlreadyExistingProgram() throws ProgramAlreadyExistsException
	{
		assertTrue(psdi.createProgramsScheduled(new ProgramsScheduled("A1001", "BE", "Mumbai", LocalDate.of(2020, 2, 1), LocalDate.of(2020, 3, 1), 6)));
	}
	
	@Test
	public void testUpdateProgramsScheduledForExistingPrograms() throws InvalidProgramException
	{
		assertTrue(psdi.updatePogramsScheduled("A1002", new ProgramsScheduled("A1002", "B.Sc", "Mumbai", LocalDate.of(2020, 2, 11),
				LocalDate.of(2020, 3, 1), 6)));
	}
	
	@Test(expected= InvalidProgramException.class)
	public void testUpdateProgramsScheduledForInvaildPrograms() throws InvalidProgramException
	{
		assertTrue(psdi.updatePogramsScheduled("A1008", new ProgramsScheduled("A1008", "B.Com", "Pune", LocalDate.of(2020, 5, 11),
				LocalDate.of(2020, 3, 10), 6)));
	}
	
	@Test
	public void testDeleteProgramsScheduledForExistingPrograms() throws InvalidProgramException
	{
		assertTrue(psdi.deleteProgramsScheduled("A1003"));
	}
	
	@Test(expected=InvalidProgramException.class)
	public void testDeleteProgramsScheduledForInvalidPrograms() throws InvalidProgramException
	{
		assertTrue(psdi.deleteProgramsScheduled("A1009"));
	}
}
