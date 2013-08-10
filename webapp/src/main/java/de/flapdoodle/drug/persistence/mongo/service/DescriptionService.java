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
