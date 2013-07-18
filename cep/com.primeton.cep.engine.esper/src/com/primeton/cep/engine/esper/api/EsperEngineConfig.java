package com.primeton.cep.engine.esper.api;

import com.espertech.esper.client.Configuration;
import com.primeton.cep.engine.api.CEPEngineConfig;

public interface EsperEngineConfig extends CEPEngineConfig {

	public Configuration getConfiguration();

}
