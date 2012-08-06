package de.flapdoodle.drug.markup;

public class Label {

	final String _name;
	String _display;

	public Label(String name) {
		_name = name;
	}

	public Label(String name, String display) {
		_name = name;
		_display = display;
	}

	public String getName() {
		return _name;
	}

	public String getDisplay() {
		return _display;
	}
	
	public String getDisplayOrName() {
		return _display!=null ? _display : _name;
	}
}
