package de.flapdoodle.drug.parser.markdown;

import org.pegdown.ast.RootNode;

import de.flapdoodle.drug.markup.IMarkupVisitor;
import de.flapdoodle.drug.render.StraightTextRenderer;

import junit.framework.TestCase;


public class TestTripleMarkdownParser extends TestCase {

	public void testBuildTripleTable() {
		String markup ="[s:A] [p:a] [o:A]\n[s0:B] [p:b] [o0:B]\n\n[s:C] [p:c] [o:C]\n\n" +
				"WikiLinks [[Klaus]] und [[Bert]] \n\n" +
				"Das ist ein http://autolink.de oder?\n\n" +
				"Dies ist [ein Beispiel](http://example.com/ \"Der Linktitel\") für" +
				"einen Inline-Link.\n\n" +
				"Dies ist [ein Beispiel] [id] für einen Referenz-Link.\n\n" +
				"  [id]: http://example.com/  \"Optionalen Titel hier eintragen\"\n\n";
		
		RootNode root = TripleMarkdownParser.parseMarkup(markup);
		
		TripleNodeRelationMap relMap = new TripleReferenceVisitor().buildReferenceMap(root);
		StraightTextRenderer markupVisitor=new StraightTextRenderer();
		new TripleMarkdownMarkupVisitorAdapter().toHtml(root, relMap, markupVisitor);
		System.out.println("---------------------------\n");
		System.out.println("Html: "+markupVisitor.toString());
		System.out.println("---------------------------\n");
//		System.out.println("All: "+all);
//		System.out.println("---------------------------\n");
	}
}
