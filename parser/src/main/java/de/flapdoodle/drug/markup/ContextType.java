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


public enum ContextType {
	At("at"),
	NearBy("near"),
	From("from"),
	To("to"),
	;
	
	private final String _asString;

	ContextType(String asString) {
		_asString = asString;
	}
	
	public String asString() {
		return _asString;
	}
	
	public static ContextType fromString(String c) {
		for (ContextType t : ContextType.values()) {
			if (t._asString.equals(c)) return t;
		}
		return null;
	}

}
