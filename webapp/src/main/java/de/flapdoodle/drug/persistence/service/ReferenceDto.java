package de.flapdoodle.drug.persistence.service;

import java.io.Serializable;


public final class ReferenceDto<T> implements Serializable {
	
	final String _id;
	final Class<T> _type;
	
	public ReferenceDto(Class<T> type,String id) {
		_type = type;
		_id = id;
	}
	
	public String getId() {
		return _id;
	}
	
	public static <T> ReferenceDto<T> with(Class<T> type,String id) {
		return new ReferenceDto<T>(type, id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null)
				? 0
				: _id.hashCode());
		result = prime * result + ((_type == null)
				? 0
				: _type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReferenceDto other = (ReferenceDto) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (_type == null) {
			if (other._type != null)
				return false;
		} else if (!_type.equals(other._type))
			return false;
		return true;
	}	
	
	
}
