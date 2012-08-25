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

		String html = toHtml(markup);
		System.out.println("---------------------------\n");
		System.out.println("Html: " + html);
		System.out.println("---------------------------\n");
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

		String html = toHtml(markup);
		System.out.println("---------------------------\n");
		System.out.println("Html: " + html);
		System.out.println("---------------------------\n");
	}

	public void testExample2() {
		String markup = "Es wird Markdown Sysntax benutzt\n" +
				"================================\n\n"+
				"das kann so aussehen:\n\n" +
				" * Wer kann [s:->Mensch] [o:Seiten] [p:erstellen]?\n"+
				" * http://wikipedia.de ?\n" +
				"\n" +
				"test";

		String html = toHtml(markup);
		System.out.println("---------------------------\n");
		System.out.println("Html: " + html);
		System.out.println("---------------------------\n");
	}
}
