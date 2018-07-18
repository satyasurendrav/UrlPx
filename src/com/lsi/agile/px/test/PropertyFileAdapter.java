package com.lsi.agile.px.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertyFileAdapter {
	
	Logger logger = Logger.getLogger(PropertyFileAdapter.class);
	Properties properties = new Properties();

	public Properties loadProperties() throws Throwable {
		logger.info("Reading properties file ...");
		try {
			InputStream pis = new FileInputStream(System.getProperty("catalina.base")+"/conf/customcommon.properties");
			properties.load(pis);
			pis.close();
			logger.info("Here is the content of the properties file...\n"+ properties.toString());
			return properties;
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("Error while reading properties file", e);
			throw e;
		}
	}
}
