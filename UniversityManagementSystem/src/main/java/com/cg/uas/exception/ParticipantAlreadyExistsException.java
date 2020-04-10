package com.cg.uas.exception;

public class ParticipantAlreadyExistsException extends Exception {
	@Override
	public String getMessage() {
		return ExceptionDetail.getExceptionDetail("ParticipantAlreadyExistsException");
	}
}
