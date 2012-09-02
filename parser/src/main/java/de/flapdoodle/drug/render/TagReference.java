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
		return _predicate==null || _object==null ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_context == null)
				? 0
				: _context.hashCode());
		result = prime * result + ((_contextType == null)
				? 0
				: _contextType.hashCode());
		result = prime * result + ((_object == null)
				? 0
				: _object.hashCode());
		result = prime * result + ((_predicate == null)
				? 0
				: _predicate.hashCode());
		result = prime * result + ((_subject == null)
				? 0
				: _subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagReference other = (TagReference) obj;
		if (_context == null) {
			if (other._context != null)
				return false;
		} else if (!_context.equals(other._context))
			return false;
		if (_contextType != other._contextType)
			return false;
		if (_object == null) {
			if (other._object != null)
				return false;
		} else if (!_object.equals(other._object))
			return false;
		if (_predicate == null) {
			if (other._predicate != null)
				return false;
		} else if (!_predicate.equals(other._predicate))
			return false;
		if (_subject == null) {
			if (other._subject != null)
				return false;
		} else if (!_subject.equals(other._subject))
			return false;
		return true;
	}

	
}
