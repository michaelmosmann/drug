package de.flapdoodle.drug.markup;

public class RelationPart extends AbstractReference {

	private final Type _type;
	private final Integer _index;

	public RelationPart(Type type, Integer index, Label label) {
		super(label);
		_type = type;
		_index = index;
	}

	public Integer getIndex() {
		return _index;
	}

	public Type getType() {
		return _type;
	}

}
