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
