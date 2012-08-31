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

import static org.parboiled.common.Preconditions.checkArgNotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.pegdown.ast.RootNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.WikiLinkNode;

import de.flapdoodle.drug.logging.Loggers;
import de.flapdoodle.drug.markup.IMarkupVisitor;
import de.flapdoodle.drug.markup.IRelation;
import de.flapdoodle.drug.markup.Label;

public class TripleMarkdownMarkupVisitorAdapter extends AbstractPrintingVisitor {
	
	private static final Logger _logger = Loggers.getLogger(TripleMarkdownMarkupVisitorAdapter.class);
	
	private IMarkupVisitor _markupVisitor;
	private TripleNodeRelationMap _relations;
//	private TripleReferenceVisitor _refVisitor;

	public TripleMarkdownMarkupVisitorAdapter() {
	}

	public void toHtml(RootNode astRoot,TripleNodeRelationMap relations, IMarkupVisitor markupVisitor) {
		checkArgNotNull(astRoot, "astRoot");
		checkArgNotNull(markupVisitor, "markupVisitor");
		checkArgNotNull(relations, "relations");
		_markupVisitor = markupVisitor;
		_relations = relations;
		_markupVisitor.begin();
		astRoot.accept(this);
		_markupVisitor.text(printer.getString());
		_markupVisitor.end();
//		return printer.getString();
	}

	public void visit(WikiLinkNode node) {
//		printLink(defaultLinkRenderer.render(node));
		_markupVisitor.text(printer.getString());
    _markupVisitor.reference(new Label(node.getText()));
    printer.clear();
	}

	public void visit(TripleNode node) {
		_markupVisitor.text(printer.getString());
		
		if (!node.isHidden()) {
			IRelation relation = _relations.get(node);
			if (relation!=null) {
				String base=node.getBase();
				if (base==null) base=node.getText();
				Label label=new Label(base,node.getText());
				switch(node.getType()) {
					case Subject:
						_markupVisitor.subject(label, relation);
						break;
					case Predicate:
						_markupVisitor.predicate(label, relation);
						break;
					case Object:
						_markupVisitor.object(label, relation);
						break;
				}
			} else {
	//			new Exception("Node: "+node.toString()).printStackTrace();
				_markupVisitor.text("!!!"+node.toString()+"!!!");
			}
		}
//		printer.print(node.getText());
		printer.clear();
	}

	public void visit(TripleContextNode node) {
		_markupVisitor.text(printer.getString());
		
		if (!node.isHidden()) {
			IRelation relation = _relations.get(node);
			if (relation!=null) {
				String base=node.getBase();
				if (base==null) base=node.getText();
				Label label=new Label(base,node.getText());
				_markupVisitor.context(label, node.getType(), relation);
			} else {
				_markupVisitor.text("!!!"+node.toString()+"!!!");
			}
		}
		printer.clear();
	}
	
	@Override
	protected void visitChildren(SuperNode node) {
		_markupVisitor.text(printer.getString());
		printer.clear();
		
		_markupVisitor.blockStart(typeAsName(node));
		
		super.visitChildren(node);
		
		_markupVisitor.text(printer.getString());
		printer.clear();
		
		_markupVisitor.blockEnd(typeAsName(node));
	}
	
	static String typeAsName(SuperNode node) {
		return node.getClass().getSimpleName();
	}
	
	
	// helpers

}
