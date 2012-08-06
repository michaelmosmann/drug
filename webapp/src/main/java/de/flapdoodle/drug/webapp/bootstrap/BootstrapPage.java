package de.flapdoodle.drug.webapp.bootstrap;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import de.flapdoodle.drug.persistence.beans.Description;
import de.flapdoodle.drug.persistence.dao.DescriptionDao;
import de.flapdoodle.drug.persistence.dao.TransformationDao;
import de.flapdoodle.drug.webapp.DrugWebApplication;

public class BootstrapPage extends WebPage {

	@Inject
	TransformationDao _transformationDao;

	@Inject
	DescriptionDao _descriptionDao;

	private boolean _done;

	public BootstrapPage() {
		if (!bootUp())
			throw new RuntimeException("Could not BootUp");

		setResponsePage(DrugWebApplication.get().getHomePage());
	}

	private synchronized boolean bootUp() {
		if (_done)
			return true;

		List<Description> list = _descriptionDao.findByName(true, "Start");
		if ((list==null) || (list.isEmpty())) {
			Description start = new Description();
			start.setName("Start");
			start.setObject(true);
			start.setText("Das ist die Start-Testseite. Ab hier kann [s:Mensch|man] weitere [o:Seiten] [p:erstellen].");
			_descriptionDao.save(start);
		}
		_done = true;
		return true;
	}

}
