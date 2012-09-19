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
package de.flapdoodle.drug.webapp.app.pages;

import java.util.Locale;

import org.apache.wicket.Page;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
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
import de.flapdoodle.drug.webapp.DrugWebApplication;
import de.flapdoodle.drug.webapp.app.StartPage;
import de.flapdoodle.drug.webapp.security.NotPublic;
import de.flapdoodle.drug.webapp.security.ValidUserKey;

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
		    
		if (!DrugWebApplication.isDevelopmentMode()) {
			
			NavbarButton<LoginPage> login = new NavbarButton<LoginPage>(LoginPage.class, Model.of("Anmelden"));
			login.setVisible(!ValidUserKey.isSet(getSession()));
			navbar.addButton(ButtonPosition.LEFT,
	        new NavbarButton<StartPage>(StartPage.class, Model.of("Home")),
	        new NavbarButton<MarkdownHelpPage>(MarkdownHelpPage.class, Model.of("Hilfe")),
	        login,
	        new ProtectedNavbarButton<LogoutPage>(LogoutPage.class, Model.of("Abmelden"))
			        );
		} else {
			navbar.addButton(ButtonPosition.LEFT,
	        new NavbarButton<StartPage>(StartPage.class, Model.of("Home")),
	        new NavbarButton<MarkdownHelpPage>(MarkdownHelpPage.class, Model.of("Hilfe")),
	        new NavbarButton<ShutdownWebappPage>(ShutdownWebappPage.class, Model.of("Shutdown"))
	        );
		}
		
		add(navbar);
	}

	@NotPublic
	public static class ProtectedNavbarButton<T extends Page> extends NavbarButton<T> {

		public ProtectedNavbarButton(Class<T> pageClass, IModel<String> label) {
			super(pageClass, label);
		}
		
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
	    super.renderHead(response);

	    Bootstrap.renderHead(response);
	}
}
