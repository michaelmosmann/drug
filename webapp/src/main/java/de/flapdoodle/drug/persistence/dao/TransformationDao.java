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
package de.flapdoodle.drug.persistence.dao;

import java.util.List;

import com.google.common.collect.Lists;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.mongoom.AbstractDao;
import de.flapdoodle.mongoom.IEntityQuery;
import de.flapdoodle.mongoom.types.Reference;

public class TransformationDao extends AbstractDao<Transformation> {

	public TransformationDao() {
		super(Transformation.class);
	}

	public List<Transformation> find(List<Reference<Description>> subjects, List<Reference<Description>> predicates,
			List<Reference<Description>> objects,ContextType type,List<Reference<Description>> contexts) {
		
		int inQuery = 0;
		if ((subjects != null) && (!subjects.isEmpty()))
			inQuery++;
		if ((predicates != null) && (!predicates.isEmpty()))
			inQuery++;
		if ((objects != null) && (!objects.isEmpty()))
			inQuery++;
		if ((contexts != null) && (!contexts.isEmpty()))
			inQuery++;

		if (inQuery>=1) {
			IEntityQuery<Transformation> query = createQuery();
			if (subjects != null)
				query.field(Transformation.Subject).in(subjects);
			if (predicates != null)
				query.field(Transformation.Predicate).in(predicates);
			if (objects != null)
				query.field(Transformation.Object).in(objects);
			if (contexts != null) {
				query.field(Transformation.Context).in(contexts);
				if (type != null)
					query.field(Transformation.ContextType).eq(type);
			}
			return query.result().asList();
		}
		return Lists.newArrayList();
	}

}
