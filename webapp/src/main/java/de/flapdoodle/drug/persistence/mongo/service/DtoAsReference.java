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
