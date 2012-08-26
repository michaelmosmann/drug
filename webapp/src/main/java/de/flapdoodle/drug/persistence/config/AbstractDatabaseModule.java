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
