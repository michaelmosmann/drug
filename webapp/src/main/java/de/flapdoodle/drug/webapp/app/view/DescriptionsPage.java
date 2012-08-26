package de.flapdoodle.drug.webapp.app.view;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.common.collect.Lists;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.webapp.app.models.Descriptions;
import de.flapdoodle.drug.webapp.app.models.ListModels;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.pages.AbstractBasePage;

public class DescriptionsPage extends AbstractBasePage {

	static final String P_BEGRIFF = "Begriff";
	static final String P_OBJECT = "Object";

	public DescriptionsPage(PageParameters pageParameters) {
		String begriff = pageParameters.get(P_BEGRIFF).toOptionalString();
		boolean isObject = pageParameters.get(P_OBJECT).toBoolean(true);
		IModel<List<Description>> descriptionsModel = Descriptions.withQuery(begriff);

		add(new DescriptionsPanel("descriptions", descriptionsModel));

		List<Description> result = descriptionsModel.getObject();
		if ((result != null) && (!result.isEmpty())) {
			if (result.size() == 1) {
				Navigation.toDescription(result.get(0)).asResponse();
			}
		} else {
			Navigation.editDescription(begriff, isObject).asResponse();
		}
	}

	public static Jump<DescriptionsPage> toDescriptions(String name,boolean isObject) {
		PageParameters params = new PageParameters();
		params.set(P_BEGRIFF, name);
		params.set(P_OBJECT, isObject);
		return new Jump<DescriptionsPage>(DescriptionsPage.class, params);
	}

}
