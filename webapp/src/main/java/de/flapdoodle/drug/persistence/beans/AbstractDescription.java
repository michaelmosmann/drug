package de.flapdoodle.drug.persistence.beans;

import java.io.Serializable;

import de.flapdoodle.mongoom.annotations.MappedSuperclass;
import de.flapdoodle.mongoom.annotations.Version;


@MappedSuperclass
public abstract class AbstractDescription implements Serializable {

	@Version
	String _version;

	String _text;

	public String getText() {
		return _text;
	}

	public void setText(String text) {
		_text = text;
	}

}
