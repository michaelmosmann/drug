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

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.service.TransformationDto;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;


public class TransformationsPanel extends Panel {

	public TransformationsPanel(String id, IModel<List<TransformationDto>> descriptionsModel) {
		super(id);
		
		add(new ListView<TransformationDto>("list",descriptionsModel) {
			@Override
			protected void populateItem(ListItem<TransformationDto> item) {
				BookmarkablePageLink<TransformationPage> link = Navigation.toTransformation(item.getModelObject()).asLink("link");
				link.add(new Label("title",new PropertyModel<String>(item.getModel(), "title")));
				item.add(link);
			}
		});
	}

}
