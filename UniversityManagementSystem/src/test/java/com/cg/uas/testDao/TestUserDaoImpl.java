package com.cg.uas.testDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.uas.bean.User;
import com.cg.uas.dao.UserDao;
import com.cg.uas.dao.UserDaoImpl;
import com.cg.uas.exception.InvalidUserException;
import com.cg.uas.exception.UserAlreadyExistsException;

public class TestUserDaoImpl {
	private static UserDao udi = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		udi = new UserDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		udi = null;
	}

	@Before
	public void setUp() throws Exception {
		UserDaoImpl.mockData();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadUserForValidUser() throws InvalidUserException {
		assertEquals("Pass1", udi.readUser("user1").getPassword());
	}

	@Test(expected = InvalidUserException.class)
	public void testReadUserForInValidUser() throws InvalidUserException {
		udi.readUser("user6");
	}

	@Test
	public void testCreateUserForSuccessfulUserCreation() throws UserAlreadyExistsException {
		assertTrue(udi.createUser(new User("user6", "Pass6", "mac")));
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void testCreateUserForUserDuplicateCreationFailure() throws UserAlreadyExistsException {
		assertTrue(udi.createUser(new User("user6", "Pass6", "mac")));
	}

	@Test
	public void testUpdateUserForAvailableUser() throws InvalidUserException {
		assertTrue(udi.updateUser("user1", new User("user1", "Pass1new", "ma")));
	}

	@Test(expected = InvalidUserException.class)
	public void testUpdateUserForUnavailableUser() throws InvalidUserException {
		assertTrue(udi.updateUser("user8", new User("user8", "Pass8", "ma")));
	}

	@Test
	public void testDeleteForValidUser() throws InvalidUserException {
		assertTrue(udi.deleteUser("user1"));
	}

	@Test(expected = InvalidUserException.class)
	public void testDeleteFOrInvalidUser() throws InvalidUserException {
		assertTrue(udi.deleteUser("user7"));
	}
}
