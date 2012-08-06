package de.flapdoodle.drug.persistence.config;

import java.util.Set;
import java.util.logging.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.mongodb.Mongo;

import de.flapdoodle.mongoom.IDatastore;
import de.flapdoodle.mongoom.datastore.Datastore;
import de.flapdoodle.mongoom.logging.LogConfig;
import de.flapdoodle.mongoom.mapping.context.IMappingContextFactory;
import de.flapdoodle.mongoom.mapping.context.MappingContextFactory;
import de.flapdoodle.mongoom.mapping.context.Transformations;

public class Dao extends AbstractModule {

	private static final Logger _logger = LogConfig.getLogger(Dao.class);

	@Override
	protected void configure() {
		bind(Transformations.class).toProvider(TransformationsProvider.class).in(Singleton.class);
		bind(IDatastore.class).toProvider(DatastoreProvider.class).in(Singleton.class);
	}

	static class DatastoreProvider implements Provider<IDatastore> {

		private final Mongo _mongo;
		private final Transformations _transformations;
		private final String _database;

		@Inject
		public DatastoreProvider(Mongo mongo, Transformations transformations, @Named("database") String database) {
			_mongo = mongo;
			_transformations = transformations;
			_database = database;
		}

		@Override
		public IDatastore get() {
			IDatastore ret = new Datastore(_mongo, _database, _transformations);
			ret.ensureCaps();
			ret.ensureIndexes();
			return ret;
		}
	}

	static class TransformationsProvider implements Provider<Transformations> {

		private final Set<Class<?>> _classes;

		@Inject
		public TransformationsProvider(@Named("beans") Set<Class<?>> classes) {
			_classes = classes;
		}

		@Override
		public Transformations get() {
			IMappingContextFactory<?> factory = new MappingContextFactory();
//			Set<Class<?>> classes=(Set<Class<?>>) (Set) _classes;
			Transformations ret = new Transformations(factory,_classes);
			return ret;
		}
	}

}
