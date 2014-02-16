/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
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
