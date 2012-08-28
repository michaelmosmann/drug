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

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.mongoom.types.Reference;
import de.flapdoodle.wicket.model.Models;


public class Descriptions implements Function1<List<Description>, String> {

	@Inject
	DescriptionDao _descriptionDao;
	
	private final boolean _exact;
	
	public Descriptions(boolean exact) {
		_exact = exact;
		
		Injector.get().inject(this);
	}
	
	@Override
	public List<Description> apply(String name) {
		if (_exact) {
			Description description = _descriptionDao.getByName(name);
			if (description!=null) {
				return Lists.newArrayList(description);
			}
			return null;
		}
		return _descriptionDao.findAnyByName(name);
	}
	
	public static IModel<List<Description>> withQuery(String query) {
		return Models.on(Model.of(query)).apply(new Descriptions(false));
	}

	public static IModel<List<Description>> get(String query) {
		return Models.on(Model.of(query)).apply(new Descriptions(true));
	}

	public static IModel<List<Reference<Description>>> references(IModel<List<Description>> choices) {
		return Models.on(choices).apply(new Function1<List<Reference<Description>>, List<Description>>() {
			@Override
			public List<Reference<Description>> apply(List<Description> value) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
}
