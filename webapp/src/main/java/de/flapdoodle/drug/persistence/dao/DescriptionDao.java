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

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.mongoom.AbstractDao;

public class DescriptionDao extends AbstractDao<Description> {

	public DescriptionDao() {
		super(Description.class);
	}

	public List<Description> findByName(boolean isObject, String name) {
		return createQuery().field(Description.isObject).eq(isObject).or().field(Description.Name).eq(name).parent().or().listfield(
				Description.OtherNames).eq(name).parent().result().order("name", true).asList();
	}
	
	public List<Description> findByType(boolean isObject) {
		return createQuery().field(Description.isObject).eq(isObject).result().order("name", true).asList();
	}

	public List<Description> findAnyByName(String name) {
		return createQuery().or().field(Description.Name).eq(name).parent().or().listfield(Description.OtherNames).eq(name).parent().result().order(
				"name", true).asList();
	}

	public Description getByName(String name) {
		return createQuery().field(Description.Name).eq(name).result().order("name", true).get();
	}
}
