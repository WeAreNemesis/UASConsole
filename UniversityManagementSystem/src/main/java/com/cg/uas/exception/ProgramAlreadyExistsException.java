package com.cg.uas.exception;

public class ProgramAlreadyExistsException extends Exception {
	@Override
	public String getMessage() {
		return ExceptionDetail.getExceptionDetail("ProgramAlreadyExists");
	}
}
