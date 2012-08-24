package de.flapdoodle.drug.parser.markdown;

import org.pegdown.ast.RootNode;

import junit.framework.TestCase;


public class TestTripleMarkdownParser extends TestCase {

	public void testBuildTripleTable() {
		String markup ="[s:A] [p:a] [o:A]\n[s0:B] [p:b] [o0:B]\n\n[s:C] [p:c] [o:C]";
		RootNode root = TripleMarkdownParser.parseMarkup(markup);
		
		new TripleReferenceVisitor().buildReferenceMap(root);
	}
}
