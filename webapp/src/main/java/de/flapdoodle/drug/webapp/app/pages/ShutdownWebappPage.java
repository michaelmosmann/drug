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

import org.apache.wicket.Application;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.google.inject.Inject;

import de.flapdoodle.drug.config.Profile;
import de.flapdoodle.drug.webapp.DrugWebApplication;


public class ShutdownWebappPage extends AbstractProtectedPage {

	@Inject
	Profile _profile;
	
	public ShutdownWebappPage() {
		
		add(new Link<Void>("link") {
			@Override
			public void onClick() {
				if (DrugWebApplication.isDevelopmentMode()) {
					System.exit(0);
				}
			}
		});
		
		add(new Label("profil",_profile.name()));
	}
}
