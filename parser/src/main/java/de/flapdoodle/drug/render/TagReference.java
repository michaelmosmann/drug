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

import java.io.Serializable;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.markup.Type;

public class TagReference implements Serializable {

	private final String _subject;
	private final String _predicate;
	private final String _object;

	private final ContextType _contextType;
	private final String _context;

	public TagReference(String subject, String predicate, String object, ContextType contextType, String context) {
		super();
		_subject = subject;
		_predicate = predicate;
		_object = object;
		_contextType = contextType;
		_context = context;
	}

	public String getSubject() {
		return _subject;
	}

	public String getPredicate() {
		return _predicate;
	}

	public String getObject() {
		return _object;
	}

	public ContextType getContextType() {
		return _contextType;
	}

	public String getContext() {
		return _context;
	}

	public boolean notSet() {
		return _subject==null && _predicate==null && _object==null && _context==null;
	}

	public boolean anythingIsSet() {
		return !notSet();
	}

	public boolean isOpen() {
		return _subject==null || _predicate==null || _object==null ;
	}

}
