package de.flapdoodle.drug.persistence.service;

import java.util.List;

import de.flapdoodle.drug.markup.ContextType;


public interface ITransformationService {

	TransformationDto getByStringId(String id);

	List<TransformationDto> find(List<ReferenceDto<DescriptionDto>> subjects,
			List<ReferenceDto<DescriptionDto>> predicates, List<ReferenceDto<DescriptionDto>> objects, ContextType type,
			List<ReferenceDto<DescriptionDto>> contexts);

	TransformationDto save(TransformationDto transformation);

	TransformationDto update(TransformationDto transformation);

}
