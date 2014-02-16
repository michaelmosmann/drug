/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.drug.persistence.neo4j;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.neo4j.graphdb.Direction;
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
		if (node!=null) {
			DescriptionDto ret = new DescriptionDto();
			ret.setId(new ReferenceDto<DescriptionDto>(DescriptionDto.class, getProperty(node, "id",String.class)));
			ret.setName(getProperty(node, "name", String.class));
			ret.setObject(isObject(node));
			ret.setText(getProperty(node, "text", String.class));
			ret.setVersion(getProperty(node, "version", String.class));
			ret.setOtherNames(otherNamesAsSet(node));
			return ret;
		}
		return null;
	}

	static void apply(DescriptionDto dto, Node node, GraphDatabaseService graphDb) {
		node.addLabel(MappedTypes.Description);
		if (dto.getId()!=null) node.setProperty("id", dto.getId().getId());
		node.setProperty("name", dto.getName());
		//node.setProperty("isObject", dto.isObject());
		node.setProperty("text", dto.getText());
		if (dto.getVersion()!=null) node.setProperty("version", dto.getVersion());
		
		otherNames(dto.getOtherNames(), node, graphDb);
		object(dto.isObject(),node,graphDb);
	}
	
	static void verifyVersion(DescriptionDto descriptionDto, Node node) {
		String dbVersion = getProperty(node,"version", String.class);
		if (!dbVersion.equals(descriptionDto.getVersion())) {
			throw new IllegalArgumentException("versions are different: stored="+dbVersion+" requested: "+descriptionDto.getVersion());
		}
	}
	
	static void newId(Node node) {
		node.setProperty("id", UUID.randomUUID().toString());
	}
	
	static void newVersion(Node node) {
		node.setProperty("version", UUID.randomUUID().toString());
	}
	
	private static boolean isObject(Node node) {
		Relationship isObjectRelationShip = node.getSingleRelationship(Relationsships.IsObject, Direction.OUTGOING);
		return isObjectRelationShip!=null && "object".equals(getProperty(isObjectRelationShip.getEndNode(), "type", String.class));
	}
	
	private static void object(boolean isObject, Node node, GraphDatabaseService graphDb) {
		if (isObject) {
			if (node.getSingleRelationship(Relationsships.IsObject, Direction.OUTGOING)==null) {
				Node isObjectNode = oneAndOnlyOne(graphDb.findNodesByLabelAndProperty(MappedTypes.Relation, "type", "object"));
				if (isObjectNode==null) {
					isObjectNode=graphDb.createNode(MappedTypes.Relation);
					isObjectNode.setProperty("type", "object");
				}
				node.createRelationshipTo(isObjectNode, Relationsships.IsObject);
			}
		} else {
			Relationship isObjectRelationShip = node.getSingleRelationship(Relationsships.IsObject, Direction.OUTGOING);
			if (isObjectRelationShip!=null) {
				isObjectRelationShip.delete();
			}
		}
	}

	private static Set<String> otherNamesAsSet(Node node) {
		Iterable<Relationship> relationships = node.getRelationships(Relationsships.HasOtherName);
		Set<String> otherNames = Sets.newHashSet();
		for (Relationship relationship : relationships) {
			otherNames.add(getProperty(relationship.getEndNode(),"name",String.class));
		}
		return otherNames;
	}
	
	private static void otherNames(Set<String> otherNames, Node node, GraphDatabaseService graphDb) {
		Set<String> old=otherNamesAsSet(node);
		Set<String> newRelations=Sets.difference(otherNames, old);
		Set<String> removeThese=Sets.difference(old, otherNames);

		for (Relationship relation : node.getRelationships(Relationsships.HasOtherName)) {
			if (removeThese.contains(getProperty(relation.getEndNode(),"name",String.class))) {
				relation.delete();
			}
		}
		
		for (String otherName : newRelations) {
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
	
	static long asLong(ReferenceDto<DescriptionDto> id) {
		return Long.valueOf(id.getId());
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
