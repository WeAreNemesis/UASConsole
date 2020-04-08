package com.cg.uas.exception;

public class ApplicationAlreadyExistsException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7266810177597976084L;

	@Override
	public String getMessage() {
		return ExceptionDetail.getExceptionDetail("ApplicationAlreadyexists");
	}
}
