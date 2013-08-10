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

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
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
import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.mongo.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.mongo.dao.SearchDao;
import de.flapdoodle.drug.persistence.mongo.dao.TransformationDao;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.ITransformationService;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.drug.persistence.service.SearchService;
import de.flapdoodle.drug.persistence.service.TransformationDto;
import de.flapdoodle.drug.render.TagReference;
import de.flapdoodle.drug.webapp.app.edit.EditReferencePanel.RefType;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.pages.AbstractBasePage;
import de.flapdoodle.drug.webapp.app.pages.AbstractProtectedPage;
import de.flapdoodle.mongoom.types.Reference;

public class EditTransformationPage extends AbstractProtectedPage {

	static final String P_SUB = "Subjekt";
	static final String P_PRED = "Predikat";
	static final String P_OBJ = "Objekt";

	static final String P_REF = "Id";
	
	@Inject
	ITransformationService _transformationService;
	
	@Inject
	SearchService _searchService;

	public EditTransformationPage(PageParameters pageParameters) {
		
		TagReference reference=Navigation.fromPageParameters(pageParameters);
		
		String id=pageParameters.get(P_REF).toOptionalString();

		TransformationDto t = new TransformationDto();
		t.setContextType(reference.getContextType());
		if (id!=null) {
			TransformationDto byId=_transformationService.getByStringId(id);
			if (byId!=null) {
				t=byId;
			}
		} else {
			List<TransformationDto> list = _searchService.find(reference.getSubject(),reference.getPredicate(),reference.getObject(),reference.getContextType(),reference.getContext());
			if ((list!=null) && (list.size()==1)) {
				t=list.get(0);
			}
		}
		
		IModel<TransformationDto> model = Model.of(t);

		add(new FeedbackPanel("feedback"));
		
//		IChoiceRenderer<? super ContextType> renderer=new IChoiceRenderer<ContextType>() {
//			@Override
//			public Object getDisplayValue(ContextType object) {
//				return object.name();
//			}
//			
//			@Override
//			public String getIdValue(ContextType object, int index) {
//				return ""+index;
//			}
//		};
//		IModel<ContextType> contextTypeModel = new PropertyModel<ContextType>(model, "contextType");
		final DropDownChoice<ContextType> contextType = new DropDownChoice<ContextType>("contextType",Lists.newArrayList(ContextType.values()));
		contextType.setNullValid(true);
		
		Form<TransformationDto> form = new Form<TransformationDto>("form", new CompoundPropertyModel<TransformationDto>(model)) {

			@Override
			protected void onValidateModelObjects() {
				super.onValidateModelObjects();
				TransformationDto transformation = getModelObject();
				if (transformation.getContext()!=null) {
					if (transformation.getContextType()==null) {
						contextType.error("Bitte geben Sie einen Ortsbeschreibung für den Kontext an");
					}
				}
				boolean hasSubject=transformation.getSubject()!=null;
				boolean hasPredicate=transformation.getPredicate()!=null;
				boolean hasObject=transformation.getObject()!=null;
				
				int count=0;
				if (hasSubject) count++;
				if (hasPredicate) count++;
				if (hasObject) count++;
				
				if (count<2) {
					// only one set
					if (count==0) {
						// nothing set
						error("Es müssen mindestens Prädikat und Objekt definiert werden.");
					} else {
						if (!hasObject) {
							error("Objekt nicht definiert");
						}
						if (!hasPredicate) {
							error("Prädikat nicht definiert");
						}
					}
				}
			}
			
			@Override
			protected void onSubmit() {
				super.onSubmit();
				
				TransformationDto transformation = getModelObject();
				if (transformation.getId()==null) {
					_transformationService.save(transformation);
				} else {
					_transformationService.update(transformation);
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
		
		IModel<ReferenceDto<DescriptionDto>> subjectModel = new PropertyModel<ReferenceDto<DescriptionDto>>(model, "subject");
		form.add(new EditReferencePanel("subject", subjectModel, RefType.Subject, reference.getSubject()));
		IModel<ReferenceDto<DescriptionDto>> predicateModel = new PropertyModel<ReferenceDto<DescriptionDto>>(model, "predicate");
		form.add(new EditReferencePanel("predicate", predicateModel, RefType.Predicate, reference.getPredicate()));
		IModel<ReferenceDto<DescriptionDto>> objectModel = new PropertyModel<ReferenceDto<DescriptionDto>>(model, "object");
		form.add(new EditReferencePanel("object", objectModel, RefType.Object, reference.getObject()));
		IModel<ReferenceDto<DescriptionDto>> contextModel = new PropertyModel<ReferenceDto<DescriptionDto>>(model, "context");
		form.add(new EditReferencePanel("context", contextModel, RefType.Context, reference.getContext()));
		
		form.add(contextType);

		form.add(new MarkdownHelpPanel("help"));
		
		add(form);
	}

	public static Jump<EditTransformationPage> editTransformation(TagReference reference) {
		PageParameters params = Navigation.asPageParameters(reference);
		return new Jump<EditTransformationPage>(EditTransformationPage.class, params,true);
	}

	public static Jump<EditTransformationPage> editTransformation(Transformation transformation) {
		PageParameters params = new PageParameters();
		if (transformation!=null) {
		params.add(P_REF, transformation.getId().getId().toString());
		}
		return new Jump<EditTransformationPage>(EditTransformationPage.class, params,true);
	}

	public static Jump<EditTransformationPage> editTransformation(TransformationDto transformation) {
		PageParameters params = new PageParameters();
		if (transformation!=null) {
		params.add(P_REF, transformation.getId().getId().toString());
		}
		return new Jump<EditTransformationPage>(EditTransformationPage.class, params,true);
	}
}
