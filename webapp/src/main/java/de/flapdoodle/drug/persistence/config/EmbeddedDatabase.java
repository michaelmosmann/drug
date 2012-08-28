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
package de.flapdoodle.drug.persistence.config;

import java.net.UnknownHostException;

import com.google.inject.name.Names;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

import de.flapdoodle.embed.process.runtime.Network;


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
