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
package de.flapdoodle.drug.markup;


public interface IMarkupVisitor {

	void begin();

	void end();

	void text(String text);

	void reference(Label label);

	void subject(Label label, IRelation relation);

	void predicate(Label label, IRelation relation);

	void object(Label label, IRelation relation);

	void context(Label label, ContextType type, IRelation relation);

	void blockStart(String typeAsName);

	void blockEnd(String typeAsName);
}
