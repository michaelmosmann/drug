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

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.dao.SearchDao;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.mongoom.types.Reference;

public class EditDescriptionPage extends WebPage {

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
