package de.flapdoodle.drug.persistence.orientdb;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.orientechnologies.orient.core.db.ODatabaseComplex;
import com.orientechnologies.orient.core.db.graph.OGraphDatabase;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.security.OUser;
import com.orientechnologies.orient.core.record.ORecordInternal;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.core.type.tree.OMVRBTreeRIDSet;

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
      System.out.println(db.query(new OSQLSynchQuery<OUser>("select from OUser")));
      //System.out.println(db.query(new OSQLSynchQuery<TestObject>("select from TestObject")));

      
      ODocument doc = db.newInstance("foo");
      doc.field("name", "Klaus");
      doc.field("created", new Date());
      doc.save();

      ODocument bar = db.newInstance("bar");
      bar.field("name", "Bar");
      bar.save();

//      ODatabaseComplex<ORecordInternal<?>> tx = db.begin();
//      tx.commit();
      
      ORecordIteratorClass<ODocument> browse = db.browseClass("foo");
      while (browse.hasNext()) {
      	System.out.println("found: "+browse.next());
      }
      
      ODocument edge = db.createEdge(doc, bar);
      edge.field("edge","foo-bar");
      edge.save();
      
      
      Iterable<ODocument> edges = db.browseEdges();
      for (ODocument e : edges) {
      	System.out.println("found: "+e);
      	System.out.println("found.in: "+e.field("in"));
      }
      System.out.println("graph stuff");
      
      OClass vertextType = db.createVertexType("vertexA");
      
      ODocument v1 = db.createVertex("vertexA").field("name", "v1").save();
      ODocument v2 = db.createVertex("vertexA").field("name", "v2").save();
      db.createEdge(v1, v2).field("edgeA", "e1").field("type","test").save();
      db.createEdge(v1, v2).field("edgeA", "e2").field("type","test").save();
      
      edges = db.browseEdges();
      for (ODocument e : edges) {
      	System.out.println("found: "+e);
      	System.out.println("found.in: "+e.field("in"));
      }
      
      System.out.println("testOne end");
  }
}
