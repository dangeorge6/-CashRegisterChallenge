package com.dannyleavitt;

public class NotEnoughBillsException extends Exception {
	private static final long serialVersionUID = 1L;	
	private static final String MESSAGE = "Not enough bills in register to perform operation.";
	
	public NotEnoughBillsException() {
        super(MESSAGE);
    }
}
