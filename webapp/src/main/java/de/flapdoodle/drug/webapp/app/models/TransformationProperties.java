package de.flapdoodle.drug.webapp.app.models;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.bson.types.ObjectId;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;
import de.flapdoodle.wicket.model.Models;

public class TransformationProperties implements Function1<Map<Reference<Description>, String>, Transformation> {

	@Inject
	DescriptionDao _descriptionDao;

	public TransformationProperties() {
		Injector.get().inject(this);
	}

	@Override
	public Map<Reference<Description>, String> apply(Transformation t) {
		Set<Reference<Description>> idList = Sets.newHashSet();

		addIfSet(idList, t.getSubject(), t.getPredicate(), t.getObject(), t.getContext());

		if (!idList.isEmpty())
			return _descriptionDao.names(idList);

		return Maps.newHashMap();
	}

	private void addIfSet(Set<Reference<Description>> idList, Reference<Description>... rl) {
		for (Reference<Description> r : rl) {
			if (r != null)
				idList.add(r);
		}
	}

	public static IModel<Map<Reference<Description>, String>> names(IModel<Transformation> transformation) {
		return Models.on(transformation).apply(new TransformationProperties());
	}
}
