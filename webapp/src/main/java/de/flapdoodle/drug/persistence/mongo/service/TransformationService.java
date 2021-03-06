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
package de.flapdoodle.drug.persistence.mongo.service;

import java.util.List;

import com.google.inject.Inject;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.mongo.dao.TransformationDao;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.ITransformationService;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.drug.persistence.service.TransformationDto;
import de.flapdoodle.mongoom.types.Reference;

public class TransformationService implements ITransformationService {

	@Inject
	TransformationDao _transformationDao;

	@Override
	public TransformationDto getByStringId(String id) {
		return TransformationAsDto.asDto(_transformationDao.getByStringId(id));
	}

	@Override
	public List<TransformationDto> find(List<ReferenceDto<DescriptionDto>> subjects,
			List<ReferenceDto<DescriptionDto>> predicates, List<ReferenceDto<DescriptionDto>> objects, ContextType type,
			List<ReferenceDto<DescriptionDto>> contexts) {
		return TransformationAsDto.asDtos(_transformationDao.find(asReference(subjects), asReference(predicates),
				asReference(objects), type, asReference(contexts)));
	}
	
	@Override
	public TransformationDto save(TransformationDto transformationDto) {
		Transformation transformation = DtoAsTransformation.asTransformation(transformationDto);
		_transformationDao.save(transformation);
		return TransformationAsDto.asDto(transformation);
	}

	@Override
	public TransformationDto update(TransformationDto transformationDto) {
		Transformation transformation = DtoAsTransformation.asTransformation(transformationDto);
		_transformationDao.update(transformation);
		return TransformationAsDto.asDto(transformation);
	}


	private static List<Reference<Description>> asReference(List<ReferenceDto<DescriptionDto>> subjects) {
		return DtoAsReference.asReferences(subjects);
	}
}
