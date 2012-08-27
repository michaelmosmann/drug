package de.flapdoodle.drug.webapp.app.edit;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
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

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.dao.SearchDao;
import de.flapdoodle.drug.persistence.dao.TransformationDao;
import de.flapdoodle.drug.render.TagReference;
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
		
		IModel<Transformation> model = Model.of(t);

		add(new FeedbackPanel("feedback"));
		
		Form<Transformation> form = new Form<Transformation>("form", new CompoundPropertyModel<Transformation>(model)) {

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
		
		form.add(new TextField<String>("title"));
		form.add(new TextArea<String>("text"));
		
		IModel<Reference<Description>> subjectModel = new PropertyModel<Reference<Description>>(model, "subject");
		form.add(new EditReferencePanel("subject", subjectModel, true, reference.getSubject()));
		IModel<Reference<Description>> predicateModel = new PropertyModel<Reference<Description>>(model, "predicate");
		form.add(new EditReferencePanel("predicate", predicateModel, false, reference.getPredicate()));
		IModel<Reference<Description>> objectModel = new PropertyModel<Reference<Description>>(model, "object");
		form.add(new EditReferencePanel("object", objectModel, true, reference.getObject()));
		IModel<Reference<Description>> contextModel = new PropertyModel<Reference<Description>>(model, "context");
		form.add(new EditReferencePanel("context", contextModel, true, reference.getContext()));
		
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
