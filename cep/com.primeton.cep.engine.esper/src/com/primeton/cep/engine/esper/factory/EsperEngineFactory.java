package com.primeton.cep.engine.esper.factory;

import com.espertech.esper.client.EPServiceProviderManager;
import com.primeton.cep.engine.api.CEPEngine;
import com.primeton.cep.engine.api.CEPEngineConfig;
import com.primeton.cep.engine.esper.api.EsperEngineConfig;
import com.primeton.cep.engine.esper.api.EsperEngineImpl;
import com.primeton.cep.engine.esper.util.UuidGenerator;

public class EsperEngineFactory {

	private static CEPEngine singleEngine_1 = null;
	private static CEPEngine singleEngine_2 = null;
	private static CEPEngine singleEngine_3 = null;
	private static CEPEngine singleEngine_4 = null;

	private static String factoryID = null;

	public static void setFactoryID(String fID) {
		if (factoryID == null || factoryID.trim().equals("")) {
			factoryID = fID;
		}
	}

	public static CEPEngine getInstance() {
		if (singleEngine_1 == null) {
			checkFactoryID();
			singleEngine_1 = new EsperEngineImpl(EPServiceProviderManager.getDefaultProvider());
		}
		return singleEngine_1;
	}

	public static CEPEngine getInstance(String engineName) {
		if (singleEngine_2 == null) {
			checkFactoryID();
			singleEngine_2 = new EsperEngineImpl(EPServiceProviderManager.getProvider(engineName));
		}
		return singleEngine_2;
	}

	public static CEPEngine getInstance(CEPEngineConfig config) {
		if (singleEngine_3 == null) {
			if (!(config instanceof EsperEngineConfig)) {
				// TODO throw a RuntimeException
			}
			EsperEngineConfig eConfig = (EsperEngineConfig) config;
			checkFactoryID();
			singleEngine_3 = new EsperEngineImpl(EPServiceProviderManager.getDefaultProvider(eConfig.getConfiguration()));
		}
		return singleEngine_3;
	}

	public static CEPEngine getInstance(String engineName, CEPEngineConfig config) {
		if (singleEngine_4 == null) {
			if (!(config instanceof EsperEngineConfig)) {
				// TODO throw a RuntimeException
			}
			EsperEngineConfig eConfig = (EsperEngineConfig) config;
			checkFactoryID();
			singleEngine_4 = new EsperEngineImpl(EPServiceProviderManager.getProvider(engineName, eConfig.getConfiguration()));
		}
		return singleEngine_4;
	}

	public static CEPEngine newInstance() {
		checkFactoryID();
		return new EsperEngineImpl(EPServiceProviderManager.getDefaultProvider());
	}

	public static CEPEngine newInstance(String engineName) {
		checkFactoryID();
		return new EsperEngineImpl(EPServiceProviderManager.getProvider(engineName));
	}

	public static CEPEngine newInstance(CEPEngineConfig config) {
		if (!(config instanceof EsperEngineConfig)) {
			// TODO throw a RuntimeException
		}
		EsperEngineConfig eConfig = (EsperEngineConfig) config;
		checkFactoryID();
		return new EsperEngineImpl(EPServiceProviderManager.getDefaultProvider(eConfig.getConfiguration()));
	}

	public static CEPEngine newInstance(String engineName, CEPEngineConfig config) {
		if (!(config instanceof EsperEngineConfig)) {
			// TODO throw a RuntimeException
		}
		EsperEngineConfig eConfig = (EsperEngineConfig) config;
		checkFactoryID();
		return new EsperEngineImpl(EPServiceProviderManager.getProvider(engineName, eConfig.getConfiguration()));
	}

	public String getFactoryID() {
		return factoryID;
	}

	private static void checkFactoryID() {
		if (factoryID == null || factoryID.trim().equals("")) {
			factoryID = UuidGenerator.generate();
		}
	}
}
