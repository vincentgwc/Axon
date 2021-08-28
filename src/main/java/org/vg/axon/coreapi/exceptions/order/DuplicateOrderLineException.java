package org.vg.axon.coreapi.exceptions.order;

public class DuplicateOrderLineException extends IllegalStateException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateOrderLineException(String productId) {
        super("Cannot duplicate order line for product identifier [" + productId + "]");
    }
}
