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

import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.mongo.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.mongo.service.DescriptionService;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.IDescriptionService;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.drug.persistence.service.TransformationDto;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;
import de.flapdoodle.wicket.model.Models;

public class TransformationProperties implements Function1<Map<ReferenceDto<DescriptionDto>, String>, TransformationDto> {

	@Inject
	IDescriptionService _descriptionDao;

	public TransformationProperties() {
		Injector.get().inject(this);
	}

	@Override
	public Map<ReferenceDto<DescriptionDto>, String> apply(TransformationDto t) {
		Set<ReferenceDto<DescriptionDto>> idList = Sets.newHashSet();

		addIfSet(idList, t.getSubject(), t.getPredicate(), t.getObject(), t.getContext());

		if (!idList.isEmpty())
			return _descriptionDao.names(idList);

		return Maps.newHashMap();
	}

	private void addIfSet(Set<ReferenceDto<DescriptionDto>> idList, ReferenceDto<DescriptionDto>... rl) {
		for (ReferenceDto<DescriptionDto> r : rl) {
			if (r != null)
				idList.add(r);
		}
	}

	public static IModel<Map<ReferenceDto<DescriptionDto>, String>> names(IModel<TransformationDto> transformation) {
		return Models.on(transformation).apply(new TransformationProperties());
	}
}
