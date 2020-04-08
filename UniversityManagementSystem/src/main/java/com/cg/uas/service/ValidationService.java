package com.cg.uas.service;

import com.cg.uas.exception.AuthenticationfailedException;

@FunctionalInterface
public interface ValidationService {
	boolean authenticate(String loginId,String password) throws AuthenticationfailedException;
}
