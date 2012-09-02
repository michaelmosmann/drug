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
package de.flapdoodle.drug.webapp.app.models;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.flapdoodle.drug.parser.markdown.TripleMarkdown;
import de.flapdoodle.drug.render.ITag;
import de.flapdoodle.drug.render.Tag;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;

public class Markups {
	
	public static IModel<List<ITag>> asTagsFormMarkdown(IModel<String> markDown) {
		return Models.on(markDown).apply(new Function1<List<ITag>, String>() {

			@Override
			public List<ITag> apply(String markDown) {
				if (markDown!=null)
					return TripleMarkdown.asTags(markDown);
				return null;
			}
		});
	}
}
