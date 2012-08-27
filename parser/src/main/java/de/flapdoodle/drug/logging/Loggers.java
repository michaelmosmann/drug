package de.flapdoodle.drug.logging;

import java.util.logging.Logger;


public class Loggers {

	public static Logger getLogger(Class<?> clazz) {
		return Logger.getLogger(clazz.getName());
	}

}
