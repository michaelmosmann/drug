package de.flapdoodle.drug.parser.markdown;


import de.flapdoodle.drug.markup.Type;

public class TripleNode extends AbstractTripleNode {

	private final Type _type;
	public TripleNode(char type, boolean hidden, Integer index, String text) {
		super(hidden,index,text);

		_type = Type.fromChar(type);
		if (_type == null)
			throw new IllegalArgumentException("Unknown Type " + type);
	}

	@Override
	public String toString() {
		return toString(""+_type.asChar());
	}

	public Type getType() {
		return _type;
	}
}
