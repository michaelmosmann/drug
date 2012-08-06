package de.flapdoodle.drug.webapp.app.edit;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;
import de.flapdoodle.wicket.model.Models;


public class EditReferencePanel extends Panel {

	@Inject
	DescriptionDao _descriptionDao;
	
	public EditReferencePanel(String id, final IModel<Reference<Description>> descrModel, final boolean isObject, final String name) {
		super(id);
		
		IModel<List<Reference<Description>>> choices=Models.on(descrModel).apply(new Function1<List<Reference<Description>>, Reference<Description>>() {
			@Override
			public List<Reference<Description>> apply(Reference<Description> value) {
				if (value!=null) {
					return Lists.newArrayList(value);
				}
				
				return Lists.transform(_descriptionDao.findByName(isObject, name), new Function<Description, Reference<Description>>() {
					@Override
					public Reference<Description> apply(Description from) {
						return from.getId();
					}
				});
			}
		});
		
//		IModel<List<Reference<Description>>> choices=new LoadableDetachableModel<List<Reference<Description>>>() {
//			@Override
//			protected List<Reference<Description>> load() {
//				return Lists.transform(_descriptionDao.findByName(isObject, name), new Function<Description, Reference<Description>>() {
//					@Override
//					public Reference<Description> apply(Description from) {
//						return from.getId();
//					}
//				});
//			}
//		};
						
		IChoiceRenderer<Reference<Description>> renderer=new IChoiceRenderer<Reference<Description>>() {
			@Override
			public Object getDisplayValue(Reference<Description> object) {
				return _descriptionDao.get(object).getName();
			}
			
			@Override
			public String getIdValue(Reference<Description> object, int index) {
				return object.toString();
			}
		};
		
		add(new DropDownChoice<Reference<Description>>("choices",descrModel,choices,renderer).setRequired(true));
		
		add(new Link<List<Reference<Description>>>("new",choices) {
			@Override
			public void onClick() {
				Description entity = new Description();
				entity.setName(name);
				entity.setObject(isObject);
				_descriptionDao.save(entity);

				descrModel.setObject(entity.getId());
			}
			
			@Override
			protected void onConfigure() {
				super.onConfigure();
				
				setVisible(getModelObject()==null || getModelObject().isEmpty());
			}
		});
	}

}
