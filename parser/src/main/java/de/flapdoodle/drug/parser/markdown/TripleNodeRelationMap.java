package de.flapdoodle.drug.parser.markdown;

import java.util.Map;

import com.google.common.collect.Maps;

import de.flapdoodle.drug.markup.IRelation;


public class TripleNodeRelationMap {
	Map<TripleNode, IRelation> _relationMap=Maps.newHashMap();
	
	public void setFor(IRelation relation, TripleNode... nodes) {
		for (TripleNode n : nodes) {
			if (n!=null) _relationMap.put(n,relation);
		}
	}

	public IRelation get(TripleNode node) {
		return _relationMap.get(node);
	}
	
}
