package de.flapdoodle.drug.markup;

public enum Type {
	Subject("s"),
	Predicate("p"),
	Object("o"),
	At("at"),
	NearBy("near"),
	From("from"),
	To("to"),
	;
	
	private final String _asString;

	Type(String asString) {
		_asString = asString;
	}
	
	public String asString() {
		return _asString;
	}
	
	public static Type fromString(String c) {
		for (Type t : Type.values()) {
			if (t._asString.equals(c)) return t;
		}
		return null;
	}
}