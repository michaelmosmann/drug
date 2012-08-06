package de.flapdoodle.drug.markup;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public abstract class AbstractContainer implements IPart {

	private final List<IPart> _parts;
	Map<Integer, RelationTriple> _relations = Maps.newHashMap();

	public AbstractContainer(List<IPart> parts) {
		_parts = parts;

		for (IPart p : _parts) {
			if (p instanceof RelationPart) {
				RelationPart relPart = (RelationPart) p;
				Integer index = relPart.getIndex();
				RelationTriple rel = _relations.get(index);
				if (rel == null) {
					rel = new RelationTriple();
					_relations.put(index, rel);
				}
				switch (relPart.getType()) {
					case Subject:
						rel.subject = relPart.getLabel();
						break;
					case Predicate:
						rel.predicate = relPart.getLabel();
						break;
					case Object:
						rel.object = relPart.getLabel();
						break;
				}
			}
		}
	}

	public void inspect(IMarkupVisitor visitor) {
		for (IPart p : _parts) {
			if (p instanceof Text) {
				visitor.text(((Text) p).getText());
			}
			if (p instanceof Key) {
				Key key = (Key) p;
				visitor.reference(key.getLabel());
			}
			if (p instanceof RelationPart) {
				RelationPart relPart = (RelationPart) p;
				switch (relPart.getType()) {
					case Subject:
						visitor.subject(relPart.getLabel(), _relations.get(relPart.getIndex()));
						break;
					case Predicate:
						visitor.predicate(relPart.getLabel(), _relations.get(relPart.getIndex()));
						break;
					case Object:
						visitor.object(relPart.getLabel(), _relations.get(relPart.getIndex()));
						break;
				}
			}
			if (p instanceof Block) {
				Block block=(Block) p;
				((Block) p).inspect(visitor);
			}
		}
	}

	private static class RelationTriple implements IRelation {

		Label subject;
		Label predicate;
		Label object;

		@Override
		public Label getSubject() {
			return subject;
		}

		@Override
		public Label getPredicate() {
			return predicate;
		}

		@Override
		public Label getObject() {
			return object;
		}

	}

}
