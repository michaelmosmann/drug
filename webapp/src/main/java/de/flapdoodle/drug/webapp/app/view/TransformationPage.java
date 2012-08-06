package de.flapdoodle.drug.webapp.app.view;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
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
import de.flapdoodle.mongoom.types.Reference;

public class TransformationPage extends WebPage {

	static final String P_SUB = "Subjekt";
	static final String P_PRED = "Predikat";
	static final String P_OBJ = "Objekt";

	static final String P_TRANS = "Id";

	@Inject
	TransformationDao _transformationDao;

	@Inject
	DescriptionDao _descriptionDao;

	@Inject
	SearchDao _searchDao;

	public TransformationPage(PageParameters pageParameters) {
		String id = pageParameters.get(P_TRANS).toOptionalString();
		boolean willJump = false;

		if (id != null) {
			Transformation transformation = _transformationDao.get(Reference.getInstance(Transformation.class, new ObjectId(
					id)));
			if (transformation != null) {
				String s = _descriptionDao.get(transformation.getSubject()).getName();
				String p = _descriptionDao.get(transformation.getPredicate()).getName();
				String o = _descriptionDao.get(transformation.getObject()).getName();

				willJump = true;
				Navigation.toTransformation(s, p, o).asResponse();
			}
		}

		if (!willJump) {
			String subject = pageParameters.get(P_SUB).toOptionalString();
			String predicate = pageParameters.get(P_PRED).toOptionalString();
			String object = pageParameters.get(P_OBJ).toOptionalString();

			IModel<List<Transformation>> transformations = Transformations.search(subject, predicate, object);

			List<Transformation> list = transformations.getObject();

			if ((list == null) || (list.isEmpty())) {
				Navigation.editTransformation(subject, predicate, object).asResponse();
			}
			
			add(new TransformationsPanel("descriptions", ListModels.ifMoreThanOne(transformations)));
			
			IModel<Transformation> descriptionModel = ListModels.ifOnlyOne(transformations);
			add(new TransformationPanel("description", descriptionModel));

			
		}

	}

	public static Jump<TransformationPage> toTransformation(String subject, String predicate, String object) {
		PageParameters params = new PageParameters();
		params.set(P_SUB, subject);
		params.set(P_PRED, predicate);
		params.set(P_OBJ, object);
		return new Jump<TransformationPage>(TransformationPage.class, params);
	}

	public static Jump<TransformationPage> toTransformation(Transformation transformation) {
		PageParameters params = new PageParameters();
		params.set(P_TRANS, transformation.getId().getId().toString());
		return new Jump<TransformationPage>(TransformationPage.class, params);
	}
}
