package de.flapdoodle.drug.parser.markdown;

import java.util.Map;

import org.pegdown.ast.BlockQuoteNode;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.RootNode;

import com.google.common.collect.Maps;

public class TripleReferenceVisitor extends AbstractVisitor {

	Context _context;
	public void buildReferenceMap(RootNode root) {
		_context=new Context();
		super.process(root);
		_context=_context.closeContext();
	}

	@Override
	public void visit(BlockQuoteNode node) {
		System.out.println("before " + node);
		_context=_context.openContext();
		super.visit(node);
		_context=_context.closeContext();
		System.out.println("after " + node);
	}

	@Override
	public void visit(ParaNode node) {
		System.out.println("before " + node);
		_context=_context.openContext();
		super.visit(node);
		_context=_context.closeContext();
		System.out.println("after " + node);
	}

	@Override
	public void visit(TripleNode node) {
		super.visit(node);
		_context.get(node.getIndex()).set(node);
//		System.out.println(node);
	}

	static class Context {

		private Map<Integer, Triple> _tripleMap = Maps.newHashMap();
		private Context _previous;
		
		public Triple get(int index) {
			Triple ret = _tripleMap.get(index);
			if (ret==null) {
				ret=new Triple();
				_tripleMap.put(index, ret);
			}
			return ret;
		}
		
		public Context openContext() {
			Context ret = new Context();
			ret._previous=this;
			return ret;
		}
		
		public Context closeContext() {
			System.out.println("Mappings: "+_tripleMap);
			return _previous;
		}
	}

	static class Triple {

		private TripleNode _subject;
		private TripleNode _prec;
		private TripleNode _object;

		public TripleNode set(TripleNode node) {
			switch (node.getType()) {
				case Subject:
					_subject = isNotSet(_subject, node);
					return _subject;
				case Predicate:
					_prec = isNotSet(_prec, node);
					return _prec;
				case Object:
					_object = isNotSet(_object, node);
					return _object;
			}
			throw new IllegalArgumentException("UnknownNodeType " + node);
		}

		public TripleNode getSubject() {
			return _subject;
		}

		public TripleNode getPredicate() {
			return _prec;
		}

		public TripleNode getObject() {
			return _object;
		}

		@Override
		public String toString() {
			return ""+getText(_subject)+":"+getText(_prec)+":"+getText(_object);
		}
		
		private String getText(TripleNode val) {
			return val!=null ? val.getText() : "";
		}

		private TripleNode isNotSet(TripleNode org, TripleNode newValue) {
			if (org != null)
				return org;
			return newValue;
		}
	}
}
