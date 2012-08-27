package de.flapdoodle.drug.render;

import de.flapdoodle.drug.markup.Type;

public class Tag {

	private final String _text;
	private String _name;
	private String _subject;
	private String _predicate;
	private String _object;
	private String _context;
	private String _relName;
	private boolean _isObject;
	private Type _contextType;

	public Tag(String text) {
		_text = text;
	}

	public Tag(String text, String name) {
		_text = text;
		_name = name;
	}

	public Tag(String text, String name, boolean isObject, String subject, String predicate, String object,
			Type contextType, String context) {
		_text = text;
		_subject = subject;
		_predicate = predicate;
		_object = object;
		_relName = name;
		_isObject = isObject;
		_contextType = contextType;
		_context = context;
	}

	public boolean isText() {
		return _name == null && _subject == null && _predicate == null && _object == null;
	}

	public boolean isRelation() {
		return _subject != null || _predicate != null || _object != null;
	}

	public String getText() {
		return _text;
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

	public String getContext() {
		return _context;
	}

	public Type getContextType() {
		return _contextType;
	}

	public String getName() {
		return _name;
	}

	public String getRelName() {
		return _relName;
	}

	public boolean isObject() {
		return _isObject;
	}
}