package de.flapdoodle.drug.persistence.service;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.inject.internal.Lists;

public class DescriptionDto extends AbstractDescriptionDto {

	String _id;

	String _name;

	List<String> _otherNames = Lists.newArrayList();

	boolean _object = true;

	public void setId(String id) {
		_id = id;
	}
	
	public String getId() {
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
