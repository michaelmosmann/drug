package de.flapdoodle.drug.render;

import de.flapdoodle.drug.markup.Type;

public class Tag {

	private final String _text;
	private String _name;
//	private String _subject;
//	private String _predicate;
//	private String _object;
//	private String _context;
	private String _relName;
	private boolean _isObject;
//	private Type _contextType;
	TagReference _reference;

	public Tag(String text) {
		_text = text;
	}

	public Tag(String text, String name) {
		_text = text;
		_name = name;
	}

	public Tag(String text, String name, boolean isObject, TagReference reference) {
		_text = text;
		_reference=reference;
		_relName = name;
		_isObject = isObject;
	}

	public boolean isText() {
		return _name == null && (_reference==null || _reference.notSet());
	}

	public boolean isRelation() {
		return _reference!=null && _reference.anythingIsSet();
	}

	public String getText() {
		return _text;
	}
	
	public TagReference getReference() {
		return _reference;
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