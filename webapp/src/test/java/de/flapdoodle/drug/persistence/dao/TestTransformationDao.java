package de.flapdoodle.drug.persistence.dao;

import java.util.List;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

import de.flapdoodle.drug.AbstractEmbedMongoTest;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.beans.Transformation;

public class TestTransformationDao extends AbstractEmbedMongoTest {

	@Inject
	TransformationDao _transformationDao;

	@Inject
	DescriptionDao _descriptionDao;

	@Inject
	SearchDao _searchDao;
	
	public void testFindSomething() {

		fillWithData();

		SearchDao searchDao = _searchDao;//new SearchDao(_descriptionDao, _transformationDao);
		
		List<Transformation> list=searchDao.find("Du", "futtern", "Brot",null,null);
		assertEquals("Size",1, list.size());
		list=searchDao.find("Ich", "futtern", "Brot",null,null);
		assertEquals("Size",1, list.size());
		list=searchDao.find(null, "futtern", "Brot",null,null);
		assertEquals("Size",1, list.size());
		list=searchDao.find("Ich", null, "Brot",null,null);
		assertEquals("Size",1, list.size());
		list=searchDao.find("Frosch", "löffeln", null,null,null);
		assertEquals("Size",2, list.size());

	}

	private void fillWithData() {
		Description frosch = new Description();
		frosch.setName("Frosch");
		frosch.setOtherNames(Sets.newHashSet("Quack", "Frog"));
		_descriptionDao.save(frosch);
		
		Description mensch = new Description();
		mensch.setName("Mensch");
		mensch.setOtherNames(Sets.newHashSet("Ich", "Du"));
		_descriptionDao.save(mensch);

		Description loeffeln = new Description();
		loeffeln.setName("löffeln");
		loeffeln.setOtherNames(Sets.newHashSet("schaufeln"));
		loeffeln.setObject(false);
		_descriptionDao.save(loeffeln);
		
		Description essen = new Description();
		essen.setName("essen");
		essen.setOtherNames(Sets.newHashSet("futtern"));
		essen.setObject(false);
		_descriptionDao.save(essen);

		Description suppe = new Description();
		suppe.setName("Suppe");
		suppe.setOtherNames(Sets.newHashSet("Plörre"));
		_descriptionDao.save(suppe);

		Description brot = new Description();
		brot.setName("Brot");
		brot.setOtherNames(Sets.newHashSet("Bemme"));
		_descriptionDao.save(brot);

		{
			Transformation t = new Transformation();
			t.setSubject(mensch.getId());
			t.setPredicate(essen.getId());
			t.setObject(brot.getId());
			_transformationDao.save(t);
		}

		{
			Transformation t = new Transformation();
			t.setSubject(mensch.getId());
			t.setPredicate(loeffeln.getId());
			t.setObject(suppe.getId());
			_transformationDao.save(t);
		}
		
		{
			Transformation t = new Transformation();
			t.setSubject(frosch.getId());
			t.setPredicate(loeffeln.getId());
			t.setObject(suppe.getId());
			_transformationDao.save(t);
		}
		
		{
			Transformation t = new Transformation();
			t.setSubject(frosch.getId());
			t.setPredicate(loeffeln.getId());
			t.setObject(brot.getId());
			_transformationDao.save(t);
		}
	}

}
