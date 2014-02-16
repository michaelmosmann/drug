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
import java.util.Set;

import org.bson.types.ObjectId;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;


public class DtoAsReference implements Function1<Reference<Description>, ReferenceDto<DescriptionDto>> {

	@Override
	public Reference<Description> apply(ReferenceDto<DescriptionDto> value) {
		return asReference(value);
	}

	static Reference<Description> asReference(ReferenceDto<DescriptionDto> value) {
		return value !=null ? Reference.getInstance(Description.class, new ObjectId(value.getId())) : null;
	}

	public static List<Reference<Description>> asReferences(List<ReferenceDto<DescriptionDto>> subjects) {
		return subjects!=null ? Lists.transform(subjects, new DtoAsReference()) : null;
	}

	public static Set<Reference<Description>> asReferencesSet(Set<ReferenceDto<DescriptionDto>> idList) {
		return Sets.newHashSet(Collections2.transform(idList, new DtoAsReference()));
	}


}
