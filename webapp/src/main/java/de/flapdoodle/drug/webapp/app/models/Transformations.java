package de.flapdoodle.drug.webapp.app.models;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.persistence.dao.SearchDao;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.Models;


public class Transformations implements Function3<List<Transformation>, String, String, String> {

	@Inject
	SearchDao _searchDao;
	
	public Transformations() {
		Injector.get().inject(this);
	}
	
	@Override
	public List<Transformation> apply(String subject,String predicate,String object) {
		return _searchDao.find(subject, predicate, object);
	}
	
	public static IModel<List<Transformation>> search(String subject,String predicate,String object) {
		return Models.on(Model.of(subject),Model.of(predicate),Model.of(object)).apply(new Transformations());
	}
}
