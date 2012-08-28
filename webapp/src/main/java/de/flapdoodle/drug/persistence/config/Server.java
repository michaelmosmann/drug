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

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class Server extends AbstractModule {

	@Override
	protected void configure() {
		bind(Mongo.class).toProvider(MongoProvider.class).in(Singleton.class);
		bind(DB.class).toProvider(MongoDBProvider.class).in(Singleton.class);
	}

	static class MongoDBProvider implements Provider<DB> {

		private final Mongo _mongo;
		private final String _dbName;

		@Inject
		public MongoDBProvider(Mongo mongo, @Named("database") String dbName) {
			_mongo = mongo;
			_dbName = dbName;
		}

		@Override
		public DB get() {
			return _mongo.getDB(_dbName);
		}
	}

	static class MongoProvider implements Provider<Mongo> {

		private final ServerAddress _address;
		private final MongoOptions _options;

		@Inject
		public MongoProvider(ServerAddress address, MongoOptions options) {
			_address = address;
			_options = options;
		}

		@Override
		public Mongo get() {
			return new Mongo(_address, _options);
		}
	}
}
