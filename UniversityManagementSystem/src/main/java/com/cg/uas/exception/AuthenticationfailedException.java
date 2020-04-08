package com.cg.uas.exception;

public class AuthenticationfailedException extends Exception {
	@Override
	public String getMessage() {
		return ExceptionDetail.getExceptionDetail("AuthenticationfailedException");
	}
}
