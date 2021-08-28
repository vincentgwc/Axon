package org.vg.axon.coreapi.event.account;

import org.vg.axon.coreapi.event.BaseEvent;
import org.vg.axon.coreapi.queries.account.Status;

public class AccountActivatedEvent extends BaseEvent<String> {
    public final Status status;

    public AccountActivatedEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
