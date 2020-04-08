package com.cg.uas.dao;

import com.cg.uas.bean.User;

//shailesh : Interface for CRUD ops for users db

public interface UserDao {
	User readUser(String loginId);

	boolean createUser(User u);

	boolean updateUser(String loginId, User u);

	boolean deleteUser(String loginId);
}
