package de.flapdoodle.drug.parser.markdown;

import de.flapdoodle.drug.markup.ContextType;


public class TripleContextNode extends AbstractTripleNode {

	private ContextType _type;

	public TripleContextNode(String type, boolean hidden, Integer index, String text) {
		super(hidden, index, text);
		
		_type = ContextType.fromString(type);
		if (_type == null)
			throw new IllegalArgumentException("Unknown Type " + type);

	}

	public ContextType getType() {
		return _type;
	}

}
