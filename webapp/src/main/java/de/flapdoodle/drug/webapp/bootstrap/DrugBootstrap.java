/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <${lic.email2}>
 *
 * with contributions from
 * 	${lic.developers}
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
package de.flapdoodle.drug.webapp.bootstrap;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.io.Streams;

import com.google.inject.Inject;

import de.flapdoodle.drug.logging.Loggers;
import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.dao.TransformationDao;
import de.flapdoodle.drug.webapp.DrugWebApplication;

public class DrugBootstrap {

	private static final Logger _logger = Loggers.getLogger(DrugBootstrap.class);
	
	@Inject
	TransformationDao _transformationDao;

	@Inject
	DescriptionDao _descriptionDao;

	private boolean _done;

	public DrugBootstrap() {
		Injector.get().inject(this);
		
		if (!bootUp())
			throw new RuntimeException("Could not BootUp");
	}

	private synchronized boolean bootUp() {
		_logger.severe("bootUp");
		
		if (_done)
			return true;

		List<Description> list = _descriptionDao.findByName(true, "Start");
		if ((list==null) || (list.isEmpty())) {
			Description start = new Description();
			start.setName("Start");
			start.setObject(true);
			
			start.setText(getBootstrapMarkup("Start","Konnte das Template für [[Start]] nicht finden. Da muss [s:man->Mensch] mal nach dem [o:Fehler] [p:suchen]."));
			_descriptionDao.save(start);
		} else {
			_logger.severe("Start exists: "+list);
		}
		_done = true;
		return true;
	}

	private String getBootstrapMarkup(String name,String defaultText) {
		try {
			return Streams.readString(getClass().getResourceAsStream(""+name+".txt"));
		} catch (IOException e) {
			e.printStackTrace();
			return defaultText;
		}
	}

}
