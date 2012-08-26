package de.flapdoodle.drug.persistence.dao;

import java.util.List;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.mongoom.AbstractDao;

public class DescriptionDao extends AbstractDao<Description> {

	public DescriptionDao() {
		super(Description.class);
	}

	public List<Description> findByName(boolean isObject, String name) {
		return createQuery().field(Description.isObject).eq(isObject).or().field(Description.Name).eq(name).parent().or().listfield(
				Description.OtherNames).eq(name).parent().result().order("name", true).asList();
	}
	
	public List<Description> findByType(boolean isObject) {
		return createQuery().field(Description.isObject).eq(isObject).result().order("name", true).asList();
	}

	public List<Description> findAnyByName(String name) {
		return createQuery().or().field(Description.Name).eq(name).parent().or().listfield(Description.OtherNames).eq(name).parent().result().order(
				"name", true).asList();
	}

	public Description getByName(String name) {
		return createQuery().field(Description.Name).eq(name).result().order("name", true).get();
	}
}
