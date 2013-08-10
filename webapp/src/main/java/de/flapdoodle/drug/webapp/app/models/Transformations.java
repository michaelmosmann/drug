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

import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.mongo.dao.SearchDao;
import de.flapdoodle.drug.render.TagReference;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.Models;


public class Transformations implements Function1<List<Transformation>, TagReference> {

	@Inject
	SearchDao _searchDao;
	
	public Transformations() {
		Injector.get().inject(this);
	}
	
	@Override
	public List<Transformation> apply(TagReference ref) {
		return _searchDao.find(ref.getSubject(), ref.getPredicate(), ref.getObject(), ref.getContextType(), ref.getContext());
	}
	
	public static IModel<List<Transformation>> search(TagReference reference) {
		// TODO Auto-generated method stub
		return Models.on(Model.of(reference)).apply(new Transformations());
	}
}
