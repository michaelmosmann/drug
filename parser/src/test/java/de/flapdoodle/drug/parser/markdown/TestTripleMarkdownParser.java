/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <${lic.email2}>
 *
 * with contributions from
 * 	${lic.developers}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

	public void testHidden() {
		String markup = "[s:Wer->Mensch] kann [o:Seiten] [p:erstellen]?\n\n"+
										"[!s:Wer->Mensch][o:Seiten] [p:erstellen]?";

		String match="<p>Wer kann Seiten erstellen?</p>" +
				"<p>Seiten erstellen?</p>";
		String html = toHtml(markup);
		assertEquals(match, html);
//		System.out.println("---------------------------\n");
//		System.out.println("Html: " + html);
//		System.out.println("---------------------------\n");
	}

	public void testFull() {
		String markup = "[s:Subjekt] [p:predikat] [o:Object]\n\n"+
				"[s:Subjekt] [p:predikat] [o:Object] [at:in China->China]\n\n" +
				"[s:Subjekt] [p:predikat] [o:Object] nach [to:China]\n\n" +
				"[s:Subjekt] [p:predikat] [o:Object] [from:von China->China]\n\n" +
				"[s:Subjekt] [p:predikat] [o:Object] bei [near:China]\n\n" +
										"";

		String match="<p>Subjekt predikat Object</p>" +
				"<p>Subjekt predikat Object in China</p>" +
				"<p>Subjekt predikat Object nach China</p>" +
				"<p>Subjekt predikat Object von China</p>" +
				"<p>Subjekt predikat Object bei China</p>";
		String html = toHtml(markup);
		assertEquals(match, html);
//		System.out.println("---------------------------\n");
//		System.out.println("Html: " + html);
//		System.out.println("---------------------------\n");
	}
}
