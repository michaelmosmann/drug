package de.flapdoodle.drug.persistence.orientdb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.orientechnologies.orient.core.db.graph.OGraphDatabase;

// https://github.com/orientechnologies/orientdb/wiki/Graph-Database-Tinkerpop

public class TestOrientDBStuff {

	private OGraphDatabase db;

//	@Test
//	public void testSomething() {
//		OGraphDatabase db=new OGraphDatabase("memory:test").open("foo", "bar");
//		db.close();
//	}

	@Before
  public void before() throws Exception {
		
      db = new OGraphDatabase("memory:test");
      System.out.println("db.exists():           " + db.exists());
      if(!db.exists()) {
          db.create();
          System.out.println("db.create()");
      }
      System.out.println("db:                    " + db.getClass().getName() + "@" + Integer.toHexString(db.hashCode()));
      //System.out.println("db.getEntityManager(): " + db.getEntityManager());

      //db.getEntityManager().registerEntityClass(TestObject.class);
  }

  @After
  public void after() throws Exception {
      db.drop();
      System.out.println("db.drop()");
  }

  @Test
  public void testOne() throws Exception {
      System.out.println("testOne start");
//      System.out.println(db.query(new OSQLSynchQuery<OUser>("select from OUser")));
//      System.out.println(db.query(new OSQLSynchQuery<TestObject>("select from TestObject")));
      System.out.println("testOne end");
  }
}
