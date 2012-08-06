package de.flapdoodle.drug.markup;

import org.antlr.runtime.RecognitionException;


public class MarkupException extends Exception {

	public MarkupException(String text, RecognitionException e) {
		super(text,e);
	}

}
