package de.flapdoodle.drug.render;

import java.io.Serializable;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.markup.Type;

public class TagReference implements Serializable {

	private final String _subject;
	private final String _predicate;
	private final String _object;

	private final ContextType _contextType;
	private final String _context;

	public TagReference(String subject, String predicate, String object, ContextType contextType, String context) {
		super();
		_subject = subject;
		_predicate = predicate;
		_object = object;
		_contextType = contextType;
		_context = context;
	}

	public String getSubject() {
		return _subject;
	}

	public String getPredicate() {
		return _predicate;
	}

	public String getObject() {
		return _object;
	}

	public ContextType getContextType() {
		return _contextType;
	}

	public String getContext() {
		return _context;
	}

	public boolean notSet() {
		return _subject==null && _predicate==null && _object==null && _context==null;
	}

	public boolean anythingIsSet() {
		return !notSet();
	}

	public boolean isOpen() {
		return _subject==null || _predicate==null || _object==null ;
	}

}
