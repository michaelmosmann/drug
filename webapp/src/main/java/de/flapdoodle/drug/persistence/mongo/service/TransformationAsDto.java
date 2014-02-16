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

import java.util.List;

import com.google.common.collect.Lists;

import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.drug.persistence.service.TransformationDto;
import de.flapdoodle.functions.Function1;

class TransformationAsDto implements Function1<TransformationDto, Transformation> {

	@Override
	public TransformationDto apply(Transformation transformation) {
		return asDto(transformation);
	}

	static TransformationDto asDto(Transformation src) {
		if (src != null) {
			TransformationDto ret = new TransformationDto();
			ret.setId(new ReferenceDto<TransformationDto>(TransformationDto.class, src.getId().getId().toString()));
			ret.setVersion(src.getVersion());

			ret.setSubject(ReferenceAsDto.asDto(src.getSubject()));
			ret.setPredicate(ReferenceAsDto.asDto(src.getPredicate()));
			ret.setObject(ReferenceAsDto.asDto(src.getObject()));
			ret.setContext(ReferenceAsDto.asDto(src.getContext()));
			ret.setContextType(src.getContextType());

			ret.setTitle(src.getTitle());
			ret.setText(src.getText());
			return ret;
		}
		return null;
	}

	public static List<TransformationDto> asDtos(List<Transformation> list) {
		return Lists.transform(list, new TransformationAsDto());
	}
}
