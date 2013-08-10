package de.flapdoodle.drug.persistence.service;

import java.io.Serializable;


public class ReferenceDto<T> implements Serializable {
	
	final String _id;
	final Class<T> _type;
	
	public ReferenceDto(Class<T> type,String id) {
		_type = type;
		_id = id;
	}
	
	public String getId() {
		return _id;
	}	
}
