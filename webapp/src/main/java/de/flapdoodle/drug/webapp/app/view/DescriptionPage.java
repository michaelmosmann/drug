package de.flapdoodle.drug.webapp.app.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.common.collect.Lists;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.render.TagReference;
import de.flapdoodle.drug.webapp.app.models.Descriptions;
import de.flapdoodle.drug.webapp.app.models.ListModels;
import de.flapdoodle.drug.webapp.app.models.Transformations;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.pages.AbstractBasePage;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;


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
		
		IModel<List<Transformation>> transformations = Models.on(descriptionModel).apply(new Function1<List<Transformation>, Description>() {
			@Override
			public List<Transformation> apply(Description descr) {
				Transformations t=new Transformations();
				if (descr.isObject()) {
					ArrayList<Transformation> ret = Lists.newArrayList();
					List<Transformation> subjects = t.apply(new TagReference(descr.getName(), null, null,null,null));
					List<Transformation> objects = t.apply(new TagReference(null, null, descr.getName(),null,null));
					if (subjects!=null) ret.addAll(subjects);
					if (objects!=null) ret.addAll(objects);
					return ret;
				} else {
					return t.apply(new TagReference(null, descr.getName(), null,null,null));
				}
			}
		});
		
		add(new TransformationsPanel("transformations", transformations));
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
