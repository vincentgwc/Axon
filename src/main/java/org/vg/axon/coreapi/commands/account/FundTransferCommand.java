package org.vg.axon.coreapi.commands.account;

import org.vg.axon.coreapi.commands.BaseCommand;

public class FundTransferCommand extends BaseCommand<String> {
	
	public String fromAcc;
	
	public String toAcc;
	
	public double txnAmount;
	
	public String currency;
	
	public FundTransferCommand(String id, String fromAcc, String toAcc, double txnAmount, String currency) {
		super(id);
		this.fromAcc = fromAcc;
		this.toAcc = toAcc;
		this.txnAmount =txnAmount;
		this.currency = currency;
	}

}
