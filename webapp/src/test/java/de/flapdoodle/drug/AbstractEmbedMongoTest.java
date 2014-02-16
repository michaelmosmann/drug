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
package de.flapdoodle.drug;

import com.google.common.collect.Lists;
import com.google.inject.Module;
import de.flapdoodle.drug.config.Profile;
import de.flapdoodle.drug.persistence.config.Logging;
import de.flapdoodle.drug.persistence.config.mongo.EmbeddedDatabase;
import de.flapdoodle.drug.persistence.config.mongo.Persistence;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import java.util.List;
import org.junit.After;
import org.junit.Before;


public abstract class AbstractEmbedMongoTest extends AbstractGuiceTest {
	
	MongodProcess _mongod = null;
	MongodExecutable _mongodExe = null;
	
	@Override
	@Before
	public void setUp() throws Exception {
		
		MongodStarter runtime = MongodStarter.getDefaultInstance();
		_mongodExe = runtime.prepare(new MongodConfigBuilder()
			.version(Version.Main.PRODUCTION)
			.net(new Net(EmbeddedDatabase.EMBEDDED_PORT,Network.localhostIsIPv6()))
			.build());
		_mongod=_mongodExe.start();
    
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		_mongod.stop();
		_mongodExe.stop();
	}
	
	@Override
	protected List<? extends Module> getModules()
	{
		return Lists.newArrayList(new Logging(), new Persistence(Profile.Test));
	}

}
