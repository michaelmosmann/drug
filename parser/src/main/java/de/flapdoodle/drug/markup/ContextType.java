package de.flapdoodle.drug.markup;


public enum ContextType {
	At("at"),
	NearBy("near"),
	From("from"),
	To("to"),
	;
	
	private final String _asString;

	ContextType(String asString) {
		_asString = asString;
	}
	
	public String asString() {
		return _asString;
	}
	
	public static ContextType fromString(String c) {
		for (ContextType t : ContextType.values()) {
			if (t._asString.equals(c)) return t;
		}
		return null;
	}

}
