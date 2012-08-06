package de.flapdoodle.drug.markup;

public abstract class AbstractReference implements IPart {

	protected final Label _label;

	protected AbstractReference(Label label) {
		_label = label;
	}

	public Label getLabel() {
		return _label;
	}

}
