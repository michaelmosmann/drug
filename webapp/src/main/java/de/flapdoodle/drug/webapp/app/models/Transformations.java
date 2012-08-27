package de.flapdoodle.drug.webapp.app.models;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.persistence.dao.SearchDao;
import de.flapdoodle.drug.render.TagReference;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.Models;


public class Transformations implements Function1<List<Transformation>, TagReference> {

	@Inject
	SearchDao _searchDao;
	
	public Transformations() {
		Injector.get().inject(this);
	}
	
	@Override
	public List<Transformation> apply(TagReference ref) {
		return _searchDao.find(ref.getSubject(), ref.getPredicate(), ref.getObject(), ref.getContextType(), ref.getContext());
	}
	
	public static IModel<List<Transformation>> search(TagReference reference) {
		// TODO Auto-generated method stub
		return Models.on(Model.of(reference)).apply(new Transformations());
	}
}
