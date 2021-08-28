package org.vg.axon.coreapi.exceptions.order;

public class OrderAlreadyConfirmedException extends IllegalStateException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderAlreadyConfirmedException(String orderId) {
        super("Cannot perform operation because order [" + orderId + "] is already confirmed.");
    }
}
