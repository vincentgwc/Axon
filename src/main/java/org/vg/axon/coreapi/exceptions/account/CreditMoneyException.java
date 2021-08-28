package org.vg.axon.coreapi.exceptions.account;

public class CreditMoneyException extends IllegalStateException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CreditMoneyException(String refNo) {
        super("CreditMoneyException [" + refNo + "]");
    }
}
