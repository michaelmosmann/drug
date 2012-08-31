/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import de.flapdoodle.drug.webapp.app.pages.AbstractProtectedPage;
import de.flapdoodle.drug.webapp.security.NotPublic;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;


public class DescriptionPage extends AbstractProtectedPage {

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
					List<Transformation> contexts = t.apply(new TagReference(null, null ,null,null,descr.getName()));
					if (subjects!=null) ret.addAll(subjects);
					if (objects!=null) ret.addAll(objects);
					if (contexts!=null) ret.addAll(contexts);
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
