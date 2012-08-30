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

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.markup.Type;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.mongoom.types.Reference;

public class SearchDao {

	private final DescriptionDao _descriptionDao;
	private final TransformationDao _transformationDao;

	// cglib
	protected SearchDao() {
		_descriptionDao = null;
		_transformationDao = null;
	}

	@Inject
	public SearchDao(DescriptionDao descriptionDao, TransformationDao transformationDao) {
		_descriptionDao = descriptionDao;
		_transformationDao = transformationDao;
	}

	public List<Transformation> find(String subject, String predicate, String object, ContextType type, String context) {
		List<Transformation> ret = Lists.newArrayList();

		List<Description> subjects = subject != null
				? _descriptionDao.findByName(true, subject)
				: null;
		List<Description> predicates = predicate != null
				? _descriptionDao.findByName(false, predicate)
				: null;
		List<Description> objects = object != null
				? _descriptionDao.findByName(true, object)
				: null;
		List<Description> contexts = (context != null)
				? _descriptionDao.findByName(true, context)
				: null;

		Function<Description, Reference<Description>> toId = new Function<Description, Reference<Description>>() {

			@Override
			public Reference<Description> apply(Description from) {
				return from.getId();
			}
		};

		ret = _transformationDao.find(nullOrTransformation(subjects, toId), nullOrTransformation(predicates, toId),
				nullOrTransformation(objects, toId),type,nullOrTransformation(contexts, toId));
		return ret;
	}

	static <T, S> List<T> nullOrTransformation(List<S> list, Function<S, T> function) {
		if (list != null)
			return Lists.transform(list, function);
		return null;
	}
}
