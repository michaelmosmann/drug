package de.flapdoodle.drug.persistence.neo4j;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Sets;

import de.flapdoodle.drug.AbstractNeo4jTest;
import de.flapdoodle.drug.persistence.service.DescriptionDto;


public class Neo4jDescriptionServiceTest extends AbstractNeo4jTest {

	@Test
	public void findDescriptionByName() {
		Neo4jDescriptionService service = new Neo4jDescriptionService(getGraphDb());
		
		DescriptionDto source=new DescriptionDto();
		source.setName("foo");
		source.setObject(false);
		source.setText("fooo text");
		source.setOtherNames(Sets.newHashSet("flup","bar"));
		DescriptionDto saved = service.save(source);
		assertNotNull(saved);
		
		DescriptionDto descriptionDto = service.getByName("foo");
		assertNotNull(descriptionDto);
		assertEquals(Sets.newHashSet("flup","bar"), descriptionDto.getOtherNames());
	}

}
