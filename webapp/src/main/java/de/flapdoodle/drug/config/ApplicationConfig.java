package de.flapdoodle.drug.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.AbstractModule;

import de.flapdoodle.drug.persistence.config.Persistence;
import de.flapdoodle.drug.persistence.config.PreviewDatabase;
import de.flapdoodle.drug.persistence.config.ProductionDatabase;

public class ApplicationConfig extends AbstractModule {

	private static final Logger _logger = Logger.getLogger(ApplicationConfig.class.getName());

	@Override
	protected void configure() {
		Profile p=getProfile();
		switch (p)
		{
			case Local:
				install(new PreviewDatabase());
				break;
			case Production:
				install(new ProductionDatabase());
				break;
		}
		install(new Persistence());
		bind(Profile.class).toInstance(p);
	}
	
	private Profile getProfile() {
		String hostname = getHostname();
		if ("ubuntu606m".equals(hostname)) {
			return Profile.Production;
		}
		return Profile.Local;
	}

	static String getHostname() {
		String hostname = System.getProperty("deployment.hostname");
		if (hostname == null) {
			try {
				hostname = InetAddress.getLocalHost().getHostName();
				_logger.info("Hostname: " + hostname);
			} catch (UnknownHostException e) {
				_logger.log(Level.SEVERE, "hostname", e);
			}
		} else {
			_logger.info("Hostname: " + hostname + " (from System.Properties)");
		}
		return hostname;
	}

	static String getUsername() {
		String ret = System.getProperty("deployment.username");
		if (ret == null) {
			ret = System.getProperty("user.name");
			_logger.info("Username: " + ret);
		} else {
			_logger.info("Username: " + ret + " (from System.Properties)");
		}
		return ret;
	}

}
