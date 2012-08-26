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
import de.flapdoodle.drug.webapp.app.pages.AbstractBasePage;
import de.flapdoodle.mongoom.types.Reference;

public class TransformationsPage extends AbstractBasePage {

	static final String P_SUB = "Subjekt";
	static final String P_PRED = "Predikat";
	static final String P_OBJ = "Objekt";

	@Inject
	TransformationDao _transformationDao;

	@Inject
	DescriptionDao _descriptionDao;

	@Inject
	SearchDao _searchDao;

	public TransformationsPage(PageParameters pageParameters) {

		String subject = pageParameters.get(P_SUB).toOptionalString();
		String predicate = pageParameters.get(P_PRED).toOptionalString();
		String object = pageParameters.get(P_OBJ).toOptionalString();

		IModel<List<Transformation>> transformations = Transformations.search(subject, predicate, object);

		List<Transformation> list = transformations.getObject();

		if ((list!=null) && (!list.isEmpty())) {
			if ((list.size()==1) && (subject!=null) && (predicate!=null) && (object!=null)) {
				Navigation.toTransformation(list.get(0)).asResponse();
			}
		} else {
			Navigation.editTransformation(subject, predicate, object).asResponse();
		}

		add(new TransformationsPanel("descriptions", transformations));

		//			IModel<Transformation> descriptionModel = ListModels.ifOnlyOne(transformations);
		//			add(new TransformationPanel("description", descriptionModel));

	}

	public static Jump<TransformationsPage> toTransformations(String subject, String predicate, String object) {
		PageParameters params = new PageParameters();
		if (subject != null)
			params.set(P_SUB, subject);
		if (predicate != null)
			params.set(P_PRED, predicate);
		if (object != null)
			params.set(P_OBJ, object);
		return new Jump<TransformationsPage>(TransformationsPage.class, params);
	}

}