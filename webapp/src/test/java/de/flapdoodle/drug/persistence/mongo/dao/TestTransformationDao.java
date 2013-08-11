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
package de.flapdoodle.drug.persistence.mongo.dao;

import java.util.List;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

import static org.junit.Assert.assertEquals;

import de.flapdoodle.drug.AbstractEmbedMongoTest;
import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.mongo.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.mongo.dao.SearchDao;
import de.flapdoodle.drug.persistence.mongo.dao.TransformationDao;

public class TestTransformationDao extends AbstractEmbedMongoTest {

	@Inject
	TransformationDao _transformationDao;

	@Inject
	DescriptionDao _descriptionDao;

	@Inject
	SearchDao _searchDao;

	public void testFindSomething() {

		fillWithData();

		SearchDao searchDao = _searchDao;//new SearchDao(_descriptionDao, _transformationDao);

		List<Transformation> list = searchDao.find("Du", "futtern", "Brot", null, null);
		assertEquals("Size", 1, list.size());
		list = searchDao.find("Ich", "futtern", "Brot", null, null);
		assertEquals("Size", 1, list.size());
		list = searchDao.find(null, "futtern", "Brot", null, null);
		assertEquals("Size", 1, list.size());
		list = searchDao.find("Ich", null, "Brot", null, null);
		assertEquals("Size", 1, list.size());
		list = searchDao.find("Frosch", "löffeln", null, null, null);
		assertEquals("Size", 3, list.size());
		list = searchDao.find("Frosch", "löffeln", null, ContextType.At, "Bahnhof");
		assertEquals("Size", 1, list.size());
		assertEquals("Type", ContextType.At, list.get(0).getContextType());

	}

	private void fillWithData() {
		Description frosch = new Description();
		frosch.setName("Frosch");
		frosch.setOtherNames(Sets.newHashSet("Quack", "Frog"));
		_descriptionDao.save(frosch);

		Description mensch = new Description();
		mensch.setName("Mensch");
		mensch.setOtherNames(Sets.newHashSet("Ich", "Du"));
		_descriptionDao.save(mensch);

		Description loeffeln = new Description();
		loeffeln.setName("löffeln");
		loeffeln.setOtherNames(Sets.newHashSet("schaufeln"));
		loeffeln.setObject(false);
		_descriptionDao.save(loeffeln);

		Description essen = new Description();
		essen.setName("essen");
		essen.setOtherNames(Sets.newHashSet("futtern"));
		essen.setObject(false);
		_descriptionDao.save(essen);

		Description suppe = new Description();
		suppe.setName("Suppe");
		suppe.setOtherNames(Sets.newHashSet("Plörre"));
		_descriptionDao.save(suppe);

		Description brot = new Description();
		brot.setName("Brot");
		brot.setOtherNames(Sets.newHashSet("Bemme"));
		_descriptionDao.save(brot);

		Description bahnhof = new Description();
		bahnhof.setName("Bahnhof");
		_descriptionDao.save(bahnhof);

		{
			Transformation t = new Transformation();
			t.setSubject(mensch.getId());
			t.setPredicate(essen.getId());
			t.setObject(brot.getId());
			_transformationDao.save(t);
		}

		{
			Transformation t = new Transformation();
			t.setSubject(mensch.getId());
			t.setPredicate(loeffeln.getId());
			t.setObject(suppe.getId());
			_transformationDao.save(t);
		}

		{
			Transformation t = new Transformation();
			t.setSubject(frosch.getId());
			t.setPredicate(loeffeln.getId());
			t.setObject(suppe.getId());
			_transformationDao.save(t);
		}

		{
			Transformation t = new Transformation();
			t.setSubject(frosch.getId());
			t.setPredicate(loeffeln.getId());
			t.setObject(brot.getId());
			_transformationDao.save(t);
		}

		{
			Transformation t = new Transformation();
			t.setSubject(frosch.getId());
			t.setPredicate(loeffeln.getId());
			t.setObject(brot.getId());
			t.setContextType(ContextType.At);
			t.setContext(bahnhof.getId());
			_transformationDao.save(t);
		}
	}

}
