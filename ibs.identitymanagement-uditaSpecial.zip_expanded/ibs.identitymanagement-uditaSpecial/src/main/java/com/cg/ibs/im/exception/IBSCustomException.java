package com.cg.ibs.im.exception;

public class IBSCustomException extends Exception {
	private static final long serialVersionUID = 4664456874499611218L;
	public IBSCustomException(String message) {
		System.out.println(message);
	}
}
