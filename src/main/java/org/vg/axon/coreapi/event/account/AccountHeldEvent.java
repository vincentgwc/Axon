package org.vg.axon.coreapi.event.account;

import org.vg.axon.coreapi.event.BaseEvent;
import org.vg.axon.coreapi.queries.account.Status;

public class AccountHeldEvent extends BaseEvent<String> {
	public final Status status;

    public AccountHeldEvent(String id, Status status) {
        super(id);
        this.status = status;
    } 
}
