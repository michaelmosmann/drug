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
