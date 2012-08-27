package de.flapdoodle.drug.persistence.dao;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.markup.Type;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.mongoom.types.Reference;

public class SearchDao {

	private final DescriptionDao _descriptionDao;
	private final TransformationDao _transformationDao;

	// cglib
	protected SearchDao() {
		_descriptionDao = null;
		_transformationDao = null;
	}

	@Inject
	public SearchDao(DescriptionDao descriptionDao, TransformationDao transformationDao) {
		_descriptionDao = descriptionDao;
		_transformationDao = transformationDao;
	}

	public List<Transformation> find(String subject, String predicate, String object, ContextType type, String context) {
		List<Transformation> ret = Lists.newArrayList();

		List<Description> subjects = subject != null
				? _descriptionDao.findByName(true, subject)
				: null;
		List<Description> predicates = predicate != null
				? _descriptionDao.findByName(false, predicate)
				: null;
		List<Description> objects = object != null
				? _descriptionDao.findByName(true, object)
				: null;
		List<Description> contexts = context != null
				? _descriptionDao.findByName(true, context)
				: null;

		Function<Description, Reference<Description>> toId = new Function<Description, Reference<Description>>() {

			@Override
			public Reference<Description> apply(Description from) {
				return from.getId();
			}
		};

		ret = _transformationDao.find(nullOrTransformation(subjects, toId), nullOrTransformation(predicates, toId),
				nullOrTransformation(objects, toId),type,nullOrTransformation(contexts, toId));
		return ret;
	}

	static <T, S> List<T> nullOrTransformation(List<S> list, Function<S, T> function) {
		if (list != null)
			return Lists.transform(list, function);
		return null;
	}
}
