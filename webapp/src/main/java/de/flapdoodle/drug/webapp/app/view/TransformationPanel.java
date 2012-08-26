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
