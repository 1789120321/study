package com.primeton.cep.engine.esper.model;

import com.espertech.esper.client.EventBean;
import com.primeton.cep.engine.model.Event;

public class EsperEvent implements Event {

	private EventBean event;

	public EsperEvent(EventBean event) {
		this.event = event;
	}

	public Class<?> getType() {
		return event.getEventType().getUnderlyingType();
	}

	public Object get(String name) {
		return event.get(name);
	}

}
