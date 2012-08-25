package de.flapdoodle.drug.webapp.app.view;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.flapdoodle.drug.render.TagListVisitior.Tag;
import de.flapdoodle.drug.webapp.app.models.Markups;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;


public class MarkupPanel extends Panel {

	public MarkupPanel(String id, IModel<String> textModel) {
		super(id);
		
		IModel<List<Tag>> tagListModel = Markups.asTagsFormMarkdown(textModel);
		
		add(new ListView<Tag>("list",tagListModel) {
			@Override
			protected void populateItem(ListItem<Tag> item) {
				Tag tag = item.getModelObject();
				String name = tag.getName();
				
				String subject = tag.getSubject();
				String predicate = tag.getPredicate();
				String object = tag.getObject();
				
				Label text = new Label("text",tag.getText());
				text.setEscapeModelStrings(false);
				item.add(text);
				
				BookmarkablePageLink<TransformationPage> transformationLink = Navigation.toTransformation(subject, predicate, object).asLink("transformation");
				transformationLink.add(new Label("text",tag.getText()));
				item.add(transformationLink);
				
				BookmarkablePageLink<DescriptionPage> descriptionLink = Navigation.toDescriptions(name).asLink("descriptions");
				descriptionLink.add(new Label("text",tag.getText()));
				item.add(descriptionLink);

				BookmarkablePageLink<DescriptionPage> descriptionsShortLink = Navigation.toDescriptions(tag.getRelName()).asLink("descriptionsShort");
				item.add(descriptionsShortLink);
				
				text.setVisible(tag.isText());
				
				transformationLink.setVisible(tag.isRelation());
				descriptionsShortLink.setVisible(tag.isRelation());
				
				descriptionLink.setVisible(!tag.isRelation() && !tag.isText());
				
				if ("".equals(tag.getText())) {
					text.setVisible(false);
					transformationLink.setVisible(false);
					descriptionsShortLink.setVisible(false);
					descriptionLink.setVisible(false);
				}
			}
		});
	}

	
}
