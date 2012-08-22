package de.flapdoodle.drug.wiki.parser;

import org.pegdown.ast.TextNode;
import org.pegdown.ast.Visitor;


public class TNode extends TextNode {

  public TNode(char type, String text) {
      super(text);
  }

  @Override
  public void accept(Visitor visitor) {
      visitor.visit(this);
  }
}