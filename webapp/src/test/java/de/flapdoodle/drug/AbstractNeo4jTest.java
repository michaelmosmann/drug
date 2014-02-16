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
