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
