/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <${lic.email2}>
 *
 * with contributions from
 * 	${lic.developers}
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

public class Tag {

	private final String _text;
	private String _name;
//	private String _subject;
//	private String _predicate;
//	private String _object;
//	private String _context;
	private String _relName;
	private boolean _isObject;
//	private Type _contextType;
	TagReference _reference;

	public Tag(String text) {
		_text = text;
	}

	public Tag(String text, String name) {
		_text = text;
		_name = name;
	}

	public Tag(String text, String name, boolean isObject, TagReference reference) {
		_text = text;
		_reference=reference;
		_relName = name;
		_isObject = isObject;
	}

	public boolean isText() {
		return _name == null && (_reference==null || _reference.notSet());
	}

	public boolean isRelation() {
		return _reference!=null && _reference.anythingIsSet();
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

	public String getRelName() {
		return _relName;
	}

	public boolean isObject() {
		return _isObject;
	}
}