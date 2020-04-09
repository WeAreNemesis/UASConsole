package com.cg.uas.dao;

import com.cg.uas.bean.User;
import com.cg.uas.exception.InvalidUserException;
import com.cg.uas.exception.UserAlreadyExistsException;

//shailesh : Interface for CRUD ops for users db

public interface UserDao {
	User readUser(String loginId) throws InvalidUserException;

	boolean createUser(User u) throws UserAlreadyExistsException;

	boolean updateUser(String loginId, User u) throws InvalidUserException;

	boolean deleteUser(String loginId) throws InvalidUserException;
}
