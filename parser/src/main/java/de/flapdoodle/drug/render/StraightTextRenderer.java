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

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.markup.IMarkupVisitor;
import de.flapdoodle.drug.markup.IRelation;
import de.flapdoodle.drug.markup.Label;
import de.flapdoodle.drug.markup.Type;


public class StraightTextRenderer extends AbstractMarkupVisitor {

	StringBuilder sb=new StringBuilder();

	@Override
	public void text(String text) {
		sb.append(text);
	}

	@Override
	public void reference(Label label) {
		sb.append(displayOrName(label));
	}

	private String displayOrName(Label label) {
		return label.getDisplay()!=null ? label.getDisplay() : label.getName();
	}

	@Override
	public void subject(Label label, IRelation relation) {
		sb.append(displayOrName(label));
	}

	@Override
	public void predicate(Label label, IRelation relation) {
		sb.append(displayOrName(label));
	}

	@Override
	public void object(Label label, IRelation relation) {
		sb.append(displayOrName(label));
	}
	
	@Override
	public void context(Label label, ContextType type, IRelation relation) {
		sb.append(displayOrName(label));
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
}
