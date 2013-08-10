package de.flapdoodle.drug;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.test.TestGraphDatabaseFactory;

import com.google.common.collect.Maps;

import de.flapdoodle.drug.logging.Loggers;
import de.flapdoodle.drug.webapp.bootstrap.DrugBootstrap;


public class AbstractNeo4jTest {

	private static final Logger _logger = Loggers.getLogger(AbstractNeo4jTest.class);
	
	private GraphDatabaseService _graphDb;

	@Before
	public void setUp() throws Exception {
		
		_logger.info("Start neo4j db");
		
		Map<String, String> config = Maps.newHashMap();
		config.put( "neostore.nodestore.db.mapped_memory", "10M" );
		config.put( "string_block_size", "60" );
		config.put( "array_block_size", "300" );
		
		_graphDb = new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder()
			//.newEmbeddedDatabaseBuilder(neo4jTempDir())
			.setConfig(config)
			.newGraphDatabase();
		
		_logger.info("Start neo4j db - done");
	}

//	private String neo4jTempDir() throws IOException {
//		return Files.createTempDirectory("neo4j").toAbsolutePath().toString();
//	}
	
	@After
	public void tearDown() {
		_logger.info("Shutdown neo4j db");
		_graphDb.shutdown();
		_logger.info("Shutdown neo4j db - done");
	}
	
	public GraphDatabaseService getGraphDb() {
		return _graphDb;
	}
}
