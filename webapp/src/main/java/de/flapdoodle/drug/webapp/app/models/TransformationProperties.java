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
package de.flapdoodle.drug.webapp.app.models;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.bson.types.ObjectId;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;
import de.flapdoodle.wicket.model.Models;

public class TransformationProperties implements Function1<Map<Reference<Description>, String>, Transformation> {

	@Inject
	DescriptionDao _descriptionDao;

	public TransformationProperties() {
		Injector.get().inject(this);
	}

	@Override
	public Map<Reference<Description>, String> apply(Transformation t) {
		Set<Reference<Description>> idList = Sets.newHashSet();

		addIfSet(idList, t.getSubject(), t.getPredicate(), t.getObject(), t.getContext());

		if (!idList.isEmpty())
			return _descriptionDao.names(idList);

		return Maps.newHashMap();
	}

	private void addIfSet(Set<Reference<Description>> idList, Reference<Description>... rl) {
		for (Reference<Description> r : rl) {
			if (r != null)
				idList.add(r);
		}
	}

	public static IModel<Map<Reference<Description>, String>> names(IModel<Transformation> transformation) {
		return Models.on(transformation).apply(new TransformationProperties());
	}
}
