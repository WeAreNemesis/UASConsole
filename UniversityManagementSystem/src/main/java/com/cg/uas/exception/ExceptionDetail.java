package com.cg.uas.exception;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExceptionDetail {
	public static String getExceptionDetail(String exception) {
		try {
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream("src/main/resources/exception/ExceptionMessage.properties");

			props.load(fis);
			String message = props.getProperty(exception);
			if (message == null) {
				return "Unknown error occured.";
			} else {
				return message;
			}
		} catch (IOException e) {
			return "Unknown error occured.";
		}
	}
}
