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
import de.flapdoodle.drug.webapp.app.navigation.Navigation;


public class DescriptionsPanel extends Panel {

	public DescriptionsPanel(String id, IModel<List<Description>> descriptionsModel) {
		super(id);
		
		add(new ListView<Description>("list",descriptionsModel) {
			@Override
			protected void populateItem(ListItem<Description> item) {
				BookmarkablePageLink<DescriptionPage> link = Navigation.toDescription(item.getModelObject()).asLink("link");
				link.add(new Label("name",new PropertyModel<String>(item.getModel(), "name")));
				item.add(link);
			}
		});
	}

}
