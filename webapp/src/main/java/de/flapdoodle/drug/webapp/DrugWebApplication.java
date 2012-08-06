package de.flapdoodle.drug.webapp;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

import com.google.inject.Inject;

import de.flapdoodle.drug.config.Profile;
import de.flapdoodle.drug.webapp.app.StartPage;
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

		
		
		switch (_profile)
		{
			case Production:
				_bootstrapDone=true;
				break;
		}
		
		if (getConfigurationType()==RuntimeConfigurationType.DEVELOPMENT) {
			getDebugSettings().setDevelopmentUtilitiesEnabled(true);
			getDebugSettings().setAjaxDebugModeEnabled(true);
		}
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
