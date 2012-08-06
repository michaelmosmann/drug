package de.flapdoodle.drug.webapp.app.view;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;


public class TransformationsPanel extends Panel {

	public TransformationsPanel(String id, IModel<List<Transformation>> descriptionsModel) {
		super(id);
		
		add(new ListView<Transformation>("list",descriptionsModel) {
			@Override
			protected void populateItem(ListItem<Transformation> item) {
				BookmarkablePageLink<TransformationPage> link = Navigation.toTransformation(item.getModelObject()).asLink("link");
				link.add(new Label("title",new PropertyModel<String>(item.getModel(), "title")));
				item.add(link);
			}
		});
	}

}
