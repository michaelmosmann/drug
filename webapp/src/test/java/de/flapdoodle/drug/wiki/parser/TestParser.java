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
import de.flapdoodle.drug.parser.markdown.TripleNodeRelationMap;
import de.flapdoodle.drug.parser.markdown.TripleReferenceVisitor;

import junit.framework.TestCase;

public class TestParser extends TestCase {

	public void testParser() {
		String markup = "--what-- [s0:Subject] [p2:pred] [o9:Object] <a href=http://google.de>Link</a> <a href=COL0101010201203120310></a>";
		markup = "[s0:Subject] [p2:pred] [o9:Object] [[wiki]] bla:* http://nix.de";
		RootNode root = TripleMarkdownParser.parseMarkup(markup);
		//		String html=new ToHtmlSerializer(new LinkRenderer()).toHtml(root);
		//	   System.out.println("Html: "+html);

		System.out.println("Html: " + new DebugVisitor().toHtml(root));

		new TripleReferenceVisitor().buildReferenceMap(root);

	}
}
