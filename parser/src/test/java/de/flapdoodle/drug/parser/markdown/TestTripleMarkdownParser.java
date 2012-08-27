package de.flapdoodle.drug.parser.markdown;

import org.pegdown.ast.RootNode;

import de.flapdoodle.drug.markup.IMarkupVisitor;
import de.flapdoodle.drug.render.StraightTextRenderer;

import junit.framework.TestCase;

public class TestTripleMarkdownParser extends TestCase {

	public void testBuildTripleTable() {
		String markup = "[s:Aaaah->ah!] [p:a] [o:Auiuiui]\n[s0:B] [p:b] [o0:B]\n\n[s:C] [p:c] [o:C]\n\n"
				+ "WikiLinks [[Klaus]] und [[Bert]] \n\n" + "Das ist ein http://autolink.de oder?\n\n"
				+ "Dies ist [ein Beispiel](http://example.com/ \"Der Linktitel\") für" + "einen Inline-Link.\n\n"
				+ "Dies ist [ein Beispiel] [id] für einen Referenz-Link.\n\n"
				+ "  [id]: http://example.com/  \"Optionalen Titel hier eintragen\"\n\n";

		String match="<p>Aaaah a Auiuiui<br/>B !!![p:b]!!! B</p><p>C c C</p>" +
				"<p>WikiLinks Klaus und Bert </p><p>Das ist ein <a href=\"http://autolink.de\">http://autolink.de</a> oder?</p>" +
				"<p>Dies ist <a href=\"http://example.com/\" title=\"Der Linktitel\">ein Beispiel</a> füreinen Inline-Link.</p>" +
				"<p>Dies ist <a href=\"http://example.com/\" title=\"Optionalen Titel hier eintragen\">ein Beispiel</a> für einen Referenz-Link.</p>";
		String html = toHtml(markup);
		
		assertEquals(match, html);
		
//		System.out.println("---------------------------\n");
//		System.out.println("Html: " + html);
//		System.out.println("---------------------------\n");
		//		System.out.println("All: "+all);
		//		System.out.println("---------------------------\n");
	}

	private String toHtml(String markup) {
		RootNode root = TripleMarkdownParser.parseMarkup(markup);

		TripleNodeRelationMap relMap = new TripleReferenceVisitor().buildReferenceMap(root);
		StraightTextRenderer markupVisitor = new StraightTextRenderer();
		new TripleMarkdownMarkupVisitorAdapter().toHtml(root, relMap, markupVisitor);
		String html = markupVisitor.toString();
		return html;
	}

	public void testExample() {
		String markup = "Das ist die Start-Testseite. Ab hier kann [s:man->Mensch] weitere [o:Seiten] [p:erstellen].\n\n"
				+ "Tests\n" + "[s:->Mensch] [p:einrichten] des [o:Arbeitsplatzes->Arbeitsplatz]\n" + "\n"
				+ "[p:einrichten] des [o:Arbeitsplatzes->Arbeitsplatz] für [s:Mitarbeiter]\n\n";

		String match="<p>Das ist die Start-Testseite. Ab hier kann man weitere Seiten erstellen.</p>" +
				"<p>Tests<br/> einrichten des Arbeitsplatzes</p>" +
				"<p>einrichten des Arbeitsplatzes für Mitarbeiter</p>";
		String html = toHtml(markup);
		assertEquals(match, html);
//		System.out.println("---------------------------\n");
//		System.out.println("Html: " + html);
//		System.out.println("---------------------------\n");
	}

	public void testExample2() {
		String markup = "Es wird Markdown Sysntax benutzt\n" +
				"================================\n\n"+
				"das kann so aussehen:\n\n" +
				" * Wer kann [s:->Mensch] [o:Seiten] [p:erstellen]?\n"+
				" * http://wikipedia.de ?\n" +
				"\n" +
				"test";

		String match="<h1>Es wird Markdown Sysntax benutzt</h1><p>das kann so aussehen:</p>\n"+
								"<ul>\n"+
								"  <li>Wer kann  Seiten erstellen?</li>\n"+
								"  <li><a href=\"http://wikipedia.de\">http://wikipedia.de</a> ?</li>\n"+
								"</ul><p>test</p>";
		String html = toHtml(markup);
		assertEquals(match, html);
//		System.out.println("---------------------------\n");
//		System.out.println("Html: " + html);
//		System.out.println("---------------------------\n");
	}
}
