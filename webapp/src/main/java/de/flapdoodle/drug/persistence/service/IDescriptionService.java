package de.flapdoodle.drug.persistence.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.flapdoodle.drug.persistence.mongo.beans.Description;

public interface IDescriptionService {

	DescriptionDto get(ReferenceDto<DescriptionDto> reference);

	DescriptionDto getByStringId(String id);

	DescriptionDto getByName(String name);

	DescriptionDto save(DescriptionDto descriptionDto);

	DescriptionDto update(DescriptionDto descriptionDto);

	List<DescriptionDto> findByName(boolean isObject, String name);

	List<DescriptionDto> findByType(boolean isObject);

	List<DescriptionDto> findAnyByName(String name);

	Map<ReferenceDto<DescriptionDto>, String> names(Set<ReferenceDto<DescriptionDto>> idList);

}
