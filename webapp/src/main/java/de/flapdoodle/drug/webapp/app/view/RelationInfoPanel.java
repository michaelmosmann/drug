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
package de.flapdoodle.drug.webapp.app.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.common.collect.Lists;

import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonSize;
import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonType;
import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.ReferenceDto;
import de.flapdoodle.drug.persistence.service.TransformationDto;
import de.flapdoodle.drug.webapp.app.models.TransformationProperties;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.mongoom.types.Reference;
import de.flapdoodle.wicket.model.Models;

public class RelationInfoPanel extends Panel {

	public RelationInfoPanel(String id, IModel<TransformationDto> relation) {
		super(id);

		IModel<Map<ReferenceDto<DescriptionDto>, String>> names = TransformationProperties.names(relation);

		IModel<List<JumpInfo<DescriptionsPage>>> links = Models.on(relation, names).apply(
				new Function2<List<JumpInfo<DescriptionsPage>>, TransformationDto, Map<ReferenceDto<DescriptionDto>, String>>() {

					@Override
					public List<JumpInfo<DescriptionsPage>> apply(TransformationDto t,
							Map<ReferenceDto<DescriptionDto>, String> names) {
						ArrayList<JumpInfo<DescriptionsPage>> ret = Lists.newArrayList();
						
						addIfSet(ret, names, "s", t.getSubject(), true);
						addIfSet(ret, names, "p", t.getPredicate(), false);
						addIfSet(ret, names, "o", t.getObject(), true);
						if (t.getContextType()!=null) addIfSet(ret, names, t.getContextType().asString(), t.getContext(), true);
						
						return ret;
					}

					private void addIfSet(ArrayList<JumpInfo<DescriptionsPage>> ret, Map<ReferenceDto<DescriptionDto>, String> names,
							String prefix, ReferenceDto<DescriptionDto> subject, boolean isObject) {
						if (subject!=null) {
							String name = names.get(subject);
							if (name!=null) {
								ret.add(new JumpInfo<DescriptionsPage>(prefix +":"+ name, Navigation.toDescriptions(name, isObject)));
							}
						}
					}

				});
		
		add(new ListView<JumpInfo<DescriptionsPage>>("links",links) {
			@Override
			protected void populateItem(ListItem<JumpInfo<DescriptionsPage>> item) {
				JumpInfo<DescriptionsPage> jumpInfo = item.getModelObject();
				
				BookmarkablePageLink<DescriptionsPage> link = jumpInfo.getJump().asLink("link");
				link.setBody(Model.of(jumpInfo.getName()));
				link.add(new ButtonBehavior(ButtonType.Default, ButtonSize.Mini));
				item.add(link);
			}
		});
		//		Navigation.toDescriptions(relation.getObject()., isObject)
	}

	static class JumpInfo<T extends Page> {

		final String _name;
		final Jump<T> _jump;

		public JumpInfo(String name, Jump<T> jump) {
			_name = name;
			_jump = jump;
		}

		public String getName() {
			return _name;
		}

		public Jump<T> getJump() {
			return _jump;
		}
	}
}
