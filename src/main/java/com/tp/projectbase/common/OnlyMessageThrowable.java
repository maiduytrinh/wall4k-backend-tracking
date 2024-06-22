package com.tp.projectbase.common;

public class OnlyMessageThrowable extends Throwable {

	public OnlyMessageThrowable(String message) {
		super(message, null, false, false);
	}
}