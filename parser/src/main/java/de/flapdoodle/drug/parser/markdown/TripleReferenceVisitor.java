package de.flapdoodle.drug.parser.markdown;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pegdown.ast.BlockQuoteNode;
import org.pegdown.ast.BulletListNode;
import org.pegdown.ast.ListItemNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.RootNode;

import com.google.common.collect.Maps;

import de.flapdoodle.drug.logging.Loggers;
import de.flapdoodle.drug.markup.IRelation;
import de.flapdoodle.drug.markup.Label;

public class TripleReferenceVisitor extends AbstractVisitor {

	private static final Logger _logger = Loggers.getLogger(TripleReferenceVisitor.class);
	private static Level _level=Level.FINE;
	
	Context _context;
	public TripleNodeRelationMap buildReferenceMap(RootNode root) {
		TripleNodeRelationMap relationMap=new TripleNodeRelationMap();
		_context=new Context(relationMap);
		super.process(root);
		_context=_context.closeContext();
		return relationMap;
	}

	@Override
	public void visit(BlockQuoteNode node) {
		before(node);
		_context=_context.openContext();
		super.visit(node);
		_context=_context.closeContext();
		after(node);
	}

	private void after(Node node) {
		if (_logger.isLoggable(_level)) _logger.log(_level,"after "+node);
	}

	private void before(Node node) {
		if (_logger.isLoggable(_level)) _logger.log(_level,"before "+node);
	}

	@Override
	public void visit(ParaNode node) {
		before(node);
		_context=_context.openContext();
		super.visit(node);
		_context=_context.closeContext();
		after(node);
	}

	@Override
	public void visit(ListItemNode node) {
		before(node);
		_context=_context.openContext();
		super.visit(node);
		_context=_context.closeContext();
		after(node);
	}
	
	@Override
	public void visit(TripleNode node) {
		super.visit(node);
		_context.get(node.getIndex()).set(node);
	}

	static class Context {

		private Map<Integer, Triple> _tripleMap = Maps.newHashMap();
		private Context _previous;
		private final TripleNodeRelationMap _relationMap;
		
		public Context(TripleNodeRelationMap relationMap) {
			_relationMap = relationMap;
		}

		public Triple get(int index) {
			Triple ret = _tripleMap.get(index);
			if (ret==null) {
				ret=new Triple();
				_tripleMap.put(index, ret);
			}
			return ret;
		}
		
		public Context openContext() {
			Context ret = new Context(_relationMap);
			ret._previous=this;
			return ret;
		}
		
		public Context closeContext() {
			for (Triple t : _tripleMap.values()) {
				TripleNode subject = t.getSubject();
				TripleNode predicate = t.getPredicate();
				TripleNode object = t.getObject();
				TripleAsRelation rel=new TripleAsRelation(t);
				_relationMap.setFor(rel,subject,predicate,object);
			}
			return _previous;
		}
	}
	
	static class TripleAsRelation implements IRelation {

		
		private final Triple _triple;

		public TripleAsRelation(Triple triple) {
			_triple = triple;
		}
		
		@Override
		public Label getSubject() {
			return asLabel(_triple.getSubject());
		}

		private Label asLabel(TripleNode tn) {
			if (tn!=null) {
				String base=tn.getBase();
				if (base==null) base=tn.getText();
				return new Label(base,tn.getText()); 
			}
			return null;
		}

		@Override
		public Label getPredicate() {
			return asLabel(_triple.getPredicate());
		}

		@Override
		public Label getObject() {
			return asLabel(_triple.getObject());
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
			return val!=null ? val.getBase()!=null ? val.getBase() : val.getText() : "";
		}

		private TripleNode isNotSet(TripleNode org, TripleNode newValue) {
			if (org != null)
				return org;
			return newValue;
		}
	}
}
