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

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.page.AbstractPageAuthorizationStrategy;

public class KeyBasedAccessAuthorizationStrategy extends AbstractPageAuthorizationStrategy {

	@Override
	protected <T extends Page> boolean isPageAuthorized(Class<T> pageClass) {
		if (pageClass.getAnnotation(NotPublic.class) != null) {
			return Session.get().getMetaData(new ValidUserKey()) != null;
		} else {
			Class<? super T> sclazz = pageClass.getSuperclass();
			if (Page.class.isAssignableFrom(sclazz)) {
				return isPageAuthorized((Class<Page>) sclazz);
			}
		}
		return true;
	}

}
