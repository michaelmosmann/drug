package de.flapdoodle.drug.persistence.mongo.service;

import org.bson.types.ObjectId;

import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;

class DtoAsDescription implements Function1<Description, DescriptionDto> {

	@Override
	public Description apply(DescriptionDto descriptionDto) {
		return asDescription(descriptionDto);
	}

	static Description asDescription(DescriptionDto src) {
		if (src != null) {
			Description ret = new Description();
			ret.setId(Reference.getInstance(Description.class, new ObjectId(src.getId().getId())));
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