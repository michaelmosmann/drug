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

import com.google.common.collect.Lists;
import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.TransformationDto;
import de.flapdoodle.drug.render.TagReference;
import de.flapdoodle.drug.webapp.app.models.Descriptions;
import de.flapdoodle.drug.webapp.app.models.ListModels;
import de.flapdoodle.drug.webapp.app.models.Transformations;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.pages.AbstractBasePage;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class DescriptionPage extends AbstractBasePage {

	static final String P_BEGRIFF="Begriff";

	
	public DescriptionPage(PageParameters pageParameters) {
		
		String begriff = pageParameters.get(P_BEGRIFF).toOptionalString();
		IModel<List<DescriptionDto>> descriptionsModel=Descriptions.get(begriff);
		
		IModel<DescriptionDto> descriptionModel = ListModels.ifOnlyOne(descriptionsModel);
		add(new DescriptionPanel("description", descriptionModel));
		
		if (descriptionModel.getObject()==null) {
			Navigation.editDescription(begriff,true).asResponse();
		}
		
		IModel<List<TransformationDto>> transformations = Models.on(descriptionModel).apply(new Function1<List<TransformationDto>, DescriptionDto>() {
			@Override
			public List<TransformationDto> apply(DescriptionDto descr) {
				Transformations t=new Transformations();
				if (descr.isObject()) {
					ArrayList<TransformationDto> ret = Lists.newArrayList();
					List<TransformationDto> subjects = t.apply(new TagReference(descr.getName(), null, null,null,null));
					List<TransformationDto> objects = t.apply(new TagReference(null, null, descr.getName(),null,null));
					List<TransformationDto> contexts = t.apply(new TagReference(null, null ,null,null,descr.getName()));
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

	public static Jump<DescriptionPage> toDescription(DescriptionDto descriptionDto) {
		return toDescription(descriptionDto.getName());
	}
	
	public static Jump<DescriptionPage> toDescription(String name) {
		PageParameters params = new PageParameters();
		params.set(P_BEGRIFF, name);
		return new Jump<DescriptionPage>(DescriptionPage.class,params);
	}
}
