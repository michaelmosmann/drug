package de.flapdoodle.drug.persistence.service;


public interface IDescriptionService {

	DescriptionDto getByStringId(String id);

	DescriptionDto getByName(String name);

	DescriptionDto save(DescriptionDto descriptionDto);

	DescriptionDto update(DescriptionDto descriptionDto);
}
