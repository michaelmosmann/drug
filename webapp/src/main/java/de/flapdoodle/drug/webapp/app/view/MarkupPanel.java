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

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.flapdoodle.drug.render.ITag;
import de.flapdoodle.drug.render.Single;
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

				boolean gotSomething = false;
				if (itag instanceof Text) {
					item.add(new TextFragment("line", "textFragment", MarkupPanel.this, (Text) itag));
					gotSomething = true;
				}
				if (itag instanceof Tag) {
					item.add(new TagFragment("line", "tagFragment", MarkupPanel.this, (Tag) itag));
					gotSomething = true;
				}
				if (itag instanceof Single) {
					item.add(new SingleFragment("line", "singleFragment", MarkupPanel.this, (Single) itag));
					gotSomething = true;
				}
				if (!gotSomething) {
					item.add(new Label("line", "" + itag.getClass().getName()));
				}
			}
		});
	}

	static class TextFragment extends Fragment {

		public TextFragment(String id, String markupId, MarkupContainer markupProvider, Text text) {
			super(id, markupId, markupProvider);

			add(new Label("text", text.getText()).setEscapeModelStrings(false));
		}

	}

	static class SingleFragment extends Fragment {

		public SingleFragment(String id, String markupId, MarkupContainer markupProvider, Single single) {
			super(id, markupId, markupProvider);

			String name = single.getName();

			BookmarkablePageLink<DescriptionsPage> descriptionLink = Navigation.toDescriptions(name, true).asLink(
					"descriptions");
			descriptionLink.add(new Label("text", single.getText()));
			add(descriptionLink);
		}
	}

	static class TagFragment extends Fragment {

		public TagFragment(String id, String markupId, MarkupContainer markupProvider, Tag tag) {
			super(id, markupId, markupProvider);

			String name = tag.getName();

			BookmarkablePageLink<TransformationsPage> transformationLink = Navigation.toTransformations(tag.getReference()).asLink(
					"transformation");
			transformationLink.add(new Label("text", tag.getText()));
			add(transformationLink);

			BookmarkablePageLink<DescriptionsPage> descriptionsShortLink = Navigation.toDescriptions(tag.getName(),
					tag.isObject()).asLink("descriptionsShort");
			add(descriptionsShortLink);

			if ("".equals(tag.getText())) {
				transformationLink.setVisible(false);
				descriptionsShortLink.setVisible(false);
			}

		}

	}

}
