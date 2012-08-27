package de.flapdoodle.drug.markup;


public interface IRelation {
	Label getSubject();
	Label getPredicate();
	Label getObject();
	Label getContext();
	Type getContextType();
}
