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

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.WebApplication;

import de.flapdoodle.drug.webapp.security.PasswordHash;
import de.flapdoodle.drug.webapp.security.ValidUserKey;

public class LoginPage extends AbstractBasePage {

	
	public LoginPage() {
		
		add(new FeedbackPanel("feedbackPanel"));
		
		Form<FormData> form = new Form<FormData>("form",new CompoundPropertyModel<FormData>(new FormData())) {
			
			@Override
			protected void onSubmit() {
				super.onSubmit();
				
				FormData data = getModelObject();
				String hash=PasswordHash.createHash(data.getName(), data.getPassword());
				if (!PasswordHash.isSecure(hash)) {
					error("Password not valid");
				} else {
					ValidUserKey.set(getSession(), hash);
					setModelObject(new FormData());
					
					continueToOriginalDestination();
					setResponsePage(WebApplication.get().getHomePage());
				}
			}
		};
		form.add(new TextField<String>("name").setRequired(true));
		form.add(new PasswordTextField("password"));
		add(form);
	}
	
	public static class FormData {

		String _name;
		String _password;

		public String getName() {
			return _name;
		}

		public void setName(String name) {
			_name = name;
		}

		public String getPassword() {
			return _password;
		}

		public void setPassword(String password) {
			_password = password;
		}
	}
}
