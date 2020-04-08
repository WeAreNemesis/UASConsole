package com.cg.uas.exception;

public class NoProgramsAvailableException extends Exception {
	
	private static final long serialVersionUID = 1927424869812588614L;

	@Override
	public String getMessage() {
		return ExceptionDetail.getExceptionDetail("NoProgramsAvailableException");
	}
}
