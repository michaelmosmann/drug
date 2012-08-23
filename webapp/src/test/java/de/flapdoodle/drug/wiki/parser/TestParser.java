package de.flapdoodle.drug.wiki.parser;

import org.parboiled.Parboiled;
import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.Parser;
import org.pegdown.PegDownProcessor;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.RootNode;

import de.flapdoodle.drug.parser.markdown.DebugVisitor;
import de.flapdoodle.drug.parser.markdown.TripleMarkdownParser;

import junit.framework.TestCase;


public class TestParser extends TestCase {

	public void testParser() {
		String markup = "--what-- [s0:Subject] [p2:pred] [o9:Object] <a href=http://google.de>Link</a> <a href=COL0101010201203120310></a>";
		markup ="[s0:Subject] [p2:pred] [o9:Object] [[wiki]] bla:* http://nix.de";
		RootNode root = TripleMarkdownParser.parseMarkup(markup);
		String html=new ToHtmlSerializer(new LinkRenderer()).toHtml(root);
	   System.out.println("Html: "+html);
	   
	   System.out.println("Html: "+new DebugVisitor().toHtml(root));
	}
}
