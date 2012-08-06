package de.flapdoodle.drug.persistence.dao;

import java.util.List;

import com.google.common.collect.Lists;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.mongoom.AbstractDao;
import de.flapdoodle.mongoom.IEntityQuery;
import de.flapdoodle.mongoom.types.Reference;

public class TransformationDao extends AbstractDao<Transformation> {

	public TransformationDao() {
		super(Transformation.class);
	}

	public List<Transformation> find(List<Reference<Description>> subjects, List<Reference<Description>> predicates,
			List<Reference<Description>> objects) {
		if ((subjects != null) && (predicates != null) && (objects != null)) {
			if ((!subjects.isEmpty()) && (!predicates.isEmpty()) && (!predicates.isEmpty())) {
				return createQuery().field(Transformation.Subject).in(subjects).field(Transformation.Predicate).in(predicates).field(
						Transformation.Object).in(objects).result().asList();
			}
		} else {
			int inQuery = 0;
			if ((subjects != null) && (!subjects.isEmpty()))
				inQuery++;
			if ((predicates != null) && (!predicates.isEmpty()))
				inQuery++;
			if ((objects != null) && (!objects.isEmpty()))
				inQuery++;

			if (inQuery>=1) {
				IEntityQuery<Transformation> query = createQuery();
				if (subjects != null)
					query.field(Transformation.Subject).in(subjects);
				if (predicates != null)
					query.field(Transformation.Predicate).in(predicates);
				if (objects != null)
					query.field(Transformation.Object).in(objects);
				return query.result().asList();
			}
		}
		return Lists.newArrayList();
	}

}
