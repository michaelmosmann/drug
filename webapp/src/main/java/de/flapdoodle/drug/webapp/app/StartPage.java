package de.flapdoodle.drug.webapp.app;

import org.apache.wicket.markup.html.WebPage;

import de.flapdoodle.drug.webapp.app.navigation.Navigation;

public class StartPage extends WebPage {
	
	public StartPage() {
		Navigation.toDescriptions("Start",true).asResponse();
	}
}
