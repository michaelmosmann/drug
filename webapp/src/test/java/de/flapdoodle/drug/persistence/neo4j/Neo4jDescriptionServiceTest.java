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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

import de.flapdoodle.drug.AbstractNeo4jTest;
import de.flapdoodle.drug.persistence.service.DescriptionDto;


public class Neo4jDescriptionServiceTest extends AbstractNeo4jTest {

	private Neo4jDescriptionService _service;

	@Before
	public void setup() {
		_service = new Neo4jDescriptionService(getGraphDb());
	}
	
	@Test
	public void savedInstanceMustHaveAnIDAfterThat() {
		DescriptionDto source=new DescriptionDto();
		source.setName("foo");
		source.setObject(false);
		source.setText("fooo text");
		source.setOtherNames(Sets.newHashSet("flup","bar"));
		
		DescriptionDto saved = _service.save(source);
		
		assertNotNull(saved);
		assertNotNull(saved.getId());
		assertNotNull(saved.getVersion());
		
		assertEquals("foo",saved.getName());
		assertEquals("fooo text",saved.getText());
		assertEquals(false,saved.isObject());
		assertEquals(Sets.newHashSet("flup","bar"),saved.getOtherNames());
	}
	
	@Test
	public void updateInstanceMustHaveANewVersion() {
		DescriptionDto source=new DescriptionDto();
		source.setName("foo");
		source.setObject(false);
		source.setText("fooo text");
		source.setOtherNames(Sets.newHashSet("flup","bar"));
		
		DescriptionDto saved = _service.save(source);
		
		assertNotNull(saved);
		assertNotNull(saved.getId());
		assertNotNull(saved.getVersion());
		
		assertEquals("foo",saved.getName());
		assertEquals("fooo text",saved.getText());
		assertEquals(false,saved.isObject());
		assertEquals(Sets.newHashSet("flup","bar"),saved.getOtherNames());
		
		saved.setName("blur");
		saved.setObject(true);
		saved.setText("blur text");
		saved.setOtherNames(Sets.newHashSet("song","fun"));
		
		DescriptionDto updated = _service.update(saved);

		assertNotNull(updated);
		assertNotNull(updated.getId());
		assertTrue(saved.getId().equals(updated.getId()));
		assertNotNull(updated.getVersion());
		assertTrue(!saved.getVersion().equals(updated.getVersion()));
		
		assertEquals("blur",updated.getName());
		assertEquals("blur text",updated.getText());
		assertEquals(true,updated.isObject());
		assertEquals(Sets.newHashSet("song","fun"),updated.getOtherNames());
	}
	
	@Test
	public void findDescriptionByName() {
		DescriptionDto source=new DescriptionDto();
		source.setName("foo");
		source.setObject(false);
		source.setText("fooo text");
		source.setOtherNames(Sets.newHashSet("flup","bar"));
		DescriptionDto saved = _service.save(source);
		assertNotNull(saved);
		
		DescriptionDto descriptionDto = _service.getByName("foo");
		assertNotNull(descriptionDto);
		assertEquals(Sets.newHashSet("flup","bar"), descriptionDto.getOtherNames());
	}

}
