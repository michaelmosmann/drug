package de.flapdoodle.drug.webapp.app.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.google.inject.Inject;

import de.flapdoodle.drug.config.Profile;


public class ShutdownWebappPage extends AbstractBasePage {

	@Inject
	Profile _profile;
	
	public ShutdownWebappPage() {
		
		add(new Link<Void>("link") {
			@Override
			public void onClick() {
				System.exit(0);
			}
		});
		
		add(new Label("profil",_profile.name()));
	}
}
