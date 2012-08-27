package de.flapdoodle.drug.render;

import java.util.List;

import com.google.common.collect.Lists;

import de.flapdoodle.drug.markup.IRelation;
import de.flapdoodle.drug.markup.Label;
import de.flapdoodle.drug.markup.Type;

public class TagListVisitior extends AbstractMarkupVisitor {

	List<Tag> _tags = Lists.newArrayList();

	@Override
	public void text(String text) {
		_tags.add(new Tag(text));
	}

	@Override
	public void reference(Label label) {
		_tags.add(new Tag(label.getDisplayOrName(), label.getName()));
	}

	@Override
	public void subject(Label label, IRelation relation) {
		relation(label, true, relation);
	}

	@Override
	public void predicate(Label label, IRelation relation) {
		relation(label, false, relation);
	}

	@Override
	public void object(Label label, IRelation relation) {
		relation(label, true, relation);
	}

	@Override
	public void context(Label label, Type type, IRelation relation) {
		relation(label, true, relation);
	}

	private void relation(Label label, boolean isObject, IRelation relation) {
		if (relation != null) {
			String subject = notNull(relation.getSubject()).getName();
			String predicate = notNull(relation.getPredicate()).getName();
			String object = notNull(relation.getObject()).getName();
			String context = notNull(relation.getContext()).getName();
			Type contextType = relation.getContextType();
			_tags.add(new Tag(label.getDisplayOrName(), label.getName(), isObject, subject, predicate, object, contextType,
					context));
		} else {
			_tags.add(new Tag(label.getDisplayOrName(), label.getName()));
		}
	}

	private Label notNull(Label subject) {
		return subject != null
				? subject
				: new Label(null);
	}

	public List<Tag> getTags() {
		return Lists.newArrayList(_tags);
	}
}
