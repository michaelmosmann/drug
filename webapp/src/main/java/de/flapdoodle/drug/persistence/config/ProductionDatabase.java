package de.flapdoodle.drug.persistence.config;

import java.net.UnknownHostException;

import com.google.inject.name.Names;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class ProductionDatabase extends AbstractDatabaseModule
{
	@Override
	protected void configure()
	{
		try
		{
			bind(ServerAddress.class).toInstance(new ServerAddress("localhost", 27017));
			MongoOptions options = new MongoOptions();
			bind(MongoOptions.class).toInstance(options);
			
			bind(String.class).annotatedWith(Names.named("database")).toInstance(getProductionDatabase());
		}
		catch (UnknownHostException uox)
		{
			addError(uox);
		}
	}
}
