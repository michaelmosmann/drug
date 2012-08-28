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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.AbstractModule;

import de.flapdoodle.mongoom.datastore.Datastore;
import de.flapdoodle.mongoom.logging.LogConfig;

public class Logging extends AbstractModule
{
	private static final Logger _logger = LogConfig.getLogger(Logging.class);
	
	@Override
	protected void configure()
	{
		_logger.severe("Logging");
		
		Logger.getLogger("de.flapdoodle").setLevel(Level.WARNING);
		_logger.info("Info hidden");
		
		Logger.getLogger("de.flapdoodle").setLevel(Level.ALL);
		Logger.getLogger(Datastore.class.getName()).setLevel(Level.FINEST);
		Logger.getLogger("com.google").setLevel(Level.ALL);
		_logger.info("Info done");
	}

}
