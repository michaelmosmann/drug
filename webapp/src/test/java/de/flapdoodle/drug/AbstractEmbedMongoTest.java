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
package de.flapdoodle.drug;

import java.util.List;

import junit.framework.TestResult;

import com.google.common.collect.Lists;
import com.google.inject.Module;

import de.flapdoodle.drug.persistence.config.EmbeddedDatabase;
import de.flapdoodle.drug.persistence.config.Persistence;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;


public abstract class AbstractEmbedMongoTest extends AbstractGuiceTest {
	
	MongodProcess _mongod = null;
	MongodExecutable _mongodExe = null;
	
	@Override
	protected void setUp() throws Exception {
		
		MongodStarter runtime = MongodStarter.getDefaultInstance();
		_mongodExe = runtime.prepare(new MongodConfig(Version.Main.V2_1, EmbeddedDatabase.EMBEDDED_PORT,Network.localhostIsIPv6()));
		_mongod=_mongodExe.start();
    
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		_mongod.stop();
		_mongodExe.cleanup();
	}
	
	@Override
	public TestResult run() {
		// TODO Auto-generated method stub
		return super.run();
	}
	
	@Override
	protected List<? extends Module> getModules()
	{
		return Lists.newArrayList(new EmbeddedDatabase(), new Persistence());
	}

}
