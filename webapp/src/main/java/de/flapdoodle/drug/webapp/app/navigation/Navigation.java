package de.flapdoodle.drug.webapp.app.navigation;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.webapp.app.edit.EditDescriptionPage;
import de.flapdoodle.drug.webapp.app.edit.EditTransformationPage;
import de.flapdoodle.drug.webapp.app.view.DescriptionPage;
import de.flapdoodle.drug.webapp.app.view.TransformationPage;

public class Navigation {

	public static Jump<DescriptionPage> toDescription(Description description) {
		return DescriptionPage.toDescription(description);
	}

	public static Jump<DescriptionPage> toDescriptions(String name) {
		return DescriptionPage.toDescriptions(name);
	}
	
	public static Jump<EditDescriptionPage> editDescription(Description transformation) {
		return EditDescriptionPage.editDescription(transformation);
	}
	
	public static Jump<EditDescriptionPage> editDescription(String name,boolean isObject) {
		return EditDescriptionPage.editDescription(name,isObject);
	}
	
	public static Jump<TransformationPage> toTransformation(String subject,String predicate, String object) {
		return TransformationPage.toTransformation(subject, predicate, object);
	}
	
	public static Jump<TransformationPage> toTransformation(Transformation transformation) {
		return TransformationPage.toTransformation(transformation);
	}
	
	public static Jump<EditTransformationPage> editTransformation(String subject,String predicate, String object) {
		return EditTransformationPage.editTransformation(subject, predicate, object);
	}
	
	public static Jump<EditTransformationPage> editTransformation(Transformation transformation) {
		return EditTransformationPage.editTransformation(transformation);
	}
	
	public static class Jump<T extends Page> {

		private final Class<T> _pageClass;
		private final PageParameters _params;

		public Jump(Class<T> pageClass, PageParameters params) {
			_pageClass = pageClass;
			_params = params;
		}

		public BookmarkablePageLink<T> asLink(String id) {
			return new BookmarkablePageLink<T>(id, _pageClass, _params);
		}

		public void asResponse() {
			throw new RedirectToUrlException(RequestCycle.get().urlFor(_pageClass,_params).toString());
		}
	}

}
