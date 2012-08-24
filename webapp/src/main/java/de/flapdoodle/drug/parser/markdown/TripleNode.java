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

	public TripleNode(char type, Integer index, String text) {
		switch (type) {
			case 's':
				_type = Type.Subject;
				break;
			case 'p':
				_type = Type.Predicate;
				break;
			case 'o':
				_type = Type.Object;
				break;
			default:
				throw new IllegalArgumentException("Unknown Type " + type);
		}
		_index = index != null
				? index
				: -1;
		_text = text;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "" + _type + _index + ":" + _text;
	}

	@Override
	public List<Node> getChildren() {
		return ImmutableList.of();
	}

	public String getText() {
		return _text;
	}

	public Type getType() {
		return _type;
	}

	public int getIndex() {
		return _index;
	}
}
