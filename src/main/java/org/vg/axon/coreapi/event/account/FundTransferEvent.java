package org.vg.axon.coreapi.event.account;

import org.vg.axon.coreapi.event.BaseEvent;

public class FundTransferEvent extends BaseEvent<String> {
	public String fromAcc;

	public String toAcc;

	public double txnAmount;
	
	public String currency;

	public FundTransferEvent(String id, String fromAcc, String toAcc, double txnAmount, String currency) {
		super(id);
		this.fromAcc = fromAcc;
		this.toAcc = toAcc;
		this.txnAmount = txnAmount;
		this.currency = currency;
	}

}
