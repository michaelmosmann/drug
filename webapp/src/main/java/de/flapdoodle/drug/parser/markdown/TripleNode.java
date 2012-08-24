package de.flapdoodle.drug.parser.markdown;

import java.util.List;

import org.parboiled.common.ImmutableList;
import org.pegdown.ast.AbstractNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.TextNode;
import org.pegdown.ast.Visitor;

import de.flapdoodle.drug.markup.Type;

public class TripleNode extends AbstractNode {

	private final Type _type;
	private final int _index;
	private final String _text;
	private final String _base;

	public TripleNode(char type, Integer index, String text, String base) {
		_type=Type.fromChar(type);
		if (_type==null) throw new IllegalArgumentException("Unknown Type " + type);
		_index = index != null
				? index
				: -1;
		_text = text;
		_base = base;
		
		System.out.println(this);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "[" + _type.asChar() + (_index!=-1 ? _index : "") + ":"  + _text+(_base!=null? "->"+_base : "")+"]";
	}

	@Override
	public List<Node> getChildren() {
		return ImmutableList.of();
	}

	public String getText() {
		return _text;
	}

	public String getBase() {
		return _base;
	}

	public Type getType() {
		return _type;
	}

	public int getIndex() {
		return _index;
	}
}
