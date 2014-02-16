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