package de.flapdoodle.drug.webapp.app.view;

import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonCssClassAppender;
import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonType;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;


public class DescriptionPanel extends Panel {

	private IModel<Description> descriptionModel;

	public DescriptionPanel(String id, IModel<Description> model) {
		super(id);
		
		descriptionModel=model;
		
		add(new Label("name",new PropertyModel<String>(model,"name")));
		add(new NamesPanel("other",new PropertyModel<Set<String>>(model,"otherNames")));
		add(new MarkupPanel("text",new PropertyModel<String>(model,"text")));
		
		add(Navigation.editDescription(model.getObject()).asLink("edit").add(new ButtonBehavior(ButtonType.Primary)));
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		
		setVisible(descriptionModel.getObject()!=null);
	}
}
