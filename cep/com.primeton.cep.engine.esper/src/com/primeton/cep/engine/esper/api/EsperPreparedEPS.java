package com.primeton.cep.engine.esper.api;

import com.espertech.esper.client.EPPreparedStatement;
import com.primeton.cep.engine.api.PreparedEPS;

public interface EsperPreparedEPS extends PreparedEPS {

	public EPPreparedStatement getPreparedEPL();
}
