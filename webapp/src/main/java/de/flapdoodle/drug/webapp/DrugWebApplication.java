/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
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
package de.flapdoodle.drug.webapp;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authentication.strategy.DefaultAuthenticationStrategy;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.core.request.mapper.BookmarkableMapper;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.settings.ISecuritySettings;

import com.google.inject.Inject;

import de.agilecoders.wicket.Bootstrap;
import de.agilecoders.wicket.settings.BootstrapSettings;
import de.flapdoodle.drug.config.Profile;
import de.flapdoodle.drug.webapp.app.StartPage;
import de.flapdoodle.drug.webapp.app.edit.EditDescriptionPage;
import de.flapdoodle.drug.webapp.app.edit.EditTransformationPage;
import de.flapdoodle.drug.webapp.app.pages.LoginPage;
import de.flapdoodle.drug.webapp.app.view.DescriptionPage;
import de.flapdoodle.drug.webapp.app.view.DescriptionsPage;
import de.flapdoodle.drug.webapp.app.view.TransformationPage;
import de.flapdoodle.drug.webapp.app.view.TransformationsPage;
import de.flapdoodle.drug.webapp.bootstrap.DrugBootstrap;
import de.flapdoodle.drug.webapp.security.KeyBasedAccessAuthorizationStrategy;
import de.flapdoodle.drug.webapp.security.LoginPageUnauthorizedComponentInstantiationListener;

public class DrugWebApplication extends WebApplication
{
	@Inject
	Profile _profile;
	
	public DrugWebApplication()
	{
		super();
		// HACK
		//Application.set(this);
	}
	
	@Override
	protected void init()
	{
		super.init();
		
//		getComponentInstantiationListeners().add(new GuiceComponentInjector(this));
		
		if ((true) || (!isDevelopmentMode())) {
			ISecuritySettings securitySettings = getSecuritySettings();
			securitySettings.setAuthenticationStrategy(new DefaultAuthenticationStrategy("drug"));
			securitySettings.setAuthorizationStrategy(new KeyBasedAccessAuthorizationStrategy());
			securitySettings.setUnauthorizedComponentInstantiationListener(new LoginPageUnauthorizedComponentInstantiationListener(LoginPage.class));
		}

    BootstrapSettings settings = new BootstrapSettings();
    settings.minify(true); // use minimized version of all bootstrap references

    Bootstrap.install(this, settings);

		
		switch (_profile)
		{
			case Production:
				_bootstrapDone=true;
				break;
		}
		
		if (getConfigurationType()==RuntimeConfigurationType.DEVELOPMENT) {
			getDebugSettings().setDevelopmentUtilitiesEnabled(true);
			getDebugSettings().setAjaxDebugModeEnabled(true);
			
			getMarkupSettings().setStripWicketTags(true);
		}
		
		mountPage("/login", LoginPage.class);
		mountPage("/info", DescriptionPage.class);
		mountPage("/infos", DescriptionsPage.class);
		mountPage("/editInfo", EditDescriptionPage.class);
		mountPage("/tranformation", TransformationPage.class);
		mountPage("/tranformations", TransformationsPage.class);
		mountPage("/editTranformation", EditTransformationPage.class);
//		mountAnnotated(DescriptionPage.class);
//		mountAnnotated(TransformationPage.class);
//		mountAnnotated(EditTransformationPage.class);
	}
	
	boolean _bootstrapDone=false;
	
	@Override
	public Class<? extends Page> getHomePage()
	{
		if (!_bootstrapDone)
		{
			_bootstrapDone=true;
			new DrugBootstrap();
		}
//		return MongoTestPage.class;
//		return DashboardPage.class;
		return StartPage.class;
	}
	
	public static boolean isDevelopmentMode() {
		return WebApplication.get().getConfigurationType()==RuntimeConfigurationType.DEVELOPMENT;
	}
}
