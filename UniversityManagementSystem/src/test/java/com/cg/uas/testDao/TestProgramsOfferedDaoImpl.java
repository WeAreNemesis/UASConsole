package com.cg.uas.testDao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.uas.bean.ProgramsOffered;
import com.cg.uas.dao.ProgramsOfferedDaoImpl;
import com.cg.uas.exception.InvalidProgramException;
import com.cg.uas.exception.ProgramAlreadyExistsException;

public class TestProgramsOfferedDaoImpl {
	public static ProgramsOfferedDaoImpl podi=null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		podi=new ProgramsOfferedDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		podi=null;
	}

	@Before
	public void setUp() throws Exception {
		ProgramsOfferedDaoImpl.mockdata();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadForValidProgramsOffered() throws InvalidProgramException {
		assertEquals("BE", podi.readProgramsOffered("BE").getProgramName());
	}
	
	@Test(expected = InvalidProgramException.class)
	public void testReadForInvalidProgramsOffered() throws InvalidProgramException {
		podi.readProgramsOffered("BEE");
	}
	
	@Test
	public void testCreateForValidProgramsOffered() throws ProgramAlreadyExistsException {
		assertTrue(podi.createProgramsOffered(new ProgramsOffered("BDS", "Bachelors in Dental", "passed 10+2 (PCB) with 60% or above", 4, "NA")));
		
	}
	
	@Test(expected = ProgramAlreadyExistsException.class)
	public void testCreateForInvalidProgramsOffered() throws ProgramAlreadyExistsException {
		assertTrue(podi.createProgramsOffered(new ProgramsOffered("BE", "Bachelors in Engg", "passed 10+2 (PCM) with 60% or above", 4, "NA")));
		
	}
	
	@Test
	public void testUpdateForValidProgramsOffered() throws InvalidProgramException {
		assertTrue(podi.updateProgramsOffered("BE",new ProgramsOffered("BE", "Bachelors in Engg", "passed 10+2 (PCB) with 70% or above", 4, "NA")));
		
	}
	
	@Test(expected = InvalidProgramException.class)
	public void testUpdateForInvalidProgramsOffered() throws InvalidProgramException {
		assertTrue(podi.updateProgramsOffered("MA",new ProgramsOffered("BDS", "Masters in Arts", "Graduate with 60% or above", 2, "NA")));
		
	}
	
	@Test
	public void testDeleteForValidProgramsOffered() throws InvalidProgramException {
		assertTrue(podi.deleteProgramsOffered("BCA"));
	}

	@Test(expected = InvalidProgramException.class)
	public void testDeleteForInvalidProgramsOffered() throws InvalidProgramException {
		assertTrue(podi.deleteProgramsOffered("BDS"));
	}
	

	
}
