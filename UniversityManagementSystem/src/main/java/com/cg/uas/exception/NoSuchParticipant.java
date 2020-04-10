package com.cg.uas.exception;

public class NoSuchParticipant extends Exception{
	@Override
	public String getMessage() {
		return ExceptionDetail.getExceptionDetail("NoSuchParticipant");
	}
}
