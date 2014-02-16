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
package de.flapdoodle.drug.persistence.service;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import de.flapdoodle.drug.persistence.mongo.beans.Description;

public class DescriptionDto extends AbstractDescriptionDto {

	ReferenceDto<DescriptionDto> _id;

	String _name;

	List<String> _otherNames = Lists.newArrayList();

	boolean _object = true;

	public void setId(ReferenceDto<DescriptionDto> id) {
		_id = id;
	}
	
	public ReferenceDto<DescriptionDto> getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public Set<String> getOtherNames() {
		return Sets.newHashSet(_otherNames);
	}

	public void setOtherNames(Set<String> otherNames) {
		_otherNames = otherNames != null
				? Lists.newArrayList(otherNames)
				: Lists.<String> newArrayList();
	}

	public boolean isObject() {
		return _object;
	}

	public void setObject(boolean object) {
		_object = object;
	}

}
