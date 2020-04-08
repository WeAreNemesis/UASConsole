package com.cg.uas.exception;

public class NoSuchApplication extends Exception {
	
	private static final long serialVersionUID = 4139989707818047202L;

	@Override
	public String getMessage() {
		return ExceptionDetail.getExceptionDetail("NoSuchApplication");
	}
}
