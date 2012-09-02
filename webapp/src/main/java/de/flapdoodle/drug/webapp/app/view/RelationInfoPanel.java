package de.flapdoodle.drug.webapp.app.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.common.collect.Lists;

import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonSize;
import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonType;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.webapp.app.models.TransformationProperties;
import de.flapdoodle.drug.webapp.app.navigation.Navigation;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.mongoom.types.Reference;
import de.flapdoodle.wicket.model.Models;

public class RelationInfoPanel extends Panel {

	public RelationInfoPanel(String id, IModel<Transformation> relation) {
		super(id);

		IModel<Map<Reference<Description>, String>> names = TransformationProperties.names(relation);

		IModel<List<JumpInfo<DescriptionsPage>>> links = Models.on(relation, names).apply(
				new Function2<List<JumpInfo<DescriptionsPage>>, Transformation, Map<Reference<Description>, String>>() {

					@Override
					public List<JumpInfo<DescriptionsPage>> apply(Transformation t,
							Map<Reference<Description>, String> names) {
						ArrayList<JumpInfo<DescriptionsPage>> ret = Lists.newArrayList();
						
						addIfSet(ret, names, "s", t.getSubject(), true);
						addIfSet(ret, names, "p", t.getPredicate(), false);
						addIfSet(ret, names, "o", t.getObject(), true);
						if (t.getContextType()!=null) addIfSet(ret, names, t.getContextType().asString(), t.getContext(), true);
						
						return ret;
					}

					private void addIfSet(ArrayList<JumpInfo<DescriptionsPage>> ret, Map<Reference<Description>, String> names,
							String prefix, Reference<Description> subject, boolean isObject) {
						if (subject!=null) {
							String name = names.get(subject);
							if (name!=null) {
								ret.add(new JumpInfo<DescriptionsPage>(prefix +":"+ name, Navigation.toDescriptions(name, isObject)));
							}
						}
					}

				});
		
		add(new ListView<JumpInfo<DescriptionsPage>>("links",links) {
			@Override
			protected void populateItem(ListItem<JumpInfo<DescriptionsPage>> item) {
				JumpInfo<DescriptionsPage> jumpInfo = item.getModelObject();
				
				BookmarkablePageLink<DescriptionsPage> link = jumpInfo.getJump().asLink("link");
				link.setBody(Model.of(jumpInfo.getName()));
				link.add(new ButtonBehavior(ButtonType.Default, ButtonSize.Mini));
				item.add(link);
			}
		});
		//		Navigation.toDescriptions(relation.getObject()., isObject)
	}

	static class JumpInfo<T extends Page> {

		final String _name;
		final Jump<T> _jump;

		public JumpInfo(String name, Jump<T> jump) {
			_name = name;
			_jump = jump;
		}

		public String getName() {
			return _name;
		}

		public Jump<T> getJump() {
			return _jump;
		}
	}
}
