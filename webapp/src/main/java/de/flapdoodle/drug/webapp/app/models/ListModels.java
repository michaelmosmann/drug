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
package de.flapdoodle.drug.webapp.app.models;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;


public class ListModels {

	public static <T> IModel<T> ifOnlyOne(IModel<List<T>> descriptions) {
		return Models.on(descriptions).apply(new Function1<T, List<T>>() {
			@Override
			public T apply(List<T> list) {
				if ((list!=null) && (list.size()==1)) return list.get(0);
				return null;
			}
		});
	}

	public static <T> IModel<List<T>> ifMoreThanOne(IModel<List<T>> descriptions) {
		return Models.on(descriptions).apply(new Function1<List<T>, List<T>>() {
			@Override
			public List<T> apply(List<T> list) {
				if ((list!=null) && (list.size()>1)) return list;
				return null;
			}
		});
	}

}
