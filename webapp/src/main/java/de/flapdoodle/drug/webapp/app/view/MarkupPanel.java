/**
 * Copyright (C) 2011
 * Michael Mosmann <michael@mosmann.de>
 * Jan Bernitt <unknown@email.de>
 * 
 * with contributions from
 * nobody yet
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.drug.webapp.app.view;

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.flapdoodle.drug.render.ITag;
import de.flapdoodle.drug.render.Tag;
import de.flapdoodle.drug.render.Text;
import de.flapdoodle.drug.webapp.app.models.Markups;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;

public class MarkupPanel extends Panel {

	public MarkupPanel(String id, IModel<String> textModel) {
		super(id);

		IModel<List<ITag>> tagListModel = Markups.asTagsFormMarkdown(textModel);

		add(new ListView<ITag>("list", tagListModel) {

			@Override
			protected void populateItem(ListItem<ITag> item) {
				ITag itag = item.getModelObject();

				boolean gotSomething=false;
				if (itag instanceof Text) {
					item.add(new TextFragment("line", "textFragment", MarkupPanel.this, (Text) itag));
					gotSomething=true;
				}
				if (itag instanceof Tag) {
					item.add(new TagFragment("line", "tagFragment", MarkupPanel.this, (Tag) itag));
					gotSomething=true;
				}
				if (!gotSomething) {
					item.add(new Label("line",""+itag.getClass().getName()));
				}
			}
		});
	}

	static class TextFragment extends Fragment {

		public TextFragment(String id, String markupId, MarkupContainer markupProvider, Text text) {
			super(id, markupId, markupProvider);

			add(new Label("text",text.getText()).setEscapeModelStrings(false));
		}

	}

	static class TagFragment extends Fragment {

		public TagFragment(String id, String markupId, MarkupContainer markupProvider, Tag tag) {
			super(id, markupId, markupProvider);

			String name = tag.getName();

			//		Label text = new Label("text",tag.getText());
			//		text.setEscapeModelStrings(false);
			//		item.add(text);

			BookmarkablePageLink<TransformationsPage> transformationLink = Navigation.toTransformations(tag.getReference()).asLink(
					"transformation");
			transformationLink.add(new Label("text", tag.getText()));
			add(transformationLink);

			BookmarkablePageLink<DescriptionsPage> descriptionLink = Navigation.toDescriptions(name, tag.isObject()).asLink(
					"descriptions");
			descriptionLink.add(new Label("text", tag.getText()));
			add(descriptionLink);

			BookmarkablePageLink<DescriptionsPage> descriptionsShortLink = Navigation.toDescriptions(tag.getRelName(),
					tag.isObject()).asLink("descriptionsShort");
			add(descriptionsShortLink);

			//		text.setVisible(tag.isText());

			transformationLink.setVisible(tag.isRelation());
			descriptionsShortLink.setVisible(tag.isRelation());

			descriptionLink.setVisible(!tag.isRelation());

			if ("".equals(tag.getText())) {
				transformationLink.setVisible(false);
				descriptionsShortLink.setVisible(false);
				descriptionLink.setVisible(false);
			}

		}

	}

}
