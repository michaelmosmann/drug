package de.flapdoodle.drug.parser.markdown;

import java.util.List;

import org.parboiled.common.ImmutableList;
import org.pegdown.ast.AbstractNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.Visitor;

import de.flapdoodle.drug.markup.ContextType;


public abstract class AbstractTripleNode extends AbstractNode {

	private final int _index;
	private final String _text;
	private final String _base;
	private final boolean _hidden;

	public AbstractTripleNode(boolean hidden, Integer index, String text) {
		_hidden = hidden;

		_index = index != null
				? index
				: -1;
		String base = null;
		int idx = text.indexOf("->");
		if (idx != -1) {
			base = text.substring(idx + 2);
			text = text.substring(0, idx);
		}
		_text = text;
		_base = base;
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

	public int getIndex() {
		return _index;
	}

	public boolean isHidden() {
		return _hidden;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	protected String toString(String type) {
		return "[" + (_hidden
				? "!"
				: "") + type + (_index != -1
				? _index
				: "") + ":" + _text + (_base != null
				? "->" + _base
				: "") + "]";
	}



}
