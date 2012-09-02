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
package de.flapdoodle.drug.render;

import de.flapdoodle.drug.markup.Type;

public class Tag implements ITag {

	private final String _text;
	private String _name;
	private boolean _isObject;
	TagReference _reference;


	public Tag(String text, String name, boolean isObject, TagReference reference) {
		_text = text;
		_name=name;
		_reference=reference;
		_isObject = isObject;
	}

	public String getText() {
		return _text;
	}
	
	public TagReference getReference() {
		return _reference;
	}

	public String getName() {
		return _name;
	}

	public boolean isObject() {
		return _isObject;
	}
}