package de.flapdoodle.drug.persistence.neo4j;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.ResourceIterator;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import de.flapdoodle.drug.persistence.service.DescriptionDto;
import de.flapdoodle.drug.persistence.service.ReferenceDto;


public class NodeDtoMapper {

	static DescriptionDto asDescriptionDto(Node node) {
		DescriptionDto ret = new DescriptionDto();
		ret.setId(new ReferenceDto<DescriptionDto>(DescriptionDto.class, ""+node.getId()));
		ret.setName(getProperty(node, "name", String.class));
		ret.setObject(getPropertyOrDefault(node,"isObject", true));
		ret.setText(getProperty(node, "text", String.class));
		ret.setVersion(getProperty(node, "version", String.class));
		Iterable<Relationship> relationships = node.getRelationships(Relationsships.HasOtherName);
		Set<String> otherNames = Sets.newHashSet();
		for (Relationship relationship : relationships) {
			otherNames.add(getProperty(relationship.getEndNode(),"name",String.class));
		}
		ret.setOtherNames(otherNames);
		return ret;
	}
	
	static void apply(DescriptionDto dto, Node node) {
		node.addLabel(MappedTypes.Description);
		node.setProperty("name", dto.getName());
		node.setProperty("isObject", dto.isObject());
		node.setProperty("text", dto.getText());
		if (dto.getVersion()!=null) node.setProperty("version", dto.getVersion());
		//node.setProperty("names", dto.getOtherNames());
	}
	
	static void newVersion(Node node) {
		node.setProperty("version", UUID.randomUUID().toString());
	}
	
	static void otherNames(Set<String> otherNames, Node node, GraphDatabaseService graphDb) {
		for (String otherName : otherNames) {
			Node nameNode=oneAndOnlyOne(graphDb.findNodesByLabelAndProperty(MappedTypes.Names, "name", otherName));
			if (nameNode==null) {
				nameNode=graphDb.createNode(MappedTypes.Names);
				nameNode.setProperty("name", otherName);
			}
			node.createRelationshipTo(nameNode, Relationsships.HasOtherName);
		}
	}
	
	static Node oneAndOnlyOne(ResourceIterable<Node> labelAndProperty) {
		Node node=null;
		
		ResourceIterator<Node> iterator = labelAndProperty.iterator();
		
		if (iterator.hasNext()) {
			node = iterator.next();
			if (iterator.hasNext()) {
				throw new IllegalArgumentException("more then one found for "+node.getLabels());
			}
		}
		
		return node;
	}
	
	private static <T> T getPropertyOrDefault(Node node, String key, T defaultValue) {
		Preconditions.checkNotNull(defaultValue,"defaultValue is null");
		Object property = node.getProperty(key, defaultValue);
		if (defaultValue.getClass().isInstance(property)) {
			return (T) property;
		}
		return defaultValue;
	}

	private static <T> T getProperty(Node node, String key, Class<T> type) {
		Preconditions.checkNotNull(type,"type is null");
		Object property = node.getProperty(key);
		if (type.isInstance(property)) {
			return (T) property;
		}
		throw new NotFoundException("property "+key+" not found in "+node);
	}



}
