package de.flapdoodle.drug.parser.markdown;

import java.util.List;

import org.pegdown.ast.RootNode;

import de.flapdoodle.drug.render.StraightTextRenderer;
import de.flapdoodle.drug.render.TagListVisitior;
import de.flapdoodle.drug.render.TagListVisitior.Tag;


public class TripleMarkdown {

	public static List<Tag> asTags(String markDown) {
		RootNode root = TripleMarkdownParser.parseMarkup(markDown);
		TripleNodeRelationMap relMap = new TripleReferenceVisitor().buildReferenceMap(root);
		TagListVisitior markupVisitor=new TagListVisitior();
		new TripleMarkdownMarkupVisitorAdapter().toHtml(root, relMap, markupVisitor);
		return markupVisitor.getTags();
	}
}
