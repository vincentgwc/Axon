package org.vg.axon.coreapi.exceptions.account;

public class CreateAccountException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CreateAccountException(String refNo) {
        super("CreateAccountException [" + refNo + "]");
    }
}
