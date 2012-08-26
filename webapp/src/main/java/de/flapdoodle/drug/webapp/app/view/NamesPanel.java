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
				item.add(new Label("label",item.getModel()).add(new ButtonBehavior(ButtonType.Menu)));
				
			}
		});
	}

}
