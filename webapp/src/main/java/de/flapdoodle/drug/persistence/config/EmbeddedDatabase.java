package de.flapdoodle.drug.persistence.config;

import java.net.UnknownHostException;

import com.google.inject.name.Names;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

import de.flapdoodle.embedmongo.runtime.Network;


public class EmbeddedDatabase extends AbstractDatabaseModule
{
	public static final int EMBEDDED_PORT = 65432;

	@Override
	protected void configure()
	{
		try
		{
			bind(ServerAddress.class).toInstance(new ServerAddress(Network.getLocalHost(), EMBEDDED_PORT));
			MongoOptions options = new MongoOptions();
			bind(MongoOptions.class).toInstance(options);
			
			bind(String.class).annotatedWith(Names.named("database")).toInstance(getPreviewDatabase());
		}
		catch (UnknownHostException uox)
		{
			addError(uox);
		}
	}
}
