package de.flapdoodle.drug.persistence.mongo.service;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;

public class ReferenceAsDto implements Function1<ReferenceDto<DescriptionDto>, Reference<Description>> {

	@Override
	public ReferenceDto<DescriptionDto> apply(Reference<Description> value) {
		return asDto(value);
	}

	static ReferenceDto<DescriptionDto> asDto(Reference<Description> value) {
		return value != null
				? new ReferenceDto<DescriptionDto>(DescriptionDto.class, value.getId().toString())
				: null;
	}

	public static Map<ReferenceDto<DescriptionDto>, String> asDtoMap(Map<Reference<Description>, String> names) {
		Map<ReferenceDto<DescriptionDto>, String> ret = Maps.newHashMap();
		for (Reference<Description> key : names.keySet()) {
			String value = names.get(key);
			ret.put(asDto(key), value);
		}
		return ret;
	}

}
