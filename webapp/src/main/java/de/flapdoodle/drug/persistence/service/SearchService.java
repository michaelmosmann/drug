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
package de.flapdoodle.drug.persistence.service;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.mongo.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.mongo.dao.TransformationDao;
import de.flapdoodle.mongoom.types.Reference;


public class SearchService {

	@Inject
	IDescriptionService _descriptionDao;
	
	@Inject
	ITransformationService _transformationDao;

	public List<TransformationDto> find(String subject, String predicate, String object, ContextType type, String context) {
		List<TransformationDto> ret = Lists.newArrayList();

		List<DescriptionDto> subjects = subject != null
				? _descriptionDao.findByName(true, subject)
				: null;
		List<DescriptionDto> predicates = predicate != null
				? _descriptionDao.findByName(false, predicate)
				: null;
		List<DescriptionDto> objects = object != null
				? _descriptionDao.findByName(true, object)
				: null;
		List<DescriptionDto> contexts = (context != null)
				? _descriptionDao.findByName(true, context)
				: null;

		Function<DescriptionDto, ReferenceDto<DescriptionDto>> toId = new Function<DescriptionDto, ReferenceDto<DescriptionDto>>() {

			@Override
			public ReferenceDto<DescriptionDto> apply(DescriptionDto from) {
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
