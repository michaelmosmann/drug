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
