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
package de.flapdoodle.drug.persistence.service;

import de.flapdoodle.drug.markup.ContextType;

public class TransformationDto extends AbstractDescriptionDto {

	ReferenceDto<TransformationDto> _id;

	public void setId(ReferenceDto<TransformationDto> id) {
		_id = id;
	}

	public ReferenceDto<TransformationDto> getId() {
		return _id;
	}

	ReferenceDto<DescriptionDto> _subject;

	ReferenceDto<DescriptionDto> _predicate;

	ReferenceDto<DescriptionDto> _object;

	ReferenceDto<DescriptionDto> _context;

	ContextType _contextType;

	String _title;

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public ReferenceDto<DescriptionDto> getSubject() {
		return _subject;
	}

	public void setSubject(ReferenceDto<DescriptionDto> subject) {
		_subject = subject;
	}

	public ReferenceDto<DescriptionDto> getPredicate() {
		return _predicate;
	}

	public void setPredicate(ReferenceDto<DescriptionDto> predicate) {
		_predicate = predicate;
	}

	public ReferenceDto<DescriptionDto> getObject() {
		return _object;
	}

	public void setObject(ReferenceDto<DescriptionDto> object) {
		_object = object;
	}

	public ReferenceDto<DescriptionDto> getContext() {
		return _context;
	}

	public void setContext(ReferenceDto<DescriptionDto> context) {
		_context = context;
	}

	public ContextType getContextType() {
		return _contextType;
	}

	public void setContextType(ContextType contextType) {
		_contextType = contextType;
	}

}
