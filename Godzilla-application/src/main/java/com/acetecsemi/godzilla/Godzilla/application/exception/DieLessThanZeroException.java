package com.acetecsemi.godzilla.Godzilla.application.exception;

public class DieLessThanZeroException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2856144257200831046L;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DieLessThanZeroException() {
		this.setMsg("Die Qty Less-Than Zero");
	}

	public String toString() {
		return this.getMsg();
	}
}
