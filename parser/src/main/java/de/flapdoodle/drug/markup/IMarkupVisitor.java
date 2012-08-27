package de.flapdoodle.drug.markup;


public interface IMarkupVisitor {

	void begin();

	void end();

	void text(String text);

	void reference(Label label);

	void subject(Label label, IRelation relation);

	void predicate(Label label, IRelation relation);

	void object(Label label, IRelation relation);

	void context(Label label, Type type, IRelation relation);
}
