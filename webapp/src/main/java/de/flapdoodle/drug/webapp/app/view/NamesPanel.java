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
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.common.collect.Lists;

import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonType;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;


public class NamesPanel extends Panel {

	public NamesPanel(String id, IModel<Set<String>> model) {
		super(id);
		
		IModel<List<String>> asList=Models.on(model).apply(new Function1<List<String>, Set<String>>() {
			@Override
			public List<String> apply(Set<String> value) {
				if (value!=null) return Lists.newArrayList(value);
				return null;
			}
		});
		
		add(new ListView<String>("list",asList) {
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Label("label",item.getModel()));
				
			}
		});
	}

}
