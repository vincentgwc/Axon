package org.vg.axon.coreapi.exceptions.order;

public class UnconfirmedOrderException extends IllegalStateException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnconfirmedOrderException() {
        super("Cannot ship an order which has not been confirmed yet.");
    }
}
