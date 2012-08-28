/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <${lic.email2}>
 *
 * with contributions from
 * 	${lic.developers}
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
package de.flapdoodle.mongoom;

import java.util.Iterator;
import java.util.logging.Logger;

import com.google.inject.Inject;

import de.flapdoodle.mongoom.types.Reference;

public class AbstractDao<T extends IEntity<T>>
{
	private static final Logger _logger = Logger.getLogger(AbstractDao.class.getName());

	protected Class<T> _entityClazz;

	@Inject
	protected IDatastore _datastore;

	public AbstractDao(Class<T> entityClass)
	{
		_entityClazz = entityClass;
	}

	protected IDatastore getDatastore()
	{
		return _datastore;
	}
	
	protected IEntityQuery<T> createQuery()
	{
		return _datastore.with(_entityClazz);
	}

	public Class<T> getEntityClass()
	{
		return _entityClazz;
	}

	public void update(T entity)
	{
		_datastore.update(entity);
	}
	
	public void save(T entity)
	{
		_datastore.save(entity);
	}
	
	public void insert(T entity)
	{
		_datastore.insert(entity);
	}
	
	public void delete(T entity)
	{
		_datastore.delete(entity);
	}
	
	public Iterator<T> find()
	{
		return _datastore.with(_entityClazz).result().iterator();
	}
	
	public T get(Reference<T> id)
	{
		return _datastore.with(_entityClazz).id().eq(id).result().get();
	}
	
	public IEntityQuery<T> query()
	{
		return _datastore.with(_entityClazz);
	}
}
