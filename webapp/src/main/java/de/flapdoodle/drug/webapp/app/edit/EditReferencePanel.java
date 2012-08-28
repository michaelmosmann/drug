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

	public enum RefType {
		Subject, Object, Predicate, Context
	};
	
	@Inject
	DescriptionDao _descriptionDao;
	
	public EditReferencePanel(String id, final IModel<Reference<Description>> descrModel, final RefType type, final String name) {
		super(id);
		
		IModel<List<Reference<Description>>> choices=Models.on(descrModel).apply(new Function1<List<Reference<Description>>, Reference<Description>>() {
			@Override
			public List<Reference<Description>> apply(Reference<Description> value) {
				if (value!=null) {
					return Lists.newArrayList(value);
				}
				
				List<Description> descriptions;
				if (name!=null) {
					descriptions = _descriptionDao.findByName(type!=RefType.Predicate, name);
				} else {
					descriptions = _descriptionDao.findByType(type!=RefType.Predicate);
				}
				return Lists.transform(descriptions, new Function<Description, Reference<Description>>() {
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
		
		DropDownChoice<Reference<Description>> dropDownChoice = new DropDownChoice<Reference<Description>>("choices",descrModel,choices,renderer);
		dropDownChoice.setRequired(type!=RefType.Context);
		dropDownChoice.setNullValid(type==RefType.Context);
		if (name!=null) {
			List<Reference<Description>> choiceList = choices.getObject();
			if (choiceList.size()==1) {
				descrModel.setObject(choiceList.get(0));
			}
		}
		add(dropDownChoice);
		
		add(new Link<List<Reference<Description>>>("new",choices) {
			@Override
			public void onClick() {
				Description entity = new Description();
				entity.setName(name);
				entity.setObject(type!=RefType.Predicate);
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
