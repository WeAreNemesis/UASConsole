package com.cg.uas.testDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.uas.bean.Participant;
import com.cg.uas.dao.ParticipantDaoImpl;
import com.cg.uas.exception.NoSuchParticipant;
import com.cg.uas.exception.ParticipantAlreadyExistsException;

public class TestParticipantDao {

	private static ParticipantDaoImpl pdi = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pdi = new ParticipantDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		pdi = null;
	}

	@Before
	public void setUp() throws Exception {
		pdi.mockData();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadParticipantForValidParticipant() throws NoSuchParticipant {
		assertEquals("A1003", pdi.readParticipant("1001").getScheduledProgramId());
	}

	@Test(expected = NoSuchParticipant.class)
	public void testReadParticipantForInValidParticipant() throws NoSuchParticipant {
		pdi.readParticipant("1003");
	}

	@Test
	public void testCreateParticipantForSuccessfulParticipantCreation() throws ParticipantAlreadyExistsException {
		assertTrue(pdi.createParticipant(new Participant("1003", "123@gmail.com", "5", "A1002")));
	}

	@Test(expected = ParticipantAlreadyExistsException.class)
	public void testCreateParticipantForDuplicateParticipantCreationFailure() throws ParticipantAlreadyExistsException {
		assertTrue(pdi.createParticipant(new Participant("1001", "123@gmail.com", "5", "A1002")));
	}

	@Test
	public void testUpdateParticipantForAvailableParticipant() throws NoSuchParticipant {
		assertTrue(pdi.updateParticipant("1001", new Participant("1001", "123@gmail.com", "5", "A1002")));
	}
	
	@Test(expected = NoSuchParticipant.class)
	public void testUpdateParticipantForInvalidParticipant() throws NoSuchParticipant {
		assertTrue(pdi.updateParticipant("1004", new Participant("1004", "123@gmail.com", "5", "A1002")));
	}
	
	@Test
	public void testDeleteParticipantForValidParticipant() throws NoSuchParticipant{
		assertTrue(pdi.deleteParticipant("1001"));
	}
	
	@Test(expected = NoSuchParticipant.class)
	public void testDeleteParticipantForInvalidParticipant() throws NoSuchParticipant{
		assertTrue(pdi.deleteParticipant("1006"));
	}
}
