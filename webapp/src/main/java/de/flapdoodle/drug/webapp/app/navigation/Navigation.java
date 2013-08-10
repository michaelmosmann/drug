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
package de.flapdoodle.drug.webapp.app.navigation;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.flapdoodle.drug.markup.ContextType;
import de.flapdoodle.drug.markup.Type;
import de.flapdoodle.drug.persistence.mongo.beans.Description;
import de.flapdoodle.drug.persistence.mongo.beans.Transformation;
import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.TransformationDto;
import de.flapdoodle.drug.render.TagReference;
import de.flapdoodle.drug.webapp.app.edit.EditDescriptionPage;
import de.flapdoodle.drug.webapp.app.edit.EditTransformationPage;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.view.DescriptionPage;
import de.flapdoodle.drug.webapp.app.view.DescriptionsPage;
import de.flapdoodle.drug.webapp.app.view.TransformationPage;
import de.flapdoodle.drug.webapp.app.view.TransformationsPage;
import de.flapdoodle.drug.webapp.security.NotPublic;

public class Navigation {

	private static final String P_TYPE = "type";
	private static final String P_CONTEXT = "context";
	private static final String P_OBJECT = "object";
	private static final String P_PREDICATE = "predicate";
	private static final String P_SUBJECT = "subject";

	public static Jump<DescriptionPage> toDescription(DescriptionDto descriptionDto) {
		return DescriptionPage.toDescription(descriptionDto);
	}
	
	public static Jump<DescriptionPage> toDescription(Description description) {
		return DescriptionPage.toDescription(description);
	}

	public static Jump<DescriptionsPage> toDescriptions(String name,boolean isObject) {
		return DescriptionsPage.toDescriptions(name,isObject);
	}
	
	public static Jump<EditDescriptionPage> editDescription(Description description) {
		return EditDescriptionPage.editDescription(description);
	}
	
	public static Jump<EditDescriptionPage> editDescription(DescriptionDto descriptionDto) {
		return EditDescriptionPage.editDescription(descriptionDto);
	}
	
	public static Jump<EditDescriptionPage> editDescription(String name,boolean isObject) {
		return EditDescriptionPage.editDescription(name,isObject);
	}
	
//	public static Jump<TransformationsPage> toTransformations(String subject,String predicate, String object) {
//		return TransformationsPage.toTransformations(subject, predicate, object);
//	}
	
	public static Jump<TransformationPage> toTransformation(Transformation transformation) {
		return TransformationPage.toTransformation(transformation);
	}
	
	public static Jump<TransformationPage> toTransformation(TransformationDto transformationDto) {
		return TransformationPage.toTransformation(transformationDto);
	}
	
	public static Jump<EditTransformationPage> editTransformation(TagReference reference) {
		return EditTransformationPage.editTransformation(reference);
	}
	
	public static Jump<EditTransformationPage> editTransformation(Transformation transformation) {
		return EditTransformationPage.editTransformation(transformation);
	}
	
	public static Jump<EditTransformationPage> editTransformation(TransformationDto transformation) {
		return EditTransformationPage.editTransformation(transformation);
	}
	
	public static class Jump<T extends Page> {

		private final Class<T> _pageClass;
		private final PageParameters _params;
		private final boolean _privateLink;

		public Jump(Class<T> pageClass, PageParameters params) {
			this(pageClass,params,false);
		}
		
		public Jump(Class<T> pageClass, PageParameters params, boolean privateLink) {
			_pageClass = pageClass;
			_params = params;
			_privateLink = privateLink;
		}

		public BookmarkablePageLink<T> asLink(String id) {
			if (_privateLink) return new PrivateBookmarkablePageLink<T>(id, _pageClass, _params);
			return new BookmarkablePageLink<T>(id, _pageClass, _params);
		}

		public void asResponse() {
			throw new RedirectToUrlException(RequestCycle.get().urlFor(_pageClass,_params).toString());
		}
	}

	@NotPublic
	static class PrivateBookmarkablePageLink<T extends Page> extends BookmarkablePageLink<T> {

		public PrivateBookmarkablePageLink(String id, Class<T> pageClass, PageParameters parameters) {
			super(id, pageClass, parameters);
		}
	}

	public static Jump<TransformationsPage> toTransformations(TagReference reference) {
		return TransformationsPage.toTransformations(reference);
	}

	public static PageParameters asPageParameters(TagReference reference) {
		PageParameters params=new PageParameters();
		if (reference!=null) {
			String subject=reference.getSubject();
			String predicate=reference.getPredicate();
			String object=reference.getObject();
			String context=reference.getContext();
			
			ContextType contextType=reference.getContextType();
			
			if (subject != null)
				params.set(P_SUBJECT, subject);
			if (predicate != null)
				params.set(P_PREDICATE, predicate);
			if (object != null)
				params.set(P_OBJECT, object);
			if (context != null) {
				params.set(P_CONTEXT, context);
				if (contextType != null)
					params.set(P_TYPE, contextType.asString());
			}
		}
		return params;
	}
	
	public static TagReference fromPageParameters(PageParameters p) {
		String subject=p.get(P_SUBJECT).toOptionalString();
		String predicate=p.get(P_PREDICATE).toOptionalString();
		String object=p.get(P_OBJECT).toOptionalString();
		String context=p.get(P_CONTEXT).toOptionalString();
		ContextType contextType=null;
		if (context!=null) contextType=ContextType.fromString(p.get(P_TYPE).toOptionalString());
		return new TagReference(subject, predicate, object, contextType, context);
	}

}
