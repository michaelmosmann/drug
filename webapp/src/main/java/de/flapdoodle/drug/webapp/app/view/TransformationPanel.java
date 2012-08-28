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
package de.flapdoodle.drug.webapp.app.view;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonType;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;


public class TransformationPanel extends Panel {

	private IModel<Transformation> descriptionModel;

	public TransformationPanel(String id, IModel<Transformation> model) {
		super(id);
		
		descriptionModel=model;
		
		add(new Label("title",new PropertyModel<String>(model,"title")));
		add(new MarkupPanel("text",new PropertyModel<String>(model,"text")));
		
		add(Navigation.editTransformation(model.getObject()).asLink("edit").add(new ButtonBehavior(ButtonType.Primary)));
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		
		setVisible(descriptionModel.getObject()!=null);
	}
}
