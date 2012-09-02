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
package de.flapdoodle.drug.persistence.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

import de.flapdoodle.drug.AbstractEmbedMongoTest;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.mongoom.types.Reference;


public class TestDescriptionDao extends AbstractEmbedMongoTest {

	@Inject
	DescriptionDao _descriptionDao;
	
	public void testDescriptionKeywords() {
		Description entity = new Description();
		entity.setName("Kaffee");
		entity.setOtherNames(Sets.newHashSet("Coffee","Java"));
		_descriptionDao.save(entity);
		
		entity = new Description();
		entity.setName("Zonenkaffee");
		entity.setOtherNames(Sets.newHashSet("Coffee","Java","Rondo"));
		_descriptionDao.save(entity);
		
		List<Description> list = _descriptionDao.findByName(true,"Kaffee");
		assertEquals("size",1, list.size());
		assertEquals("item.name","Kaffee", list.get(0).getName());

		list = _descriptionDao.findByName(true,"Coffee");
		assertEquals("size",2, list.size());
	}
	
	public void testMan() {
		Description entity = new Description();
		entity.setName("Mensch");
		entity.setOtherNames(Sets.newHashSet("Man","ich","Du"));
		_descriptionDao.save(entity);
		
		List<Description> list = _descriptionDao.findAnyByName("Man");
		assertEquals("size",1, list.size());
	}
	
	public void testDescriptions() {
		Set<Reference<Description>> ids=Sets.newHashSet();
		
		Description entity = new Description();
		entity.setName("Kaffee");
		entity.setOtherNames(Sets.newHashSet("Coffee","Java"));
		_descriptionDao.save(entity);
		ids.add(entity.getId());
		
		entity = new Description();
		entity.setName("Zonenkaffee");
		entity.setOtherNames(Sets.newHashSet("Coffee","Java","Rondo"));
		_descriptionDao.save(entity);
		ids.add(entity.getId());
		
		Map<Reference<Description>, String> names = _descriptionDao.names(ids);
		
		assertEquals("size",2, names.size());
		assertEquals("Zonenkaffee","Zonenkaffee", names.get(entity.getId()));
	}
}
