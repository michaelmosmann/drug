package de.flapdoodle.drug.webapp.app.view;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.bson.types.ObjectId;

import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.dao.SearchDao;
import de.flapdoodle.drug.persistence.dao.TransformationDao;
import de.flapdoodle.drug.webapp.app.models.ListModels;
import de.flapdoodle.drug.webapp.app.models.Transformations;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.pages.AbstractBasePage;
import de.flapdoodle.mongoom.types.Reference;

public class TransformationPage extends AbstractBasePage {

//	static final String P_SUB = "Subjekt";
//	static final String P_PRED = "Predikat";
//	static final String P_OBJ = "Objekt";

	static final String P_TRANS = "Id";

	@Inject
	TransformationDao _transformationDao;

	@Inject
	DescriptionDao _descriptionDao;

	@Inject
	SearchDao _searchDao;

	public TransformationPage(PageParameters pageParameters) {
		final String id = pageParameters.get(P_TRANS).toOptionalString();

//		if (id != null) {
//			Transformation transformation = _transformationDao.get(Reference.getInstance(Transformation.class, new ObjectId(
//					id)));
//			if (transformation != null) {
//				String s = _descriptionDao.get(transformation.getSubject()).getName();
//				String p = _descriptionDao.get(transformation.getPredicate()).getName();
//				String o = _descriptionDao.get(transformation.getObject()).getName();
//
//				willJump = true;
//				Navigation.toTransformation(s, p, o).asResponse();
//			}
//		}

//		if (!willJump) {
//			String subject = pageParameters.get(P_SUB).toOptionalString();
//			String predicate = pageParameters.get(P_PRED).toOptionalString();
//			String object = pageParameters.get(P_OBJ).toOptionalString();

			IModel<Transformation> transformation = new LoadableDetachableModel<Transformation>() {
				@Override
				protected Transformation load() {
					// TODO Auto-generated method stub
					return _transformationDao.get(Reference.getInstance(Transformation.class, new ObjectId(id)));
				}
			};

			add(new TransformationPanel("description", transformation));


	}

//	public static Jump<TransformationPage> toTransformation(String subject, String predicate, String object) {
//		PageParameters params = new PageParameters();
//		params.set(P_SUB, subject);
//		params.set(P_PRED, predicate);
//		params.set(P_OBJ, object);
//		return new Jump<TransformationPage>(TransformationPage.class, params);
//	}

	public static Jump<TransformationPage> toTransformation(Transformation transformation) {
		PageParameters params = new PageParameters();
		params.set(P_TRANS, transformation.getId().getId().toString());
		return new Jump<TransformationPage>(TransformationPage.class, params);
	}
}
