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
package de.flapdoodle.drug.webapp.security;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;


public class LoginPageUnauthorizedComponentInstantiationListener implements IUnauthorizedComponentInstantiationListener {

	private final Class<? extends Page> _signInPageClass;

	
	public LoginPageUnauthorizedComponentInstantiationListener(Class<? extends Page> signInPageClass) {
		_signInPageClass=signInPageClass;
	}
	
	@Override
	public void onUnauthorizedInstantiation(Component component) {
		// If there is a sign in page class declared, and the
		// unauthorized component is a page, but it's not the
		// sign in page
		if (component instanceof Page)
		{
			// Redirect to page to let the user sign in
			throw new RestartResponseAtInterceptPageException(_signInPageClass);
		}
		else
		{
			// The component was not a page, so throw exception
			throw new UnauthorizedInstantiationException(component.getClass());
		}

	}

}
