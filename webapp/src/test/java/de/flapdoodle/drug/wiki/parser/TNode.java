package de.flapdoodle.drug.wiki.parser;

import org.pegdown.ast.TextNode;
import org.pegdown.ast.Visitor;


public class TNode extends TextNode {

  public TNode(char type, Integer index, String text) {
      super(text+"("+type+":"+index+")");
  }

  @Override
  public void accept(Visitor visitor) {
      visitor.visit(this);
  }
}