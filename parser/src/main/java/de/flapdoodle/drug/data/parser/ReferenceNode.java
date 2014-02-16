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
package de.flapdoodle.drug.data.parser;

import java.util.List;

import org.parboiled.common.ImmutableList;
import org.pegdown.ast.AbstractNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.Visitor;

import de.flapdoodle.drug.markup.ContextType;


public abstract class ReferenceNode extends AbstractNode {

	private final int _index;
	private final String _text;
	private final String _base;
	private final boolean _hidden;

	public ReferenceNode(boolean hidden, Integer index, String text) {
		_hidden = hidden;

		_index = index != null
				? index
				: -1;
		String base = null;
		int idx = text.indexOf("->");
		if (idx != -1) {
			base = text.substring(idx + 2);
			text = text.substring(0, idx);
		}
		_text = text;
		_base = base;
	}

	@Override
	public List<Node> getChildren() {
		return ImmutableList.of();
	}

	public String getText() {
		return _text;
	}

	public String getBase() {
		return _base;
	}

	public int getIndex() {
		return _index;
	}

	public boolean isHidden() {
		return _hidden;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	protected String toString(String type) {
		return "[" + (_hidden
				? "!"
				: "") + type + (_index != -1
				? _index
				: "") + ":" + _text + (_base != null
				? "->" + _base
				: "") + "]";
	}



}
