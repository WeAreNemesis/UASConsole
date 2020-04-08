package com.cg.uas.dao;

import java.util.HashMap;

import com.cg.uas.bean.User;

public class UserDaoImpl implements UserDao {//shailesh
	private static HashMap<String, User> users = new HashMap<String, User>();

	@Override
	public User readUser(String loginId) {
		User u = users.get(loginId);
		return u;
	}

	@Override
	public boolean createUser(User u) {
		User result = users.putIfAbsent(u.getLoginId(), u);
		if (result != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUser(String loginId, User u) {
		if (users.containsKey(loginId)) {
			User result = users.put(loginId, u);

			if (result != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteUser(String loginId) {
		User u = users.remove(loginId);
		if (u != null) {
			return true;
		}
		return false;
	}

	// mockData and print are for redundant
	// mockData adds data for just testing purposes
	public static void mockData() {
		users.put("user1",new User("user1", "Pass1", "mac"));
		users.put("user2",new User("user2", "Pass2", "mac"));
		users.put("user3",new User("user3", "Pass3", "mac"));
		users.put("user4",new User("user4", "Pass4", "admin"));
		users.put("user5",new User("user5", "Pass5", "admin"));

	}

	// print will display all the Users from database
	public void print() {
		System.out.println(users);
	}
}
