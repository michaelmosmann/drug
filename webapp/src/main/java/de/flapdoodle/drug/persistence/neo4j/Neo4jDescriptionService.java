package de.flapdoodle.drug.persistence.neo4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import com.google.common.base.Preconditions;
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
		return NodeDtoMapper.asDescriptionDto(getNodeById(reference));
	}

	@Override
	public DescriptionDto getByStringId(String id) {
		return NodeDtoMapper.asDescriptionDto(getNodeByStringId(id));
	}

	@Override
	public DescriptionDto getByName(String name) {
		return NodeDtoMapper.asDescriptionDto(NodeDtoMapper.oneAndOnlyOne(_graphDb.findNodesByLabelAndProperty(MappedTypes.Description, "name", name)));
	}

	@Override
	public DescriptionDto save(DescriptionDto descriptionDto) {
		Preconditions.checkArgument(descriptionDto.getId() == null, "id is set");

		Transaction tx = _graphDb.beginTx();
		try {
			Node node = _graphDb.createNode();
			NodeDtoMapper.apply(descriptionDto, node,_graphDb);
			NodeDtoMapper.newId(node);
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
		Preconditions.checkNotNull(descriptionDto.getId());
		
		Transaction tx = _graphDb.beginTx();
		try {
			Node node = getNodeById(descriptionDto.getId());
			
			if (node != null) {
				NodeDtoMapper.verifyVersion(descriptionDto, node);
				NodeDtoMapper.apply(descriptionDto, node,_graphDb);
				NodeDtoMapper.newVersion(node);
				tx.success();
				return NodeDtoMapper.asDescriptionDto(node);

			} else {
				throw new RuntimeException("Could not find node with " + descriptionDto.getId());
			}

		} catch (RuntimeException ex) {
			tx.failure();
			throw ex;
		} finally {
			tx.finish();
		}
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



	private Node getNodeById(ReferenceDto<DescriptionDto> reference) {
		return getNodeByStringId(reference.getId());
	}

	private Node getNodeByStringId(String id) {
		return NodeDtoMapper.oneAndOnlyOne(_graphDb.findNodesByLabelAndProperty(MappedTypes.Description,"id",id));
	}

}
