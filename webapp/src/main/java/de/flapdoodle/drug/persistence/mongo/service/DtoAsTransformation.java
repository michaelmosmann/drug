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

import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.drug.persistence.service.TransformationDto;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;

class DtoAsTransformation implements Function1<Transformation, TransformationDto> {

	@Override
	public Transformation apply(TransformationDto transformationDto) {
		return asTransformation(transformationDto);
	}

	static Transformation asTransformation(TransformationDto src) {
		if (src != null) {
			Transformation ret = new Transformation();
			ret.setId(Reference.getInstance(Transformation.class, new ObjectId(src.getId().getId())));
			ret.setVersion(src.getVersion());

			ret.setSubject(DtoAsReference.asReference(src.getSubject()));
			ret.setPredicate(DtoAsReference.asReference(src.getPredicate()));
			ret.setObject(DtoAsReference.asReference(src.getObject()));
			ret.setContext(DtoAsReference.asReference(src.getContext()));
			ret.setContextType(src.getContextType());

			ret.setTitle(src.getTitle());
			ret.setText(src.getText());
			return ret;
		}
		return null;
	}
}
