package de.flapdoodle.drug.persistence.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.AbstractModule;

import de.flapdoodle.mongoom.datastore.Datastore;
import de.flapdoodle.mongoom.logging.LogConfig;

public class Logging extends AbstractModule
{
	private static final Logger _logger = LogConfig.getLogger(Logging.class);
	
	@Override
	protected void configure()
	{
		_logger.severe("Logging");
		
		Logger.getLogger("de.flapdoodle").setLevel(Level.WARNING);
		_logger.info("Info hidden");
		
		Logger.getLogger("de.flapdoodle").setLevel(Level.ALL);
		Logger.getLogger(Datastore.class.getName()).setLevel(Level.FINEST);
		Logger.getLogger("com.google").setLevel(Level.ALL);
		_logger.info("Info done");
	}

}
