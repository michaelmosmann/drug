/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
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
package de.flapdoodle.drug.persistence.config;

import de.flapdoodle.mongoom.logging.LogConfig;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import com.google.inject.Provider;
import com.google.inject.name.Names;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.RuntimeConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.runtime.Mongod;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.runtime.Network;


public class NoInstallDatabase extends AbstractDatabaseModule
{
	private static final Logger _logger = LogConfig.getLogger(NoInstallDatabase.class);
	
	public static final int EMBEDDED_PORT = 65432;

	@Override
	protected void configure()
	{
//		try
		{
			bind(ServerAddress.class).toProvider(ServerAddressProvider.class);
			MongoOptions options = new MongoOptions();
			bind(MongoOptions.class).toInstance(options);
			
			bind(String.class).annotatedWith(Names.named("database")).toInstance(getNoInstallDatabase());
		}
//		catch (UnknownHostException uox)
//		{
//			addError(uox);
//		}
	}
	
	static class ServerAddressProvider implements Provider<ServerAddress> {

		private MongodExecutable _mongodExe;
		private MongodProcess _mongod;
		
		private String _databaseDir=System.getProperty("user.home")+File.separator+".drugNoInstallDatabaseDir";

		@Override
		public ServerAddress get() {
			try {
				
				_logger.severe("DatabaseDir: "+_databaseDir);
				
				InetAddress localHost = Network.getLocalHost();
//				int freeServerPort=Network.getFreeServerPort(localHost);
				int freeServerPort=45678;
				
				// send shutdown if someone is there
				Mongod.sendShutdown(localHost, freeServerPort);
				
				MongodStarter runtime = MongodStarter.getDefaultInstance();
				
				_mongodExe = runtime.prepare(new MongodConfig(Version.Main.V2_1, freeServerPort,Network.localhostIsIPv6(),_databaseDir));
				_mongod=_mongodExe.start();
				
				return new ServerAddress(localHost, freeServerPort);
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
}
