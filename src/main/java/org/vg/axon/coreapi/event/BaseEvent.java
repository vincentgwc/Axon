package org.vg.axon.coreapi.event;

public class BaseEvent<T> {
	public final T id;

	public BaseEvent(T id) {
		this.id = id;
	}
}
