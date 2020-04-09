package com.cg.uas.dao;

import java.util.HashMap;

import com.cg.uas.bean.User;
import com.cg.uas.exception.InvalidUserException;
import com.cg.uas.exception.UserAlreadyExistsException;

public class UserDaoImpl implements UserDao {// shailesh
	private static HashMap<String, User> users = new HashMap<String, User>();

	@Override
	public User readUser(String loginId) throws InvalidUserException {
		User u = users.get(loginId);
		if (u != null) {
			return u;
		} else {
			throw new InvalidUserException();
		}
	}

	@Override
	public boolean createUser(User u) throws UserAlreadyExistsException {
		User result = users.putIfAbsent(u.getLoginId(), u);
		if (result == null) {
			return true;
		} else {
			throw new UserAlreadyExistsException();
		}

	}

	@Override
	public boolean updateUser(String loginId, User u) throws InvalidUserException {
		if (users.containsKey(loginId)) {
			User result = users.put(loginId, u);

			if (result != null) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new InvalidUserException();
		}
	}

	@Override
	public boolean deleteUser(String loginId) throws InvalidUserException {
		User u = users.remove(loginId);
		if (u != null) {
			return true;
		} else {
			throw new InvalidUserException();
		}
	}

	// mockData and print are for redundant
	// mockData adds data for just testing purposes
	public static void mockData() {
		users.put("user1", new User("user1", "Pass1", "mac"));
		users.put("user2", new User("user2", "Pass2", "mac"));
		users.put("user3", new User("user3", "Pass3", "mac"));
		users.put("user4", new User("user4", "Pass4", "admin"));
		users.put("user5", new User("user5", "Pass5", "admin"));

	}

	// print will display all the Users from database
	public void print() {
		System.out.println(users);
	}
}
