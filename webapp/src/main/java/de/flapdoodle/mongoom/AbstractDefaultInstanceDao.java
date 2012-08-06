package de.flapdoodle.mongoom;

public class AbstractDefaultInstanceDao<T extends IEntity<T>> extends AbstractDao<T> {

	public AbstractDefaultInstanceDao(Class<T> entityClass) {
		super(entityClass);
	}

	public T getNew() {
		try
		{
			return _entityClazz.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
	
}
