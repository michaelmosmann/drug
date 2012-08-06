package de.flapdoodle.drug;

import java.util.List;

import junit.framework.TestCase;

import com.google.inject.Guice;
import com.google.inject.Module;

public abstract class AbstractGuiceTest extends TestCase
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		Guice.createInjector(getModules()).injectMembers(this);
	}
	
	protected abstract List<? extends Module> getModules();
}
