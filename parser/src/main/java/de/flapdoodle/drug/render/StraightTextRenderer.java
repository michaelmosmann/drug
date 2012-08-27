package de.flapdoodle.drug.render;

import de.flapdoodle.drug.markup.IMarkupVisitor;
import de.flapdoodle.drug.markup.IRelation;
import de.flapdoodle.drug.markup.Label;
import de.flapdoodle.drug.markup.Type;


public class StraightTextRenderer extends AbstractMarkupVisitor {

	StringBuilder sb=new StringBuilder();

	@Override
	public void text(String text) {
		sb.append(text);
	}

	@Override
	public void reference(Label label) {
		sb.append(displayOrName(label));
	}

	private String displayOrName(Label label) {
		return label.getDisplay()!=null ? label.getDisplay() : label.getName();
	}

	@Override
	public void subject(Label label, IRelation relation) {
		sb.append(displayOrName(label));
	}

	@Override
	public void predicate(Label label, IRelation relation) {
		sb.append(displayOrName(label));
	}

	@Override
	public void object(Label label, IRelation relation) {
		sb.append(displayOrName(label));
	}
	
	@Override
	public void context(Label label, Type type, IRelation relation) {
		sb.append(displayOrName(label));
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
}
