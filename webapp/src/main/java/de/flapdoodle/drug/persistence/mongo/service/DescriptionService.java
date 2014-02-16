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
import java.util.Map;
import java.util.Set;


import com.google.common.collect.Lists;
import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.IDescriptionService;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.mongoom.types.Reference;

public class DescriptionService implements IDescriptionService {

	@Inject
	DescriptionDao _descriptionDao;

	@Override
	public DescriptionDto get(ReferenceDto<DescriptionDto> reference) {
		return DescriptionAsDto.asDto(_descriptionDao.get(DtoAsReference.asReference(reference)));
	}
	
	@Override
	public DescriptionDto getByStringId(String id) {
		return DescriptionAsDto.asDto(_descriptionDao.getByStringId(id));
	}

	@Override
	public DescriptionDto getByName(String name) {
		return DescriptionAsDto.asDto(_descriptionDao.getByName(name));
	}

	@Override
	public List<DescriptionDto> findByName(boolean isObject, String name) {
		return Lists.transform(_descriptionDao.findByName(isObject, name), new DescriptionAsDto());
	}

	@Override
	public List<DescriptionDto> findAnyByName(String name) {
		return Lists.transform(_descriptionDao.findAnyByName(name), new DescriptionAsDto());
	}
	
	@Override
	public List<DescriptionDto> findByType(boolean isObject) {
		return Lists.transform(_descriptionDao.findByType(isObject), new DescriptionAsDto());
	}

	@Override
	public Map<ReferenceDto<DescriptionDto>, String> names(Set<ReferenceDto<DescriptionDto>> idList) {
		return ReferenceAsDto.asDtoMap(_descriptionDao.names(DtoAsReference.asReferencesSet(idList)));
	}

	@Override
	public DescriptionDto save(DescriptionDto descriptionDto) {
		Description description = DtoAsDescription.asDescription(descriptionDto);
		_descriptionDao.save(description);
		return DescriptionAsDto.asDto(description);
	}

	@Override
	public DescriptionDto update(DescriptionDto descriptionDto) {
		Description description = DtoAsDescription.asDescription(descriptionDto);
		_descriptionDao.update(description);
		return DescriptionAsDto.asDto(description);
	}


}
