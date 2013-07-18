/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.named;

import com.espertech.esper.epl.lookup.SubordTableLookupStrategy;
import com.espertech.esper.util.StopCallback;

public class NamedWindowSubqueryStopCallback implements StopCallback {

	private final NamedWindowProcessorInstance processor;
	private final SubordTableLookupStrategy namedWindowSubqueryLookup;

	public NamedWindowSubqueryStopCallback(NamedWindowProcessorInstance processor, SubordTableLookupStrategy namedWindowSubqueryLookup) {
		this.processor = processor;
		this.namedWindowSubqueryLookup = namedWindowSubqueryLookup;
	}

	public void stop() {
		processor.getRootViewInstance().removeSubqueryLookupStrategy(namedWindowSubqueryLookup);
	}
}