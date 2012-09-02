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

import java.util.Set;

import com.google.common.collect.Sets;

import de.flapdoodle.drug.markup.IRelation;
import de.flapdoodle.drug.markup.Label;

public class Block {

	private Block() {
		// no instance
	}

	static abstract class AbstractBlock implements ITag {

		private String _type;

		public AbstractBlock(String typeAsName) {
			_type = typeAsName;
		}

		public String getType() {
			return _type;
		}

	}

	public static class Start extends AbstractBlock {

		Set<String> _references = Sets.newHashSet();
		Set<TagReference> _relations = Sets.newHashSet();

		public Start(String typeAsName) {
			super(typeAsName);
		}

		public void addReference(String name) {
			_references.add(name);
		}

		public void addRelation(TagReference tagReference) {
			_relations.add(tagReference);
		}

		public Set<String> getReferences() {
			return Sets.newHashSet(_references);
		}

		public Set<TagReference> getRelations() {
			return Sets.newHashSet(_relations);
		}
	}

	public static class End extends AbstractBlock {

		private final Start _start;

		public End(String typeAsName, Start matching) {
			super(typeAsName);
			_start = matching;
		}

		public Set<String> getReferences() {
			return _start.getReferences();
		}

		public Set<TagReference> getRelations() {
			return _start.getRelations();
		}

	}
}
