package com.cg.uas.exception;

public class InvalidProgramException extends Exception{
	
	private static final long serialVersionUID = 7820215187937633471L;

	@Override
	public String toString() {
		return "InvalidProgramException";
	}
	
	@Override
	public String getMessage() {
		return ExceptionDetail.getExceptionDetail("InvalidProgramException");
	}
	
}
