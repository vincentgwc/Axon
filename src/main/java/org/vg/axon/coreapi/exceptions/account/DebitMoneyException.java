package org.vg.axon.coreapi.exceptions.account;

public class DebitMoneyException extends IllegalStateException {
	private static final long serialVersionUID = 1L;
	
	public DebitMoneyException(String refNo) {
        super("DebitMoneyException [" + refNo + "]");
    }
}
