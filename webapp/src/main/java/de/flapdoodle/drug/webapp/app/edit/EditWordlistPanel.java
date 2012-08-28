/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <${lic.email2}>
 *
 * with contributions from
 * 	${lic.developers}
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
