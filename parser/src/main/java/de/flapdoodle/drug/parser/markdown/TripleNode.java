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
package de.flapdoodle.drug.parser.markdown;


import de.flapdoodle.drug.markup.Type;

public class TripleNode extends AbstractTripleNode {

	private final Type _type;
	public TripleNode(char type, boolean hidden, Integer index, String text) {
		super(hidden,index,text);

		_type = Type.fromChar(type);
		if (_type == null)
			throw new IllegalArgumentException("Unknown Type " + type);
	}

	@Override
	public String toString() {
		return toString(""+_type.asChar());
	}

	public Type getType() {
		return _type;
	}
}
