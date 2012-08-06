package de.flapdoodle.drug.markup;

public class Text implements IPart {

	String _text;

	public Text(String text) {
		_text = text;
	}

	public String getText() {
		return _text;
	}
}
