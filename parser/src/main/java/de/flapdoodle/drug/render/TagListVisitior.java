/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.drug.render;

import java.util.List;

import com.google.common.collect.Lists;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.markup.IRelation;
import de.flapdoodle.drug.markup.Label;
import de.flapdoodle.drug.markup.Type;

public class TagListVisitior extends AbstractMarkupVisitor {

	List<ITag> _tags = Lists.newArrayList();

	@Override
	public void text(String text) {
		_tags.add(new Text(text));
	}

	@Override
	public void reference(Label label) {
		_tags.add(new Single(label));
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
	public void context(Label label, ContextType type, IRelation relation) {
		relation(label, true, relation);
	}
	
	@Override
	public void blockStart(String typeAsName) {
		
	}
	
	@Override
	public void blockEnd(String typeAsName) {
		
	}

	private void relation(Label label, boolean isObject, IRelation relation) {
		if (relation != null) {
			String subject = notNull(relation.getSubject()).getName();
			String predicate = notNull(relation.getPredicate()).getName();
			String object = notNull(relation.getObject()).getName();
			String context = notNull(relation.getContext()).getName();
			ContextType contextType = relation.getContextType();
			_tags.add(new Tag(label.getDisplayOrName(), label.getName(), isObject, new TagReference(subject, predicate,
					object, contextType, context)));
		} else {
			_tags.add(new Tag(label.getDisplayOrName(), label.getName()));
		}
	}

	private Label notNull(Label subject) {
		return subject != null
				? subject
				: new Label(null);
	}

	public List<ITag> getTags() {
		return Lists.newArrayList(_tags);
	}
}
