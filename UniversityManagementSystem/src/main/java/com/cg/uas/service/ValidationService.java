package com.cg.uas.service;

@FunctionalInterface
public interface ValidationService {
	boolean authenticate(String loginId,String password);
}
