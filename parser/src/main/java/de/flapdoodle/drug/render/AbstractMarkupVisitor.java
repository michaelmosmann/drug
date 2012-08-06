package de.flapdoodle.drug.render;

import de.flapdoodle.drug.markup.IMarkupVisitor;
import de.flapdoodle.drug.markup.IRelation;
import de.flapdoodle.drug.markup.Label;

public abstract class AbstractMarkupVisitor implements IMarkupVisitor {

	@Override
	public void begin() {
		// do nothing
	}

	@Override
	public void end() {
		// do nothing
	}
}
