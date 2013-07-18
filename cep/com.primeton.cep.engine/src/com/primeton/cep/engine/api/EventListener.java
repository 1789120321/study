package com.primeton.cep.engine.api;

import com.primeton.cep.engine.model.Event;

public interface EventListener {

	public void update(Event[] newEvents, Event[] oldEvents);
}
