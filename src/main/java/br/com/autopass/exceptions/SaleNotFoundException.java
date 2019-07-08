package br.com.autopass.exceptions;

public class SaleNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public SaleNotFoundException(String msg) {
		super(msg);
	}
	
	public SaleNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
