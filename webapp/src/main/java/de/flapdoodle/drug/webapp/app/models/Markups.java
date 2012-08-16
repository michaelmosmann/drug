package de.flapdoodle.drug.webapp.app.models;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.flapdoodle.drug.markup.Markup;
import de.flapdoodle.drug.markup.MarkupException;
import de.flapdoodle.drug.render.TagListVisitior;
import de.flapdoodle.drug.render.TagListVisitior.Tag;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;

public class Markups {

	public static IModel<Markup> asMarkup(IModel<String> textModel) {
		return Models.on(textModel).apply(new Function1<Markup, String>() {

			@Override
			public Markup apply(String text) {
				try {
					return Markup.fromString(text);
				} catch (MarkupException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public static IModel<List<Tag>> asTags(IModel<Markup> markupModel) {
		return Models.on(markupModel).apply(new Function1<List<Tag>, Markup>() {

			@Override
			public List<Tag> apply(Markup markup) {
				if (markup != null)
					return TagListVisitior.asTags(markup);
				return null;
			}
		});
	}
}