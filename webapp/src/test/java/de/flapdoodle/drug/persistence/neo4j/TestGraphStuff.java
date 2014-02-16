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

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

import de.flapdoodle.drug.AbstractNeo4jTest;

public class TestGraphStuff extends AbstractNeo4jTest {

	enum RelTypes implements RelationshipType {
		KNOWS
	}

	@Test
	public void someStuff() {
		GraphDatabaseService graphDb = getGraphDb();

		Transaction tx = graphDb.beginTx();
		try {

			Node firstNode = graphDb.createNode();
			firstNode.setProperty("message", "Hello, ");
			Node secondNode = graphDb.createNode();
			secondNode.setProperty("message", "World!");

			Relationship relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
			relationship.setProperty("message", "brave Neo4j ");

			tx.success();

			System.out.print(firstNode.getProperty("message"));
			System.out.print(relationship.getProperty("message"));
			System.out.print(secondNode.getProperty("message"));
			
		} catch (Exception ex) {
			tx.failure();
		} finally {
			tx.finish();
		}

	}
}
