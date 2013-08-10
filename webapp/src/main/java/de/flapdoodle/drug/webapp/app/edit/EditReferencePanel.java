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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.mongo.service.DescriptionService;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.IDescriptionService;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;
import de.flapdoodle.wicket.model.Models;

public class EditReferencePanel extends Panel {

	public enum RefType {
		Subject,
		Object,
		Predicate,
		Context
	};

	@Inject
	IDescriptionService _descriptionDao;

	public EditReferencePanel(String id, final IModel<ReferenceDto<DescriptionDto>> descrModel, final RefType type,
			final String name) {
		super(id);

		IModel<List<ReferenceDto<DescriptionDto>>> choices = Models.on(descrModel).apply(
				new Function1<List<ReferenceDto<DescriptionDto>>, ReferenceDto<DescriptionDto>>() {

					@Override
					public List<ReferenceDto<DescriptionDto>> apply(ReferenceDto<DescriptionDto> value) {
						if (value != null) {
							return Lists.newArrayList(value);
						}

						List<DescriptionDto> descriptions;
						if (name != null) {
							descriptions = _descriptionDao.findByName(type != RefType.Predicate, name);
						} else {
							descriptions = _descriptionDao.findByType(type != RefType.Predicate);
						}
						return Lists.transform(descriptions, new Function<DescriptionDto, ReferenceDto<DescriptionDto>>() {

							@Override
							public ReferenceDto<DescriptionDto> apply(DescriptionDto from) {
								return from.getId();
							}
						});
					}
				});

		//		IModel<List<ReferenceDto<DescriptionDto>>> choices=new LoadableDetachableModel<List<ReferenceDto<DescriptionDto>>>() {
		//			@Override
		//			protected List<ReferenceDto<DescriptionDto>> load() {
		//				return Lists.transform(_descriptionDao.findByName(isObject, name), new Function<Description, ReferenceDto<DescriptionDto>>() {
		//					@Override
		//					public ReferenceDto<DescriptionDto> apply(Description from) {
		//						return from.getId();
		//					}
		//				});
		//			}
		//		};

		IChoiceRenderer<ReferenceDto<DescriptionDto>> renderer = new IChoiceRenderer<ReferenceDto<DescriptionDto>>() {

			@Override
			public Object getDisplayValue(ReferenceDto<DescriptionDto> object) {
				return _descriptionDao.get(object).getName();
			}

			@Override
			public String getIdValue(ReferenceDto<DescriptionDto> object, int index) {
				return object.toString();
			}
		};

		DropDownChoice<ReferenceDto<DescriptionDto>> dropDownChoice = new DropDownChoice<ReferenceDto<DescriptionDto>>("choices",
				descrModel, choices, renderer);
		//		dropDownChoice.setRequired(type!=RefType.Context);
		dropDownChoice.setNullValid(true);
		if (name != null) {
			List<ReferenceDto<DescriptionDto>> choiceList = choices.getObject();
			if (choiceList.size() == 1) {
				descrModel.setObject(choiceList.get(0));
			}
		}
		add(dropDownChoice);

		Link<List<ReferenceDto<DescriptionDto>>> link = new Link<List<ReferenceDto<DescriptionDto>>>("new", choices) {

			@Override
			public void onClick() {
				DescriptionDto entity = new DescriptionDto();
				entity.setName(name);
				entity.setObject(type != RefType.Predicate);
				_descriptionDao.save(entity);

				descrModel.setObject(entity.getId());
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();

				setVisible(getModelObject() == null || getModelObject().isEmpty());
			}
		};
		link.add(new Label("begriff", name));
		add(link);
	}

}
