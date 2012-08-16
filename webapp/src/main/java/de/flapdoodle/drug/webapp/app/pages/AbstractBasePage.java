package de.flapdoodle.drug.webapp.app.pages;

import java.util.Locale;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;

import de.agilecoders.wicket.Bootstrap;
import de.agilecoders.wicket.markup.html.bootstrap.html.ChromeFrameMetaTag;
import de.agilecoders.wicket.markup.html.bootstrap.html.HtmlTag;
import de.agilecoders.wicket.markup.html.bootstrap.html.MetaTag;
import de.agilecoders.wicket.markup.html.bootstrap.html.OptimizedMobileViewportMetaTag;
import de.agilecoders.wicket.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.markup.html.bootstrap.navbar.Navbar.ButtonPosition;
import de.agilecoders.wicket.markup.html.bootstrap.navbar.Navbar.Position;
import de.agilecoders.wicket.markup.html.bootstrap.navbar.NavbarButton;
import de.flapdoodle.drug.webapp.app.StartPage;


public abstract class AbstractBasePage extends WebPage {

	
	public AbstractBasePage() {
		add(new HtmlTag("html").locale(Locale.GERMAN));
		
		add(new OptimizedMobileViewportMetaTag("base-viewport"));
		add(new ChromeFrameMetaTag("base-chrome-frame"));
		add(new MetaTag("base-description", Model.of("description"), Model.of("Alter Ego of Wiki")));
		add(new MetaTag("base-author", Model.of("author"), Model.of("Michael Mosmann <michael@mosmann.de>")));
		
		Navbar navbar = new Navbar("base-nav");
//		navbar.fluid();
		navbar.setPosition(Position.TOP);
		navbar.brandName(Model.of("Drug"));
		    
		navbar.addButton(ButtonPosition.LEFT,
		        new NavbarButton<StartPage>(StartPage.class, Model.of("Home"))
//		        new NavbarButton<LinkPage>(LinkPage.class, Model.of("Link")),
		        );
		
		add(navbar);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
	    super.renderHead(response);

	    Bootstrap.renderHead(response);
	}
}
