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


public class DescriptionPage extends WebPage {

	static final String P_BEGRIFF="Begriff";
	static final String P_SUCHE="Suche";

	
	public DescriptionPage(PageParameters pageParameters) {
		
		String suche = pageParameters.get(P_SUCHE).toOptionalString();
		IModel<List<Description>> descriptionsModel = Descriptions.withQuery(suche);
		String begriff = pageParameters.get(P_BEGRIFF).toOptionalString();
		if (begriff!=null) descriptionsModel=Descriptions.get(begriff);
		add(new DescriptionsPanel("descriptions", ListModels.ifMoreThanOne(descriptionsModel)));
		
		IModel<Description> descriptionModel = ListModels.ifOnlyOne(descriptionsModel);
		add(new DescriptionPanel("description", descriptionModel));
		
		if ((descriptionsModel.getObject()==null) || (descriptionsModel.getObject().isEmpty())) {
			Navigation.editDescription(suche!=null? suche : begriff,true).asResponse();
		}
	}
	
	public static Jump<DescriptionPage> toDescriptions(String name) {
		PageParameters params = new PageParameters();
		params.set(P_SUCHE, name);
		return new Jump<DescriptionPage>(DescriptionPage.class,params);
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
