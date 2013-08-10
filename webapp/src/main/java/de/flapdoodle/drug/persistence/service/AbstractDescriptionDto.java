package de.flapdoodle.drug.persistence.service;

import java.io.Serializable;

public class AbstractDescriptionDto implements Serializable {

	String _version;

	String _text;

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public String getText() {
		return _text;
	}

	public void setText(String text) {
		_text = text;
	}
}
