package org.vg.axon.coreapi.commands.account;

import org.vg.axon.coreapi.commands.BaseCommand;

public class GenericAccountRollbackCommand extends BaseCommand<String>{
	
	public String errorMsg;
	
	public GenericAccountRollbackCommand(String id, String errorMsg) {
		super(id);
		// TODO Auto-generated constructor stub
		this.errorMsg = errorMsg;
	}

}
