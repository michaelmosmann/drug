package de.flapdoodle.drug.data.parser;

import java.util.List;

import org.pegdown.ast.RootNode;

import de.flapdoodle.drug.parser.markdown.TripleMarkdownMarkupVisitorAdapter;
import de.flapdoodle.drug.parser.markdown.TripleMarkdownParser;
import de.flapdoodle.drug.parser.markdown.TripleNodeRelationMap;
import de.flapdoodle.drug.parser.markdown.TripleReferenceVisitor;
import de.flapdoodle.drug.render.ITag;
import de.flapdoodle.drug.render.TagListVisitior;


public class MarkdownParser implements IMarkdownParser {

	public List<ITag> parse(String markDown) {
		RootNode root = TripleMarkdownParser.parseMarkup(markDown);
		TripleNodeRelationMap relMap = new TripleReferenceVisitor().buildReferenceMap(root);
		TagListVisitior markupVisitor=new TagListVisitior();
		new TripleMarkdownMarkupVisitorAdapter().toHtml(root, relMap, markupVisitor);
		return markupVisitor.getTags();
	}
}
