package de.flapdoodle.drug.render;


public class Text implements ITag {

	private final String _text;

	public Text(String text) {
		_text = text;
	}

	public String getText() {
		return _text;
	}

}
