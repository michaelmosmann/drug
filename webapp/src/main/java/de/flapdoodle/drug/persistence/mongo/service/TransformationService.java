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
