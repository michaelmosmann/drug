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
import org.bson.types.ObjectId;

import com.google.inject.Inject;

import de.agilecoders.wicket.markup.html.bootstrap.form.FormBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.form.FormBehavior.Type;
import de.agilecoders.wicket.markup.html.bootstrap.form.InputBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.form.InputBorder;
import de.agilecoders.wicket.markup.html.bootstrap.layout.SpanType;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.dao.SearchDao;
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
	DescriptionDao _descriptionDao;

	@Inject
	SearchDao _searchDao;

	public EditDescriptionPage(PageParameters pageParameters) {

		String name = pageParameters.get(P_NAME).toOptionalString();
		boolean isObject = pageParameters.get(P_OBJECT).toBoolean(true);
		String id = pageParameters.get(P_REF).toOptionalString();

		Description t = new Description();
		t.setName(name);
		t.setObject(isObject);
		
		if (id != null) {
			Description byId = _descriptionDao.get(Reference.getInstance(Description.class, new ObjectId(id)));
			if (byId != null) {
				t = byId;
			}
		} else {
			Description byName = _descriptionDao.getByName(name);
			if (byName != null) {
				t = byName;
			}
		}

		IModel<Description> model = Model.of(t);

		add(new FeedbackPanel("feedback"));

		Form<Description> form = new Form<Description>("form", new CompoundPropertyModel<Description>(model)) {

			@Override
			protected void onSubmit() {
				super.onSubmit();

				Description transformation = getModelObject();
				if (transformation.getId() == null) {
					_descriptionDao.save(transformation);
				} else {
					_descriptionDao.update(transformation);
				}
				setModelObject(transformation);

				Navigation.toDescription(transformation).asResponse();
			}
		};
		form.add(new FormBehavior(Type.Default));
				
		form.add(new TextField<String>("name"));
		form.add(new TextArea<String>("text"));
		IModel<Set<String>> othersModel = new PropertyModel<Set<String>>(model, "otherNames");
		form.add(new EditWordlistPanel("other",othersModel));
		form.add(new CheckBox("object"));

		add(form);
	}

	public static Jump<EditDescriptionPage> editDescription(String name, boolean isObject) {
		PageParameters params = new PageParameters();
		params.add(P_NAME, name);
		params.add(P_OBJECT, isObject);
		return new Jump<EditDescriptionPage>(EditDescriptionPage.class, params);
	}

	public static Jump<EditDescriptionPage> editDescription(Description descr) {
		PageParameters params = new PageParameters();
		if (descr != null)
			params.add(P_REF, descr.getId().getId().toString());
		return new Jump<EditDescriptionPage>(EditDescriptionPage.class, params);
	}
}
