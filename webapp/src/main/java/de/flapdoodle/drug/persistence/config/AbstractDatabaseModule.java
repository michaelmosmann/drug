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

public abstract class AbstractDatabaseModule extends AbstractModule
{
	protected static final String DATABASE="drug";
	
	protected static String getPreviewDatabase()
	{
		return as("preview",DATABASE);
	}
	protected static String getNoInstallDatabase()
	{
		return as("noinstall",DATABASE);
	}
	protected static String getProductionDatabase()
	{
		return as("prod",DATABASE);
	}
	protected static String getUnittestDatabase()
	{
		return as("unittest",DATABASE);
	}
	
	private static String as(String prefix, String dbName)
	{
		return prefix+"_"+dbName;
	}
}
