package de.flapdoodle.drug.webapp.app.view;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.webapp.app.models.Descriptions;
import de.flapdoodle.drug.webapp.app.models.ListModels;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.pages.AbstractBasePage;


public class DescriptionPage extends AbstractBasePage {

	static final String P_BEGRIFF="Begriff";

	
	public DescriptionPage(PageParameters pageParameters) {
		
		String begriff = pageParameters.get(P_BEGRIFF).toOptionalString();
		IModel<List<Description>> descriptionsModel=Descriptions.get(begriff);
		
		IModel<Description> descriptionModel = ListModels.ifOnlyOne(descriptionsModel);
		add(new DescriptionPanel("description", descriptionModel));
		
		if (descriptionModel.getObject()==null) {
			Navigation.editDescription(begriff,true).asResponse();
		}
	}
	
	public static Jump<DescriptionPage> toDescription(Description description) {
		return toDescription(description.getName());
	}

	public static Jump<DescriptionPage> toDescription(String name) {
		PageParameters params = new PageParameters();
		params.set(P_BEGRIFF, name);
		return new Jump<DescriptionPage>(DescriptionPage.class,params);
	}
}
