/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <${lic.email2}>
 *
 * with contributions from
 * 	${lic.developers}
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
