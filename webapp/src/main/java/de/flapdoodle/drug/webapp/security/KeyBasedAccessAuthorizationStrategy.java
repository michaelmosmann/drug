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
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.strategies.page.AbstractPageAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;

public class KeyBasedAccessAuthorizationStrategy implements IAuthorizationStrategy {

	
	protected <T extends Component> boolean isAuthorized(Class<T> pageClass) {
		if (pageClass.getAnnotation(NotPublic.class) != null) {
			return ValidUserKey.isSet(Session.get());
		} else {
			Class<? super T> sclazz = pageClass.getSuperclass();
			if (Component.class.isAssignableFrom(sclazz)) {
				return isAuthorized((Class<Component>) sclazz);
			}
		}
		return true;
	}

	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
		if (Page.class.isAssignableFrom(componentClass)) {
			return isAuthorized((Class<Page>) componentClass);
		}
		return true;
	}

	@Override
	public boolean isActionAuthorized(Component component, Action action) {
		if (action.equals(Component.RENDER)) {
			return isAuthorized(component.getClass());
		}
		return true;
	}

}
