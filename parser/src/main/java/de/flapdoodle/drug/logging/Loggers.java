package de.flapdoodle.drug.logging;

import java.util.logging.Logger;

import de.flapdoodle.drug.parser.markdown.TripleReferenceVisitor;


public class Loggers {

	public static Logger getLogger(Class<TripleReferenceVisitor> clazz) {
		return Logger.getLogger(clazz.getName());
	}

}
