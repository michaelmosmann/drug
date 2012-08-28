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
package de.flapdoodle.drug.parser.markdown;

import java.util.Map;

import com.google.common.collect.Maps;

import de.flapdoodle.drug.markup.IRelation;


public class TripleNodeRelationMap {
	Map<AbstractTripleNode, IRelation> _relationMap=Maps.newHashMap();
	
	public void setFor(IRelation relation, AbstractTripleNode... nodes) {
		for (AbstractTripleNode n : nodes) {
			if (n!=null) _relationMap.put(n,relation);
		}
	}

	public IRelation get(AbstractTripleNode node) {
		return _relationMap.get(node);
	}
	
}
