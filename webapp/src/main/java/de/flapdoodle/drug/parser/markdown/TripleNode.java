package de.flapdoodle.drug.parser.markdown;

import org.pegdown.ast.TextNode;
import org.pegdown.ast.Visitor;


public class TripleNode extends TextNode {

  public TripleNode(char type, Integer index, String text) {
      super(text+"("+type+":"+index+")");
  }

  @Override
  public void accept(Visitor visitor) {
      visitor.visit(this);
  }
}