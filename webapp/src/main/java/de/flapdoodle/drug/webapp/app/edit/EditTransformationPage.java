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
package de.flapdoodle.drug.webapp.app.edit;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
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

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.dao.SearchDao;
import de.flapdoodle.drug.persistence.dao.TransformationDao;
import de.flapdoodle.drug.render.TagReference;
import de.flapdoodle.drug.webapp.app.edit.EditReferencePanel.RefType;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.pages.AbstractBasePage;
import de.flapdoodle.mongoom.types.Reference;

public class EditTransformationPage extends AbstractBasePage {

	static final String P_SUB = "Subjekt";
	static final String P_PRED = "Predikat";
	static final String P_OBJ = "Objekt";

	static final String P_REF = "Id";
	
	@Inject
	DescriptionDao _descriptionDao;

	@Inject
	TransformationDao _transformationDao;
	
	@Inject
	SearchDao _searchDao;

	public EditTransformationPage(PageParameters pageParameters) {
		
		TagReference reference=Navigation.fromPageParameters(pageParameters);
		
		String id=pageParameters.get(P_REF).toOptionalString();

		Transformation t = new Transformation();
		if (id!=null) {
			Transformation byId=_transformationDao.get(Reference.getInstance(Transformation.class, new ObjectId(id)));
			if (byId!=null) {
				t=byId;
			}
		} else {
			List<Transformation> list = _searchDao.find(reference.getSubject(),reference.getPredicate(),reference.getObject(),reference.getContextType(),reference.getContext());
			if ((list!=null) && (list.size()==1)) {
				t=list.get(0);
			}
		}
		t.setContextType(reference.getContextType());
		
		IModel<Transformation> model = Model.of(t);

		add(new FeedbackPanel("feedback"));
		
		final DropDownChoice<ContextType> contextType = new DropDownChoice<ContextType>("contextType",Lists.newArrayList(ContextType.values()));
		
		Form<Transformation> form = new Form<Transformation>("form", new CompoundPropertyModel<Transformation>(model)) {

			@Override
			protected void onValidateModelObjects() {
				super.onValidateModelObjects();
				Transformation transformation = getModelObject();
				if (transformation.getContext()!=null) {
					if (transformation.getContextType()==null) {
						contextType.error("Bitte geben Sie einen Ortsbeschreibung für den Kontext an");
					}
				}
			}
			
			@Override
			protected void onSubmit() {
				super.onSubmit();
				
				Transformation transformation = getModelObject();
				if (transformation.getId()==null) {
					_transformationDao.save(transformation);
				} else {
					_transformationDao.update(transformation);
				}
				setModelObject(transformation);
				
				Navigation.toTransformation(transformation).asResponse();
			}
		};
		
		TextField<String> title = new TextField<String>("title");
		title.add(StringValidator.minimumLength(3));
		title.setRequired(true);
		
		TextArea<String> text = new TextArea<String>("text");
		text.add(StringValidator.minimumLength(3));
		text.setRequired(true);
		
		form.add(title);
		form.add(text);
		
		IModel<Reference<Description>> subjectModel = new PropertyModel<Reference<Description>>(model, "subject");
		form.add(new EditReferencePanel("subject", subjectModel, RefType.Subject, reference.getSubject()));
		IModel<Reference<Description>> predicateModel = new PropertyModel<Reference<Description>>(model, "predicate");
		form.add(new EditReferencePanel("predicate", predicateModel, RefType.Predicate, reference.getPredicate()));
		IModel<Reference<Description>> objectModel = new PropertyModel<Reference<Description>>(model, "object");
		form.add(new EditReferencePanel("object", objectModel, RefType.Object, reference.getObject()));
		IModel<Reference<Description>> contextModel = new PropertyModel<Reference<Description>>(model, "context");
		form.add(new EditReferencePanel("context", contextModel, RefType.Context, reference.getContext()));
		
		form.add(contextType);

		
		add(form);
	}

	public static Jump<EditTransformationPage> editTransformation(TagReference reference) {
		PageParameters params = Navigation.asPageParameters(reference);
		return new Jump<EditTransformationPage>(EditTransformationPage.class, params);
	}

	public static Jump<EditTransformationPage> editTransformation(Transformation transformation) {
		PageParameters params = new PageParameters();
		if (transformation!=null) {
		params.add(P_REF, transformation.getId().getId().toString());
		}
		return new Jump<EditTransformationPage>(EditTransformationPage.class, params);
	}
}
