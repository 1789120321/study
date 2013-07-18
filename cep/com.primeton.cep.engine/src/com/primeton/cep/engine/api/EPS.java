package com.primeton.cep.engine.api;

import com.primeton.cep.engine.model.Event;

public interface EPS {

	public String getEPS();

	public void addListener(EventListener listener);

	/**
	 * 状态复制：查询view中包含的事件
	 * @param suspend 如果为true，则只有在suspend状态下才能复制事件
	 * @return
	 */
	public Event[] getViewEvents(boolean suspend);

	/**
	 * 状态复制：查询from子句中包含的事件，一般针对pattern中的follow
	 * @param suspend 如果为true，则只有在suspend状态下才能复制事件
	 * @return
	 */
	public Event[] getFromClauseEvents(boolean suspend);

	/**
	 * 状态复制：查询事件输入中途产生的某些聚合值，比如平均值，总和等等
	 * @param suspend 如果为true，则只有在suspend状态下才能复制事件
	 * @return
	 */
	public Event[] getSelectClauseEvents(boolean suspend);

	//  暂时想不到此接口的用途
	//	public Event[] getInsertClauseEvents();

	public Event getLastEvent(boolean suspend);

	/**
	 * 状态写入：将view中的事件写到新实例的view中
	 * @param suspend 如果为true，则只有在suspend状态下才能写入事件
	 * @param events
	 */
	public void setViewEvents(Event[] events, boolean suspend);

	/**
	 * 状态写入：将pattern中的事件写到新实例的pattern中，一般针对pattern中的follow
	 * @param suspend 如果为true，则只有在suspend状态下才能写入事件
	 * @param events
	 */
	public void setFromClauseEvents(Event[] events, boolean suspend);

	/**
	 * 状态写入：将事件输入中途产生的某些聚合值，比如平均值，总和等等，写到新实例中
	 * @param suspend 如果为true，则只有在suspend状态下才能写入事件
	 * @return
	 */
	public void setSelectClauseEvents(Event[] events, boolean suspend);

	//  暂时想不到此接口的用途
	//	public void setInsertClauseEvents(Event[] events);

	public boolean isPattern();

	public boolean isStarted();

	public boolean isStopped();

	public boolean isDestroyed();

	public void start();

	public void stop();

	public void suspend();

	public void resume();

	public void destroy();

	public String getName();
}
