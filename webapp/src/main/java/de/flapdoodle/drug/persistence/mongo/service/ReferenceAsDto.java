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
