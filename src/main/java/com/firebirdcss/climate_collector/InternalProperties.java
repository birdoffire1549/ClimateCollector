package com.firebirdcss.climate_collector;

import java.util.Properties;

/**
 * 
 * 
 * @author Scott Griffis
 *
 */
public final class InternalProperties {
	public static final String PROPERTY_STATS_PULL_FREQ_MILLIS = "statsPullFreqMillis";
	public static final String PROPERTY_WEATHER_API_URL = "weather_api.url";
	public static final String PROPERTY_WEATHER_API_KEY = "weather_api.key";
	public static final String PROPERTY_DB_USER = "db.user";
	public static final String PROPERTY_DB_PASSWORD = "db.password";
	public static final String PROPERTY_DB_HOST = "db.host";
	public static final String PROPERTY_DB_PORT = "db.port";
	
	public static final long DEFAULT_STATS_PULL_FREQ_MILLIS = 1 * 60 * 60 * 1000; // 1 Hour
	public static final String DEFAULT_PROPERTIES_FILE_PATH = "/opt/ClimateCollector/config/application.properties";
	
	public static Properties propertyFile = null;
	
	public static final String WEATHER_DB_NAME = "climate_collector";
	public static final String WEATHER_TABLE_NAME = "weather_collection_records";
}
