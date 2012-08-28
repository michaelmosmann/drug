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
package de.flapdoodle.drug.markup;

public enum Type {
	Subject('s'),
	Predicate('p'),
	Object('o'),
	;
	
	private final char _asChar;

	Type(char asString) {
		_asChar = asString;
	}
	
	public char asChar() {
		return _asChar;
	}
	
	public static Type fromChar(char c) {
		for (Type t : Type.values()) {
			if (t._asChar==c) return t;
		}
		return null;
	}
}