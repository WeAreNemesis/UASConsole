package com.cg.uas.exception;

public class InvalidUserException extends Exception {

	@Override
	public String getMessage() {
		return ExceptionDetail.getExceptionDetail("InvalidUserException");
	}
}
