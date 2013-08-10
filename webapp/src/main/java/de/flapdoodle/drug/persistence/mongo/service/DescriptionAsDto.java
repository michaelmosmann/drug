package de.flapdoodle.drug.persistence.mongo.service;

import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.functions.Function1;

class DescriptionAsDto implements Function1<DescriptionDto, Description> {

	@Override
	public DescriptionDto apply(Description description) {
		return asDto(description);
	}

	static DescriptionDto asDto(Description src) {
		if (src != null) {
			DescriptionDto ret = new DescriptionDto();
			ret.setId(ReferenceAsDto.asDto(src.getId()));
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