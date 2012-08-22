package de.flapdoodle.drug.wiki.parser;

import org.parboiled.Parboiled;
import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.Parser;
import org.pegdown.PegDownProcessor;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.RootNode;

import junit.framework.TestCase;


public class TestParser extends TestCase {

	public void testParser() {
		String markup = "--what-- [s:Subject] [p:pred] [o:Object] <a href=http://google.de>Link</a> <a href=COL0101010201203120310></a>";
		TParser parser=Parboiled.createParser(TParser.class, Extensions.ALL);
//	   PegDownProcessor p = new PegDownProcessor(Parboiled.createParser(TParser.class, Extensions.ALL));
		RootNode root = parser.parse((markup+"\n\n").toCharArray());
		String html=new ToHtmlSerializer(new LinkRenderer()).toHtml(root);
//		html = p.markdownToHtml(markup,);
////	   assertEquals("", html);
	   System.out.println("Html: "+html);
	}
}
