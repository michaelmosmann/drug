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

import static org.parboiled.common.Preconditions.checkArgNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.parboiled.common.StringUtils;
import org.pegdown.LinkRenderer;
import org.pegdown.Printer;
import org.pegdown.ast.AbbreviationNode;
import org.pegdown.ast.AutoLinkNode;
import org.pegdown.ast.BlockQuoteNode;
import org.pegdown.ast.BulletListNode;
import org.pegdown.ast.CodeNode;
import org.pegdown.ast.DefinitionListNode;
import org.pegdown.ast.DefinitionNode;
import org.pegdown.ast.DefinitionTermNode;
import org.pegdown.ast.EmphNode;
import org.pegdown.ast.ExpImageNode;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.HtmlBlockNode;
import org.pegdown.ast.InlineHtmlNode;
import org.pegdown.ast.ListItemNode;
import org.pegdown.ast.MailLinkNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.OrderedListNode;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.QuotedNode;
import org.pegdown.ast.RefImageNode;
import org.pegdown.ast.RefLinkNode;
import org.pegdown.ast.ReferenceNode;
import org.pegdown.ast.RootNode;
import org.pegdown.ast.SimpleNode;
import org.pegdown.ast.SpecialTextNode;
import org.pegdown.ast.StrongNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TableBodyNode;
import org.pegdown.ast.TableCellNode;
import org.pegdown.ast.TableColumnNode;
import org.pegdown.ast.TableHeaderNode;
import org.pegdown.ast.TableNode;
import org.pegdown.ast.TableRowNode;
import org.pegdown.ast.TextNode;
import org.pegdown.ast.VerbatimNode;
import org.pegdown.ast.Visitor;
import org.pegdown.ast.WikiLinkNode;

public abstract class AbstractVisitor implements Visitor {

	public static Logger _logger=Logger.getLogger(AbstractVisitor.class.getName());
//	protected final Map<String, ReferenceNode> references = new HashMap<String, ReferenceNode>();
//	protected final Map<String, String> abbreviations = new HashMap<String, String>();

	protected TableNode currentTableNode;
	protected int currentTableColumn;
	protected boolean inTableHeader;

	public AbstractVisitor() {
	}

	protected void process(RootNode astRoot) {
		checkArgNotNull(astRoot, "astRoot");
		astRoot.accept(this);
	}

	public void visit(RootNode node) {
		visitChildren(node);
	}

	public void visit(AbbreviationNode node) {
	}

	public void visit(AutoLinkNode node) {
	}

	public void visit(BlockQuoteNode node) {
	}

	public void visit(BulletListNode node) {
		visitChildren(node);
	}

	public void visit(CodeNode node) {
	}

	public void visit(DefinitionListNode node) {
	}

	public void visit(DefinitionNode node) {
	}

	public void visit(DefinitionTermNode node) {
	}

	public void visit(EmphNode node) {
	}

	public void visit(ExpImageNode node) {
	}

	public void visit(ExpLinkNode node) {
	}

	public void visit(HeaderNode node) {
	}

	public void visit(HtmlBlockNode node) {
	}

	public void visit(InlineHtmlNode node) {
	}

	public void visit(ListItemNode node) {
		visitChildren(node);
	}

	public void visit(MailLinkNode node) {
	}

	public void visit(OrderedListNode node) {
		visitChildren(node);
	}

	public void visit(ParaNode node) {
		visitChildren(node);
	}

	public void visit(QuotedNode node) {
		visitChildren(node);
	}

	public void visit(ReferenceNode node) {
		// reference nodes are not printed
	}

	public void visit(RefImageNode node) {
	}

	public void visit(RefLinkNode node) {
	}

	public void visit(SimpleNode node) {
	}

	public void visit(StrongNode node) {
	}

	public void visit(TableBodyNode node) {
	}

	public void visit(TableCellNode node) {
		TableColumnNode column = currentTableNode.getColumns().get(currentTableColumn);
		column.accept(this);
		visitChildren(node);
		currentTableColumn += node.getColSpan();
	}

	public void visit(TableColumnNode node) {
	}

	public void visit(TableHeaderNode node) {
	}

	public void visit(TableNode node) {
	}

	public void visit(TableRowNode node) {
		currentTableColumn = 0;
	}

	public void visit(VerbatimNode node) {
	}

	public void visit(WikiLinkNode node) {
	}

	public void visit(TextNode node) {
	}

	public void visit(SpecialTextNode node) {
	}

	public void visit(SuperNode node) {
		visitChildren(node);
	}

	public void visit(TripleNode node) {
		
	}
	
	public void visit(TripleContextNode node) {
		
	}
	
	public void visit(Node node) {
		if (node instanceof TripleNode) {
			visit((TripleNode) node);
			return;
		}
		if (node instanceof TripleContextNode) {
			visit((TripleContextNode) node);
			return;
		}
		// override this method for processing custom Node implementations
//		throw new RuntimeException("Not implemented");
		_logger.warning("Unknown: "+node);
	}

	// helpers

	protected void visitChildren(SuperNode node) {
		for (Node child : node.getChildren()) {
			child.accept(this);
		}
	}

}
