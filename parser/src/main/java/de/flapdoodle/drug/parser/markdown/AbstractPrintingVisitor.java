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
package de.flapdoodle.drug.parser.markdown;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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


public class AbstractPrintingVisitor extends AbstractVisitor {

	protected final Printer printer = new CustomPrinter();
	protected final Map<String, String> abbreviations = new HashMap<String, String>();
	protected final Map<String, ReferenceNode> references = new HashMap<String, ReferenceNode>();
	protected final LinkRenderer defaultLinkRenderer = new LinkRenderer();
	protected TableNode currentTableNode;
	protected int currentTableColumn;
	protected boolean inTableHeader;

	public void visit(RootNode node) {
		// refactor so that NOT this printer is used!!
		for (ReferenceNode refNode : node.getReferences()) {
			AbstractPrintingVisitor local = new AbstractPrintingVisitor();
			local.visitChildren(refNode);
			references.put(normalize(local.printer.getString()), refNode);
//			printer.clear();
		}
		// refactor so that NOT this printer is used!!
		for (AbbreviationNode abbrNode : node.getAbbreviations()) {
			AbstractPrintingVisitor local = new AbstractPrintingVisitor();
			local.visitChildren(abbrNode);
			String abbr = local.printer.getString();
			local.printer.clear();
			abbrNode.getExpansion().accept(local);
			String expansion = local.printer.getString();
			abbreviations.put(abbr, expansion);
//			printer.clear();
		}
		visitChildren(node);
	}

	@Override
	protected void visitChildren(SuperNode node) {		
		for (Node child : node.getChildren()) {
			child.accept(this);
		}
	}

	protected void printTag(TextNode node, String tag) {
		printer.print('<').print(tag).print('>');
		printer.printEncoded(node.getText());
		printer.print('<').print('/').print(tag).print('>');
	}

	protected void printTag(SuperNode node, String tag) {
		printer.print('<').print(tag).print('>');
		visitChildren(node);
		printer.print('<').print('/').print(tag).print('>');
	}

	protected void printIndentedTag(SuperNode node, String tag) {
		printer.println().print('<').print(tag).print('>').indent(+2);
		visitChildren(node);
		printer.indent(-2).println().print('<').print('/').print(tag).print('>');
	}

	protected void printImageTag(SuperNode imageNode, String url) {
		printer.print("<img src=\"").print(url).print("\"  alt=\"").printEncoded(printChildrenToString(imageNode)).print(
				"\"/>");
	}

	protected void printLink(LinkRenderer.Rendering rendering) {
		printer.print('<').print('a');
		printAttribute("href", rendering.href);
		for (LinkRenderer.Attribute attr : rendering.attributes) {
			printAttribute(attr.name, attr.value);
		}
		printer.print('>').print(rendering.text).print("</a>");
	}

	private void printAttribute(String name, String value) {
		printer.print(' ').print(name).print('=').print('"').print(value).print('"');
	}

	protected String printChildrenToString(SuperNode node) {
//		Printer priorPrinter = printer;
//		printer = new Printer();
//		visitChildren(node);
//		String result = printer.getString();
//		printer = priorPrinter;
//		return result;
		AbstractPrintingVisitor local=new AbstractPrintingVisitor();
		local.abbreviations.putAll(abbreviations);
		local.visitChildren(node);
		return local.printer.getString();
	}

	protected String normalize(String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			switch (c) {
				case ' ':
				case '\n':
				case '\t':
					continue;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	protected void printWithAbbreviations(String string) {
		Map<Integer, Map.Entry<String, String>> expansions = null;
	
		for (Map.Entry<String, String> entry : abbreviations.entrySet()) {
			// first check, whether we have a legal match
			String abbr = entry.getKey();
	
			int ix = 0;
			while (true) {
				int sx = string.indexOf(abbr, ix);
				if (sx == -1)
					break;
	
				// only allow whole word matches
				ix = sx + abbr.length();
	
				if (sx > 0 && Character.isLetterOrDigit(string.charAt(sx - 1)))
					continue;
				if (ix < string.length() && Character.isLetterOrDigit(string.charAt(ix))) {
					continue;
				}
	
				// ok, legal match so save an expansions "task" for all matches
				if (expansions == null) {
					expansions = new TreeMap<Integer, Map.Entry<String, String>>();
				}
				expansions.put(sx, entry);
			}
		}
	
		if (expansions != null) {
			int ix = 0;
			for (Map.Entry<Integer, Map.Entry<String, String>> entry : expansions.entrySet()) {
				int sx = entry.getKey();
				String abbr = entry.getValue().getKey();
				String expansion = entry.getValue().getValue();
	
				printer.printEncoded(string.substring(ix, sx));
				printer.print("<abbr");
				if (StringUtils.isNotEmpty(expansion)) {
					printer.print(" title=\"");
					printer.printEncoded(expansion);
					printer.print('"');
				}
				printer.print('>');
				printer.printEncoded(abbr);
				printer.print("</abbr>");
				ix = sx + abbr.length();
			}
			printer.print(string.substring(ix));
		} else {
			printer.print(string);
		}
	}

	public void visit(AbbreviationNode node) {
	}

	public void visit(AutoLinkNode node) {
		printLink(defaultLinkRenderer.render(node));
	}

	public void visit(BlockQuoteNode node) {
		printIndentedTag(node, "blockquote");
	}

	public void visit(BulletListNode node) {
		printIndentedTag(node, "ul");
	}

	public void visit(CodeNode node) {
		printTag(node, "code");
	}

	public void visit(DefinitionListNode node) {
		printIndentedTag(node, "dl");
	}

	public void visit(DefinitionNode node) {
		printTag(node, "dd");
	}

	public void visit(DefinitionTermNode node) {
		printTag(node, "dt");
	}

	public void visit(EmphNode node) {
		printTag(node, "em");
	}

	public void visit(ExpImageNode node) {
		printImageTag(node, node.url);
	}

	public void visit(ExpLinkNode node) {
		String text = printChildrenToString(node);
		printLink(defaultLinkRenderer.render(node, text));
	}

	public void visit(HeaderNode node) {
		printTag(node, "h" + node.getLevel());
	}

	public void visit(HtmlBlockNode node) {
		String text = node.getText();
		if (text.length() > 0)
			printer.println();
		printer.print(text);
	}

	public void visit(InlineHtmlNode node) {
		printer.print(node.getText());
	}

	public void visit(ListItemNode node) {
		printer.println();
		printTag(node, "li");
	}

	public void visit(MailLinkNode node) {
		printLink(defaultLinkRenderer.render(node));
	}

	public void visit(OrderedListNode node) {
		printIndentedTag(node, "ol");
	}

	public void visit(ParaNode node) {
		printTag(node, "p");
	}

	public void visit(QuotedNode node) {
		switch (node.getType()) {
			case DoubleAngle:
				printer.print("&laquo;");
				visitChildren(node);
				printer.print("&raquo;");
				break;
			case Double:
				printer.print("&ldquo;");
				visitChildren(node);
				printer.print("&rdquo;");
				break;
			case Single:
				printer.print("&lsquo;");
				visitChildren(node);
				printer.print("&rsquo;");
				break;
		}
	}

	public void visit(ReferenceNode node) {
		// reference nodes are not printed
	}

	public void visit(RefImageNode node) {
		String text = printChildrenToString(node);
		String key = node.referenceKey != null
				? printChildrenToString(node.referenceKey)
				: text;
		ReferenceNode refNode = references.get(normalize(key));
		if (refNode == null) { // "fake" reference image link
			printer.print("![").print(text).print(']');
			if (node.separatorSpace != null) {
				printer.print(node.separatorSpace).print('[');
				if (node.referenceKey != null)
					printer.print(key);
				printer.print(']');
			}
		} else
			printImageTag(node, refNode.getUrl());
	}

	public void visit(RefLinkNode node) {
		String text = printChildrenToString(node);
		String key = node.referenceKey != null
				? printChildrenToString(node.referenceKey)
				: text;
		ReferenceNode refNode = references.get(normalize(key));
		if (refNode == null) { // "fake" reference link
			printer.print('[').print(text).print(']');
			if (node.separatorSpace != null) {
				printer.print(node.separatorSpace).print('[');
				if (node.referenceKey != null)
					printer.print(key);
				printer.print(']');
			}
		} else {
			printLink(defaultLinkRenderer.render(node, refNode.getUrl(), refNode.getTitle(), text));
		}
	}

	public void visit(SimpleNode node) {
		switch (node.getType()) {
			case Apostrophe:
				printer.print("&rsquo;");
				break;
			case Ellipsis:
				printer.print("&hellip;");
				break;
			case Emdash:
				printer.print("&mdash;");
				break;
			case Endash:
				printer.print("&ndash;");
				break;
			case HRule:
				printer.println().print("<hr/>");
				break;
			case Linebreak:
				printer.print("<br/>");
				break;
			case Nbsp:
				printer.print("&nbsp;");
				break;
			default:
				throw new IllegalStateException();
		}
	}

	public void visit(StrongNode node) {
		printTag(node, "strong");
	}

	public void visit(TableBodyNode node) {
		printIndentedTag(node, "tbody");
	}

	public void visit(TableCellNode node) {
		String tag = inTableHeader
				? "th"
				: "td";
		TableColumnNode column = currentTableNode.getColumns().get(currentTableColumn);
	
		printer.println().print('<').print(tag);
		column.accept(this);
		if (node.getColSpan() > 1)
			printer.print(" colspan=\"").print(Integer.toString(node.getColSpan())).print('"');
		printer.print('>');
		visitChildren(node);
		printer.print('<').print('/').print(tag).print('>');
	
		currentTableColumn += node.getColSpan();
	}

	public void visit(TableColumnNode node) {
		switch (node.getAlignment()) {
			case None:
				break;
			case Left:
				printer.print(" align=\"left\"");
				break;
			case Right:
				printer.print(" align=\"right\"");
				break;
			case Center:
				printer.print(" align=\"center\"");
				break;
			default:
				throw new IllegalStateException();
		}
	}

	public void visit(TableHeaderNode node) {
		inTableHeader = true;
		printIndentedTag(node, "thead");
		inTableHeader = false;
	}

	public void visit(TableNode node) {
		currentTableNode = node;
		printIndentedTag(node, "table");
		currentTableNode = null;
	}

	public void visit(TableRowNode node) {
		currentTableColumn = 0;
		printIndentedTag(node, "tr");
	}

	public void visit(VerbatimNode node) {
		printer.println().print("<pre><code>");
		String text = node.getText();
		// print HTML breaks for all initial newlines
		while (text.charAt(0) == '\n') {
			printer.print("<br/>");
			text = text.substring(1);
		}
		printer.printEncoded(text);
		printer.print("</code></pre>");
	}

	public void visit(TextNode node) {
		if (abbreviations.isEmpty()) {
			printer.print(node.getText());
		} else {
			printWithAbbreviations(node.getText());
		}
	}

	@Override
	public void visit(SpecialTextNode node) {
		printer.printEncoded(node.getText());
	}

	@Override
	public void visit(SuperNode node) {
		visitChildren(node);
	}


	static class CustomPrinter extends Printer {

		boolean notUnunsed=false;
		
		@Override
		public Printer println() {
      if (notUnunsed || (sb.length() > 0)) print('\n');
      for (int i = 0; i < indent; i++) print(' ');
      return this;
		}
		
		@Override
		public Printer clear() {
			notUnunsed=true;
			return super.clear();
		}
	}
}
