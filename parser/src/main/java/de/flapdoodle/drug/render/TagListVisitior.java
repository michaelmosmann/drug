package de.flapdoodle.drug.render;

import java.util.List;

import com.google.common.collect.Lists;

import de.flapdoodle.drug.markup.IRelation;
import de.flapdoodle.drug.markup.Label;

@Deprecated
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

	private void relation(Label label, boolean isObject, IRelation relation) {
		if (relation != null) {
			String subject = notNull(relation.getSubject()).getName();
			String predicate = notNull(relation.getPredicate()).getName();
			String object = notNull(relation.getObject()).getName();
			_tags.add(new Tag(label.getDisplayOrName(), label.getName(), isObject, subject, predicate, object));
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

	public static class Tag {

		private final String _text;
		private String _name;
		private String _subject;
		private String _predicate;
		private String _object;
		private String _relName;
		private boolean _isObject;

		public Tag(String text) {
			_text = text;
		}

		public Tag(String text, String name) {
			_text = text;
			_name = name;
		}

		public Tag(String text, String name, boolean isObject, String subject, String predicate, String object) {
			_text = text;
			_subject = subject;
			_predicate = predicate;
			_object = object;
			_relName = name;
			_isObject=isObject;
		}

		public boolean isText() {
			return _name == null && _subject == null && _predicate == null && _object == null;
		}

		public boolean isRelation() {
			return _subject != null || _predicate != null || _object != null;
		}

		public String getText() {
			return _text;
		}

		public String getSubject() {
			return _subject;
		}

		public String getPredicate() {
			return _predicate;
		}

		public String getObject() {
			return _object;
		}

		public String getName() {
			return _name;
		}

		public String getRelName() {
			return _relName;
		}
		
		public boolean isObject() {
			return _isObject;
		}
	}
}
