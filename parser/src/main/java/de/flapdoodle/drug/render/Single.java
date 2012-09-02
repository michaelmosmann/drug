package de.flapdoodle.drug.render;

import de.flapdoodle.drug.markup.Label;

public class Single implements ITag {

	private final Label _label;

	public Single(Label label) {
		_label = label;
	}

	public String getName() {
		return _label.getName();
	}

	public String getText() {
		return _label.getDisplayOrName();
	}

}
