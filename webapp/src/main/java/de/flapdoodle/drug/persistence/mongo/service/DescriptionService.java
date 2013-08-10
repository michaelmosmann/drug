package de.flapdoodle.drug.persistence.mongo.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.IDescriptionService;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;


public class DescriptionService implements IDescriptionService {

	@Inject
	DescriptionDao _descriptionDao;

	@Override
	public DescriptionDto getByStringId(String id) {
		return asDto(_descriptionDao.getByStringId(id));
	}
	
	@Override
	public DescriptionDto getByName(String name) {
		return asDto(_descriptionDao.getByName(name));
	}
	
	@Override
	public DescriptionDto save(DescriptionDto descriptionDto) {
		Description description = asDescription(descriptionDto);
		_descriptionDao.save(description);
		return asDto(description);
	}
	
	@Override
	public DescriptionDto update(DescriptionDto descriptionDto) {
		Description description = asDescription(descriptionDto);
		_descriptionDao.update(description);
		return asDto(description);
	}

	private static DescriptionDto asDto(Description description) {
		return new DescriptionAsDto().apply(description);
	}

	private static Description asDescription(DescriptionDto descriptionDto) {
		return new DtoAsDescription().apply(descriptionDto);
	}
	
	static class DescriptionAsDto implements Function1<DescriptionDto, Description> {

		@Override
		public DescriptionDto apply(Description src) {
			if (src!=null) {
				DescriptionDto ret = new DescriptionDto();
				ret.setId(src.getId().getId().toString());
				ret.setVersion(src.getVersion());
				ret.setName(src.getName());
				ret.setObject(src.isObject());
				ret.setOtherNames(src.getOtherNames());
				ret.setText(src.getText());
				return ret;
			}
			return null;
		}
	}

	static class DtoAsDescription implements Function1<Description, DescriptionDto> {

		@Override
		public Description apply(DescriptionDto src) {
			if (src!=null) {
				Description ret = new Description();
				ret.setId(Reference.getInstance(Description.class, new ObjectId(src.getId())));
				ret.setVersion(src.getVersion());
				ret.setName(src.getName());
				ret.setObject(src.isObject());
				ret.setOtherNames(src.getOtherNames());
				ret.setText(src.getText());
				return ret;
			}
			return null;
		}
	}
}
