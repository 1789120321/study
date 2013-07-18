package com.primeton.cep.engine.esper.api;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.primeton.cep.engine.api.EPS;
import com.primeton.cep.engine.api.EventListener;
import com.primeton.cep.engine.esper.model.EsperEvent;
import com.primeton.cep.engine.model.Event;

public class EPSImpl implements EPS {

	private boolean _running = true;
	private boolean _suspend = false;

	private EPStatement stmt;
	private String name;

	public EPSImpl(EPStatement stmt, String name) {
		this.stmt = stmt;
		this.name = name;
	}

	public void addListener(final EventListener listener) {
		stmt.addListener(new UpdateListener() {
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				Event[] events1 = null;
				Event[] events2 = null;
				if (newEvents != null) {
					events1 = new Event[newEvents.length];
					for (int i = 0; i < newEvents.length; i++) {
						events1[i] = new EsperEvent(newEvents[i]);
					}
				}
				if (oldEvents != null) {
					events2 = new Event[oldEvents.length];
					for (int i = 0; i < oldEvents.length; i++) {
						events2[i] = new EsperEvent(oldEvents[i]);
					}
				}
				listener.update(events1, events2);
			}
		});
	}

	public String getEPS() {
		return stmt.getText();
	}

	public Event[] getViewEvents(boolean suspend) {
		if (suspend && !_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		// TODO obtain view's Events
		return null;
	}

	public Event[] getFromClauseEvents(boolean suspend) {
		if (suspend && !_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		// TODO obtain from-clause's Events 
		return null;
	}

	public Event[] getSelectClauseEvents(boolean suspend) {
		if (suspend && !_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		// TODO obtain select-clause's Events
		return null;
	}

	public Event[] getInsertClauseEvents(boolean suspend) {
		if (suspend && !_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		// TODO obtain insert-clause's Events
		return null;
	}

	public Event getLastEvent(boolean suspend) {
		if (suspend && !_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		// TODO obtain last Event
		return null;
	}

	public void setViewEvents(Event[] events, boolean suspend) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (suspend && !_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		// TODO input view Events
	}

	public void setFromClauseEvents(Event[] events, boolean suspend) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (suspend && !_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		// TODO input from-clause Events
	}

	public void setSelectClauseEvents(Event[] events, boolean suspend) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (suspend && !_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		// TODO input select-clause Events
	}

	public void setInsertClauseEvents(Event[] events, boolean suspend) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (suspend && !_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		// TODO input insert-clause Events
	}

	public boolean isPattern() {
		return stmt.isPattern();
	}

	public boolean isStarted() {
		return stmt.isStarted();
	}

	public boolean isStopped() {
		return stmt.isStopped();
	}

	public boolean isDestroyed() {
		return stmt.isDestroyed();
	}

	public void start() {
		stmt.start();
		_running = true;
	}

	public void stop() {
		stmt.stop();
		_running = false;
	}

	public void suspend() {
		_suspend = true;
	}

	public void resume() {
		_suspend = false;
	}

	public void destroy() {
		stmt.destroy();
	}

	public String getName() {
		if (stmt != null) {
			return name;
		}
		return null;
	}
}
