package de.flapdoodle.drug.persistence.dao;

import java.util.List;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

import de.flapdoodle.drug.AbstractEmbedMongoTest;
import de.flapdoodle.drug.persistence.beans.Description;


public class TestDescriptionDao extends AbstractEmbedMongoTest {

	@Inject
	DescriptionDao _descriptionDao;
	
	public void testDescriptionKeywords() {
		Description entity = new Description();
		entity.setName("Kaffee");
		entity.setOtherNames(Sets.newHashSet("Coffee","Java"));
		_descriptionDao.save(entity);
		
		entity = new Description();
		entity.setName("Zonenkaffee");
		entity.setOtherNames(Sets.newHashSet("Coffee","Java","Rondo"));
		_descriptionDao.save(entity);
		
		List<Description> list = _descriptionDao.findByName(true,"Kaffee");
		assertEquals("size",1, list.size());
		assertEquals("item.name","Kaffee", list.get(0).getName());

		list = _descriptionDao.findByName(true,"Coffee");
		assertEquals("size",2, list.size());
	}
	
	public void testMan() {
		Description entity = new Description();
		entity.setName("Mensch");
		entity.setOtherNames(Sets.newHashSet("Man","ich","Du"));
		_descriptionDao.save(entity);
		
		List<Description> list = _descriptionDao.findAnyByName("Man");
		assertEquals("size",1, list.size());
	}
}
