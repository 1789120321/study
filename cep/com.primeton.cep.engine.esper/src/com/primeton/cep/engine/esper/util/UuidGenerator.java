package com.primeton.cep.engine.esper.util;

import java.util.UUID;

public class UuidGenerator {

	public static String generate() {
		return UUID.randomUUID().toString();
	}
}
