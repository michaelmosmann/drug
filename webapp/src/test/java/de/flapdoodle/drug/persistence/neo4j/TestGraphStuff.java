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
