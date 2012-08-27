package de.flapdoodle.drug.webapp.app.navigation;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.flapdoodle.drug.markup.Type;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;
import de.flapdoodle.drug.render.TagReference;
import de.flapdoodle.drug.webapp.app.edit.EditDescriptionPage;
import de.flapdoodle.drug.webapp.app.edit.EditTransformationPage;
import de.flapdoodle.drug.webapp.app.navigation.Navigation.Jump;
import de.flapdoodle.drug.webapp.app.view.DescriptionPage;
import de.flapdoodle.drug.webapp.app.view.DescriptionsPage;
import de.flapdoodle.drug.webapp.app.view.TransformationPage;
import de.flapdoodle.drug.webapp.app.view.TransformationsPage;

public class Navigation {

	private static final String P_TYPE = "type";
	private static final String P_CONTEXT = "context";
	private static final String P_OBJECT = "object";
	private static final String P_PREDICATE = "predicate";
	private static final String P_SUBJECT = "subject";

	public static Jump<DescriptionPage> toDescription(Description description) {
		return DescriptionPage.toDescription(description);
	}

	public static Jump<DescriptionsPage> toDescriptions(String name,boolean isObject) {
		return DescriptionsPage.toDescriptions(name,isObject);
	}
	
	public static Jump<EditDescriptionPage> editDescription(Description transformation) {
		return EditDescriptionPage.editDescription(transformation);
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
	
	public static Jump<EditTransformationPage> editTransformation(TagReference reference) {
		return EditTransformationPage.editTransformation(reference);
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

	public static Jump<TransformationsPage> toTransformations(TagReference reference) {
		return TransformationsPage.toTransformations(reference);
	}

	public static PageParameters asPageParameters(TagReference reference) {
		PageParameters params=new PageParameters();
		String subject=reference.getSubject();
		String predicate=reference.getPredicate();
		String object=reference.getObject();
		String context=reference.getContext();
		
		Type contextType=reference.getContextType();
		
		if (subject != null)
			params.set(P_SUBJECT, subject);
		if (predicate != null)
			params.set(P_PREDICATE, predicate);
		if (object != null)
			params.set(P_OBJECT, object);
		if (object != null)
			params.set(P_CONTEXT, context);
		if (contextType != null)
			params.set(P_TYPE, contextType.asString());
		return params;
	}
	
	public static TagReference fromPageParameters(PageParameters p) {
		String subject=p.get(P_SUBJECT).toOptionalString();
		String predicate=p.get(P_PREDICATE).toOptionalString();
		String object=p.get(P_OBJECT).toOptionalString();
		String context=p.get(P_CONTEXT).toOptionalString();
		Type contextType=Type.fromString(p.get(P_TYPE).toOptionalString());
		return new TagReference(subject, predicate, object, contextType, context);
	}

}
