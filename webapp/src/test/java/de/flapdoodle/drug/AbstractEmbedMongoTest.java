package de.flapdoodle.drug;

import java.util.List;

import junit.framework.TestResult;

import com.google.common.collect.Lists;
import com.google.inject.Module;

import de.flapdoodle.drug.persistence.config.EmbeddedDatabase;
import de.flapdoodle.drug.persistence.config.Persistence;
import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;
import de.flapdoodle.embedmongo.runtime.Network;


public abstract class AbstractEmbedMongoTest extends AbstractGuiceTest {
	
	MongodProcess _mongod = null;
	MongodExecutable _mongodExe = null;
	
	@Override
	protected void setUp() throws Exception {
		
		MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();
		_mongodExe = runtime.prepare(new MongodConfig(Version.V2_1_0, EmbeddedDatabase.EMBEDDED_PORT,Network.localhostIsIPv6()));
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
