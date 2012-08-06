package de.flapdoodle.drug.webapp.app.edit;

import java.util.Locale;
import java.util.Set;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import com.google.common.collect.Sets;

public class EditWordlistPanel extends Panel {

	public EditWordlistPanel(String id, IModel<Set<String>> setModel) {
		super(id);

		add(new TextField<Set<String>>("list", setModel) {

			@Override
			public <C> IConverter<C> getConverter(Class<C> type) {
				return (IConverter<C>) new StringSetConverter();
			}
		});
	}
	
	static class StringSetConverter implements IConverter<Set<String>> {

		@Override
		public Set<String> convertToObject(String value, Locale locale) {
			Set<String> ret = null;
			if (value != null) {
				ret = Sets.newLinkedHashSet();
				for (String v : value.split("[, ]")) {
					ret.add(v);
				}
			}
			return ret;
		}

		@Override
		public String convertToString(Set<String> value, Locale locale) {
			Set<String> values = (Set<String>) value;
			StringBuilder sb = new StringBuilder();
			if (values != null) {
				for (String v : values) {
					sb.append(v);
					sb.append(" ");
				}
			}
			return sb.toString();
		}

	}
}
