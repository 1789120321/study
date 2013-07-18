package com.primeton.cep.engine.api;

public interface CEPEngine {

	public EPS createEPS(PreparedEPS peps);

	public EPS createEPS(PreparedEPS peps, String name);

	public EPS createEPS(String stmt);

	public EPS createEPS(String stmt, String name);

	public PreparedEPS createPreparedEPS(String pstmt);

	public PreparedEPS createPreparedEPS(String pstmt, String name);

	public EPS createPattern(String stmt);

	public EPS createContext(String stmt);

	public EPS[] getAllEPSs();

	public PreparedEPS[] getAllPreparedEPSs();

	public CEPEngineConfig getEngineConfig();

	public String getEngineInfo();

	public CEPEngineRuntime getEngineRuntime();

	public EPS getEPS(String name);

	public PreparedEPS getPreparedEPS(String name);

	public void stopAllEPS();

	public void startAllEPS();

	public void destroyAllEPS();

	public void start();

	public void stop();

	public void resume();

	public void suspend();

	public void destroy();

	public String getFullName();

	public String getName();
}
