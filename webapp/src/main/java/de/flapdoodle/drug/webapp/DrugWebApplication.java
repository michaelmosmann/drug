package de.flapdoodle.drug.webapp;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.core.request.mapper.BookmarkableMapper;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestMapper;

import com.google.inject.Inject;

import de.agilecoders.wicket.Bootstrap;
import de.agilecoders.wicket.settings.BootstrapSettings;
import de.flapdoodle.drug.config.Profile;
import de.flapdoodle.drug.webapp.app.StartPage;
import de.flapdoodle.drug.webapp.app.view.DescriptionPage;
import de.flapdoodle.drug.webapp.app.view.DescriptionsPage;
import de.flapdoodle.drug.webapp.app.view.TransformationPage;
import de.flapdoodle.drug.webapp.app.view.TransformationsPage;
import de.flapdoodle.drug.webapp.bootstrap.BootstrapPage;

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
		
		mountPage("/info", DescriptionPage.class);
		mountPage("/infos", DescriptionsPage.class);
		mountPage("/tranformation", TransformationPage.class);
		mountPage("/tranformations", TransformationsPage.class);
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
			return BootstrapPage.class;
		}
//		return MongoTestPage.class;
//		return DashboardPage.class;
		return StartPage.class;
	}
}
