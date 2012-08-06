package de.flapdoodle.drug.persistence.config;

import com.google.inject.AbstractModule;

public class Persistence extends AbstractModule
{
	@Override
	protected void configure()
	{
		install(new Logging());
		install(new Server());
		install(new PersistentClasses());
		install(new Dao());
	}
}
