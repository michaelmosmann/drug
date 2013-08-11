package de.flapdoodle.drug.persistence.neo4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.IDescriptionService;
import de.flapdoodle.drug.persistence.service.ReferenceDto;


public class Neo4jDescriptionService implements IDescriptionService {

	private final GraphDatabaseService _graphDb;

	@Inject
	public Neo4jDescriptionService(GraphDatabaseService graphDb) {
		_graphDb = graphDb;
	}

	@Override
	public DescriptionDto get(ReferenceDto<DescriptionDto> reference) {
		throw new IllegalArgumentException("not implemented");
	}

	@Override
	public DescriptionDto getByStringId(String id) {
		throw new IllegalArgumentException("not implemented");
	}

	@Override
	public DescriptionDto getByName(String name) {
		Node node = NodeDtoMapper.oneAndOnlyOne(_graphDb.findNodesByLabelAndProperty(MappedTypes.Description, "name", name));
		
		if (node!=null) {
			return NodeDtoMapper.asDescriptionDto(node);
		}
		return null;
	}


	@Override
	public DescriptionDto save(DescriptionDto descriptionDto) {
		Transaction tx = _graphDb.beginTx();
		try {
			Node node = _graphDb.createNode();
			NodeDtoMapper.apply(descriptionDto, node);
			NodeDtoMapper.otherNames(descriptionDto.getOtherNames(), node,_graphDb);
			NodeDtoMapper.newVersion(node);
			tx.success();
			
			return NodeDtoMapper.asDescriptionDto(node);
		} catch (RuntimeException ex) {
			tx.failure();
			throw ex;
		} finally {
			tx.finish();
		}
	}

	@Override
	public DescriptionDto update(DescriptionDto descriptionDto) {
		throw new IllegalArgumentException("not implemented");
	}

	@Override
	public List<DescriptionDto> findByName(boolean isObject, String name) {
		throw new IllegalArgumentException("not implemented");
	}

	@Override
	public List<DescriptionDto> findByType(boolean isObject) {
		throw new IllegalArgumentException("not implemented");
	}

	@Override
	public List<DescriptionDto> findAnyByName(String name) {
		throw new IllegalArgumentException("not implemented");
	}

	@Override
	public Map<ReferenceDto<DescriptionDto>, String> names(Set<ReferenceDto<DescriptionDto>> idList) {
		throw new IllegalArgumentException("not implemented");
	}

}
