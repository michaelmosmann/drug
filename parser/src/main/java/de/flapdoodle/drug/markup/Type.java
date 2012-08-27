package de.flapdoodle.drug.markup;

public enum Type {
	Subject('s'),
	Predicate('p'),
	Object('o'),
	;
	
	private final char _asChar;

	Type(char asString) {
		_asChar = asString;
	}
	
	public char asChar() {
		return _asChar;
	}
	
	public static Type fromChar(char c) {
		for (Type t : Type.values()) {
			if (t._asChar==c) return t;
		}
		return null;
	}
}