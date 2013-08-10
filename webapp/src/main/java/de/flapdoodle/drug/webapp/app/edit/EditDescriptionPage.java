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
package de.flapdoodle.drug.webapp.app.edit;

import java.util.Set;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.StringValidator;
import org.bson.types.ObjectId;

import com.google.inject.Inject;

import de.agilecoders.wicket.markup.html.bootstrap.form.FormBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.form.FormBehavior.Type;
import de.agilecoders.wicket.markup.html.bootstrap.form.InputBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.form.InputBorder;
import de.agilecoders.wicket.markup.html.bootstrap.layout.SpanType;
import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.mongo.dao.SearchDao;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.IDescriptionService;
import de.flapdoodle.drug.persistence.service.SearchService;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.pages.AbstractBasePage;
import de.flapdoodle.drug.webapp.app.pages.AbstractProtectedPage;
import de.flapdoodle.mongoom.types.Reference;

public class EditDescriptionPage extends AbstractProtectedPage {

	static final String P_NAME = "Name";
	static final String P_OBJECT = "Object";
	static final String P_REF = "Id";

	@Inject
	IDescriptionService _descriptionService;

	public EditDescriptionPage(PageParameters pageParameters) {

		String name = pageParameters.get(P_NAME).toOptionalString();
		boolean isObject = pageParameters.get(P_OBJECT).toBoolean(true);
		String id = pageParameters.get(P_REF).toOptionalString();

		DescriptionDto t = new DescriptionDto();
		t.setName(name);
		t.setObject(isObject);
		
		if (id != null) {
			DescriptionDto byId = _descriptionService.getByStringId(id);
			if (byId != null) {
				t = byId;
			}
		} else {
			DescriptionDto byName = _descriptionService.getByName(name);
			if (byName != null) {
				t = byName;
			}
		}

		IModel<DescriptionDto> model = Model.of(t);

		add(new FeedbackPanel("feedback"));

		Form<DescriptionDto> form = new Form<DescriptionDto>("form", new CompoundPropertyModel<DescriptionDto>(model)) {

			@Override
			protected void onSubmit() {
				super.onSubmit();

				DescriptionDto transformation = getModelObject();
				if (transformation.getId() == null) {
					transformation=_descriptionService.save(transformation);
				} else {
					transformation=_descriptionService.update(transformation);
				}
				setModelObject(transformation);

				Navigation.toDescription(transformation).asResponse();
			}
		};
		form.add(new FormBehavior(Type.Default));
				
		TextField<String> title = new TextField<String>("name");
		title.add(StringValidator.minimumLength(3));
		title.setRequired(true);
		form.add(title);
		TextArea<String> text = new TextArea<String>("text");
		text.add(StringValidator.minimumLength(3));
		text.setRequired(true);
		form.add(text);
		IModel<Set<String>> othersModel = new PropertyModel<Set<String>>(model, "otherNames");
		form.add(new EditWordlistPanel("other",othersModel));
		form.add(new CheckBox("object"));
		form.add(new MarkdownHelpPanel("help"));
		add(form);
	}

	public static Jump<EditDescriptionPage> editDescription(String name, boolean isObject) {
		PageParameters params = new PageParameters();
		params.add(P_NAME, name);
		params.add(P_OBJECT, isObject);
		return new Jump<EditDescriptionPage>(EditDescriptionPage.class, params,true);
	}

	public static Jump<EditDescriptionPage> editDescription(Description descr) {
		PageParameters params = new PageParameters();
		if (descr != null)
			params.add(P_REF, descr.getId().getId().toString());
		return new Jump<EditDescriptionPage>(EditDescriptionPage.class, params,true);
	}

	public static Jump<EditDescriptionPage> editDescription(DescriptionDto descr) {
		PageParameters params = new PageParameters();
		if (descr != null)
			params.add(P_REF, descr.getId().getId().toString());
		return new Jump<EditDescriptionPage>(EditDescriptionPage.class, params,true);
	}
}
