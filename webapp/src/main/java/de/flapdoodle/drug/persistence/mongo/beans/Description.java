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
package de.flapdoodle.drug.persistence.mongo.beans;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.inject.internal.Lists;

import de.flapdoodle.mongoom.IEntity;
import de.flapdoodle.mongoom.annotations.Direction;
import de.flapdoodle.mongoom.annotations.Entity;
import de.flapdoodle.mongoom.annotations.Id;
import de.flapdoodle.mongoom.annotations.Version;
import de.flapdoodle.mongoom.annotations.Views;
import de.flapdoodle.mongoom.annotations.index.IndexGroup;
import de.flapdoodle.mongoom.annotations.index.IndexOption;
import de.flapdoodle.mongoom.annotations.index.Indexed;
import de.flapdoodle.mongoom.annotations.index.IndexedInGroup;
import de.flapdoodle.mongoom.exceptions.MappingException;
import de.flapdoodle.mongoom.mapping.callbacks.IEntityWriteCallback;
import de.flapdoodle.mongoom.mapping.properties.PropertyReference;
import de.flapdoodle.mongoom.types.Reference;

@Entity(value = "description", onWrite = Description.OnWrite.class)
@IndexGroup(name = "namesAndType", group = "namesAndType")
@Views({Description.OnlyName.class})
public class Description extends AbstractDescription implements IEntity<Description> {

	public static class OnWrite implements IEntityWriteCallback<Description> {

		@Override
		public void onWrite(Description entity) {
			if (entity.getName() == null)
				throw new MappingException(Description.class, "Name is null");
		}

	}

	public static final PropertyReference<Boolean> isObject = de.flapdoodle.mongoom.mapping.properties.Property.ref(
			"object", boolean.class);
	public static final PropertyReference<String> Name = de.flapdoodle.mongoom.mapping.properties.Property.ref("name",
			String.class);
	public static final PropertyReference<List<String>> OtherNames = de.flapdoodle.mongoom.mapping.properties.Property.ref(
			"otherNames", de.flapdoodle.mongoom.mapping.properties.Property.listType(String.class));

	@Id
	Reference<Description> _id;

//	@Version
//	String _version;

	@Indexed(options = @IndexOption(unique = true))
	@IndexedInGroup(group = "namesAndType", priority = 10, direction = Direction.ASC)
	String _name;

	@IndexedInGroup(group = "namesAndType", priority = 9, direction = Direction.ASC)
	List<String> _otherNames = Lists.newArrayList();

	@IndexedInGroup(group = "namesAndType", priority = 11, direction = Direction.ASC)
	boolean _object = true;

	public void setId(Reference<Description> id) {
		_id = id;
	}
	
	@Override
	public Reference<Description> getId() {
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

	public static class OnlyName {

		@Id
		Reference<Description> _id;

		String _name;

		public Reference<Description> getId() {
			return _id;
		}

		public String getName() {
			return _name;
		}

	}
}
