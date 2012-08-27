package de.flapdoodle.drug.parser.markdown;

import java.util.Map;

import com.google.common.collect.Maps;

import de.flapdoodle.drug.markup.IRelation;


public class TripleNodeRelationMap {
	Map<AbstractTripleNode, IRelation> _relationMap=Maps.newHashMap();
	
	public void setFor(IRelation relation, AbstractTripleNode... nodes) {
		for (AbstractTripleNode n : nodes) {
			if (n!=null) _relationMap.put(n,relation);
		}
	}

	public IRelation get(AbstractTripleNode node) {
		return _relationMap.get(node);
	}
	
}
