package de.flapdoodle.drug.webapp.app.models;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.flapdoodle.drug.parser.markdown.TripleMarkdown;
import de.flapdoodle.drug.render.Tag;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;

public class Markups {
	
	public static IModel<List<Tag>> asTagsFormMarkdown(IModel<String> markDown) {
		return Models.on(markDown).apply(new Function1<List<Tag>, String>() {

			@Override
			public List<Tag> apply(String markDown) {
				if (markDown!=null)
					return TripleMarkdown.asTags(markDown);
				return null;
			}
		});
	}
}
