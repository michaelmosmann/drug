package de.flapdoodle.drug.persistence.beans;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.mongoom.IEntity;
import de.flapdoodle.mongoom.annotations.Entity;
import de.flapdoodle.mongoom.annotations.Id;
import de.flapdoodle.mongoom.annotations.index.IndexGroup;
import de.flapdoodle.mongoom.annotations.index.IndexGroups;
import de.flapdoodle.mongoom.annotations.index.IndexedInGroup;
import de.flapdoodle.mongoom.mapping.properties.PropertyReference;
import de.flapdoodle.mongoom.types.Reference;

@Entity(value = "transformation")
@IndexGroups({@IndexGroup(name = "threeKey", group = "threeKey"),
		@IndexGroup(name = "contextKey", group = "contextKey")})
public class Transformation extends AbstractDescription implements IEntity<Transformation> {

	public static final PropertyReference<Reference<Description>> Subject = (PropertyReference) de.flapdoodle.mongoom.mapping.properties.Property.ref(
			"subject", Reference.class);
	public static final PropertyReference<Reference<Description>> Predicate = (PropertyReference) de.flapdoodle.mongoom.mapping.properties.Property.ref(
			"predicate", Reference.class);
	public static final PropertyReference<Reference<Description>> Object = (PropertyReference) de.flapdoodle.mongoom.mapping.properties.Property.ref(
			"object", Reference.class);
	public static final PropertyReference<Reference<Description>> Context = (PropertyReference) de.flapdoodle.mongoom.mapping.properties.Property.ref(
			"context", Reference.class);
	public static final PropertyReference<ContextType> ContextType = de.flapdoodle.mongoom.mapping.properties.Property.ref(
			"contextType", ContextType.class);

	@Id
	Reference<Transformation> _id;

	@Override
	public Reference<Transformation> getId() {
		return _id;
	}

	@IndexedInGroup(group = "threeKey", priority = 11)
	Reference<Description> _subject;

	@IndexedInGroup(group = "threeKey", priority = 9)
	Reference<Description> _predicate;

	@IndexedInGroup(group = "threeKey", priority = 10)
	Reference<Description> _object;

	@IndexedInGroup(group = "contextKey", priority = 20)
	Reference<Description> _context;

	@IndexedInGroup(group = "contextKey", priority = 21)
	ContextType _contextType;

	String _title;

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public Reference<Description> getSubject() {
		return _subject;
	}

	public void setSubject(Reference<Description> subject) {
		_subject = subject;
	}

	public Reference<Description> getPredicate() {
		return _predicate;
	}

	public void setPredicate(Reference<Description> predicate) {
		_predicate = predicate;
	}

	public Reference<Description> getObject() {
		return _object;
	}

	public void setObject(Reference<Description> object) {
		_object = object;
	}

	public Reference<Description> getContext() {
		return _context;
	}

	public void setContext(Reference<Description> context) {
		_context = context;
	}

	public ContextType getContextType() {
		return _contextType;
	}

	public void setContextType(ContextType contextType) {
		_contextType = contextType;
	}
}
