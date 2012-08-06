package de.flapdoodle.drug.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.guice.GuiceWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

import de.flapdoodle.drug.webapp.DrugWebApplication;

public class WebApplicationConfig extends ServletModule
{
	 
	@Override
	protected void configureServlets()
	{
//    bind(WicketFilter.class).in(Singleton.class);
//    filterRegex("/*").through(WicketFilter.class, withApplicationClass(DrugWebApplication.class));
    
    {
    	String filterMapping = "/*";
    	
    	Map<String, String> params = new HashMap<String, String>();
    	params.put(WicketFilter.APP_FACT_PARAM,GuiceWebApplicationFactory.class.getName());
			params.put(WicketFilter.FILTER_MAPPING_PARAM, filterMapping);
//    	params.put("injectorContextAttribute", "GuiceInjector");
    	params.put("module",DrugWebModule.class.getName());
    	params.put("configuration",RuntimeConfigurationType.DEVELOPMENT.toString());
    	filter(filterMapping).through(DrugWebFilter.class, params);
    }
	}
	
	@Singleton
	public static class DrugWebFilter extends WicketFilter
	{
		
	}
		
	public static class DrugWebModule extends AbstractModule
	{
		@Override
		protected void configure()
		{
			install(new ApplicationConfig());
	    bind(WebApplication.class).to(DrugWebApplication.class);
		}
	}
	
}
