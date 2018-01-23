/**
 * All rights reserved, Copyright 2018 by Scott Griffis
 */
package com.firebirdcss.climate_collector.collectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.firebirdcss.climate_collector.InternalProperties;
import com.firebirdcss.climate_collector.pojo.WeatherReading;
import com.firebirdcss.climate_collector.utils.ParsingUtilities;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * This {@link Collector} is intended to collect temperatures from an online source
 * and record them in an external Database for later usage.
 * 
 * @author Scott Griffis
 *
 */
public class WeatherCollectionAgent extends Collector {
	private static final Logger log = LogManager.getLogger(WeatherCollectionAgent.class);
	
	private final SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
	private final long collectionCycleMillis;
	private final String apiUrl;
	private boolean running = true;
	
	private static final String SQL_STATEMENT = ""
			+ "INSERT INTO climate_collector.weather_collection_records "
				+ "(city, state, country, postal_code, "
				+ "latitude, longitude, elevation_ft, observer, "
				+ "station_id, observation_time, weather_str, temp_f, "
				+ "relative_humidity_percent, wind_str, wind_dir_str, wind_deg, "
				+ "wind_mph, wind_gust_mph, pressure_mb, pressure_in, "
				+ "pressure_trend, dewpoint_deg_f, wind_chill_deg_f, heat_index_deg_f, "
				+ "feels_like_deg_f, visibility_mi, solar_rad, uv_rad, "
				+ "percip_one_hour_in, percip_day_in, icon_str) "
			+ "VALUES "
				+ "(?, ?, ?, ?, " //  1 - 4
				+ "?, ?, ?, ?, "  //  5 - 8
				+ "?, ?, ?, ?, "  //  9 - 12
				+ "?, ?, ?, ?, "  // 13 - 16
				+ "?, ?, ?, ?, "  // 17 - 20
				+ "?, ?, ?, ?, "  // 21 - 24
				+ "?, ?, ?, ?, "  // 25 - 28
				+ "?, ?, ?)"      // 29 - 31
	;
	
	/**
	 * CONSTRUCTOR: CurrentWeatherCollector.java
	 *
	 * @param collectionCycleMillis
	 * @param apiUrl
	 */
	public WeatherCollectionAgent(long collectionCycleMillis, String apiUrl) {
		this.collectionCycleMillis = collectionCycleMillis;
		this.apiUrl = apiUrl;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.firebirdcss.climate_collector.collectors.Collector#run()
	 */
	@Override
	public void run() {
		Thread.currentThread().setName("WeatherCollectionAgent");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		
			while (running) {
				log.info("Checking for a new temperature reading...");
				WeatherReading reading = getNewReading();
				if (reading != null) {
					storeNewReading(reading);
				}
				
				log.info("Cycle complete; Sleeping until next cycle."); 
				try {
					Thread.sleep(collectionCycleMillis);
				} catch (InterruptedException ignoreAndContinue) {
					log.warn("Cycle sleep was interrupted!");
				}
			}
		} catch (ClassNotFoundException e) {
			log.fatal(e.getMessage(), e);
		}
	}
	
	/**
	 * Fetches a new temperature reading from an online source.
	 * 
	 * @return Returns the fetched temperature as an {@link Integer}
	 */
	private WeatherReading getNewReading() {
		WeatherReading result = null;
		log.info("Requesting a new temperature from Internet...");
		
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(apiUrl);
			String response = webResource.type("application/json").get(String.class);
			client.destroy();
			result = responseToWeatherReading(response);
		  } catch (Exception e) {
			  if (log.isDebugEnabled()) {
				  log.error("There was a problem pulling weather data this cycle; Will try again next cycle.", e);
			  } else {
				  log.error("There was a problem pulling weather data this cycle; Will try again next cycle.");
			  }
		  }
		
		log.info("Request Complete.");
		
		return result;
	}
	
	/**
	 * Handles parsing of the response string and placing the data into a {@link WeatherReading} POJO.
	 * 
	 * @param response - The response string to parse as {@link String}
	 * @return Returns a {@link WeatherReading} object 
	 */
	private WeatherReading responseToWeatherReading(String response) {
		WeatherReading wr = new WeatherReading();
		if (response != null) {
			boolean problem = false;
			int beginIndex = response.indexOf("\"observation_location\":");
			if (beginIndex != -1) {
				beginIndex += "\"observation_location\":".length();
			} else {
				beginIndex = 0;
			}
			
			/* Parse Observer and City */
			try {
				String tempCityPlus = ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"city\":", ",", beginIndex));
				if (tempCityPlus != null && tempCityPlus.contains(",")) {
					int commaIndex = tempCityPlus.indexOf(',');
					wr.setObserver(tempCityPlus.substring(0, commaIndex));
					wr.setCity(tempCityPlus.substring(commaIndex + 1).trim());				
				} else {
					wr.setObserver(tempCityPlus);
					wr.setCity(tempCityPlus);
				}
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the City value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			/* Parse Observation Time */
			try {
				wr.setObservationTime(
					df.parse(
						ParsingUtilities.removeQuotes(
							ParsingUtilities.parseBetweenKeywords(response, "\"observation_time_rfc822\":", "\",", beginIndex)
						)
					)
				);
			} catch (ParseException e) {
				problem = true;
				String message = "There was a problem with the Observation Time value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			/* Parse other data */
			try {
				wr.setState(ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"state\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the State value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setCountry(ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"country_iso3166\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Country ISO value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setPostalCode(ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"zip\":", ","))); // No beginIndex intentionally.
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Zip Code value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setLatitude(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"latitude\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Latitude value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setLongitude(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"longitude\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Longitude value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setElevationFt(ParsingUtilities.sanitizeIntegerString(ParsingUtilities.parseBetweenKeywords(response, "\"elevation\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Elevation value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setStationId(ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"station_id\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Station ID value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setWeatherStr(ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"weather\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Weather value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setTempF(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"temp_f\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Temp in F value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setRelativeHumidityPercent(ParsingUtilities.sanitizeIntegerString(ParsingUtilities.parseBetweenKeywords(response, "\"relative_humidity\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Relative Humidity value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setWindStr(ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"wind_string\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Wind String value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setWindDirStr(ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"wind_dir\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Wind Direction value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setWindDeg(ParsingUtilities.sanitizeIntegerString(ParsingUtilities.parseBetweenKeywords(response, "\"wind_degrees\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Wind Degrees value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setWindMph(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"wind_mph\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Wind MPH value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setWindGustMph(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"wind_gust_mph\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Wind Gust MPH value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setPressureMb(ParsingUtilities.sanitizeIntegerString(ParsingUtilities.parseBetweenKeywords(response, "\"pressure_mb\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Pressure MB value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setPressureIn(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"pressure_in\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Pressure IN value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setPressureTrend(ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"pressure_trend\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Pressure Trend value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setDewpointDegF(ParsingUtilities.sanitizeIntegerString(ParsingUtilities.parseBetweenKeywords(response, "\"dewpoint_f\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the DewPoint F value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setWindChillDegF(ParsingUtilities.sanitizeIntegerString(ParsingUtilities.parseBetweenKeywords(response, "\"windchill_f\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Wind Chill F value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setHeatIndexDegF(ParsingUtilities.sanitizeIntegerString(ParsingUtilities.parseBetweenKeywords(response, "\"heat_index_f\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Heat Index F value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setFeelsLikeDegF(ParsingUtilities.sanitizeIntegerString(ParsingUtilities.parseBetweenKeywords(response, "\"feelslike_f\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Feels Like F value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setVisibilityMi(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"visibility_mi\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Visibility MI value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setSolarRad(ParsingUtilities.sanitizeIntegerString(ParsingUtilities.parseBetweenKeywords(response, "\"solarradiation\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Solar Radiation value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setUvRad(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"UV\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the UV value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setPercipOneHourIn(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"precip_1hr_in\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Precip 1Hr IN value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setPercipDayIn(ParsingUtilities.sanitizeFloatString(ParsingUtilities.parseBetweenKeywords(response, "\"precip_today_in\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Precip Today IN value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			
			try {
				wr.setIconStr(ParsingUtilities.removeQuotes(ParsingUtilities.parseBetweenKeywords(response, "\"icon\":", ",", beginIndex)));
			} catch (Exception e) {
				problem = true;
				String message = "There was a problem with the Icon value!";
				if (log.isDebugEnabled()) {
					log.error(message, e);
				} else {
					log.error(message);
				}
			}
			if (problem) {
				log.error("Here is the JSON that was received from the server:\n\n[" + response + "]\n");
			}
		}
		
		return wr;
	}
	
	/**
	 * Stores the given reading in the appropriate external database.
	 * 
	 * @param reading - The reading as {@link Integer}
	 */
	private void storeNewReading(WeatherReading reading) {
		log.info("Saving new WeatherReading to the database...");
		try {
			String host = InternalProperties.propertyFile.getProperty(InternalProperties.PROPERTY_DB_HOST, "127.0.0.1");
			String port = InternalProperties.propertyFile.getProperty(InternalProperties.PROPERTY_DB_PORT, "3306");
			String user = InternalProperties.propertyFile.getProperty(InternalProperties.PROPERTY_DB_USER, "default");
			String pass = InternalProperties.propertyFile.getProperty(InternalProperties.PROPERTY_DB_PASSWORD, "default");
			Connection conndb = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/climate_collector", user, pass);
				PreparedStatement stmt = conndb.prepareStatement(SQL_STATEMENT);
				stmt.setString(1, reading.getCity());
				stmt.setString(2, reading.getState());
				stmt.setString(3, reading.getCountry());
				stmt.setString(4, reading.getPostalCode());
				stmt.setFloat(5, reading.getLatitude());
				stmt.setFloat(6, reading.getLongitude());
				stmt.setInt(7, reading.getElevationFt());
				stmt.setString(8, reading.getObserver());
				stmt.setString(9, reading.getStationId());
				stmt.setDate(10, new java.sql.Date(reading.getObservationTime().getTime()));
				stmt.setString(11, reading.getWeatherStr());
				stmt.setFloat(12, reading.getTempF());
				stmt.setInt(13, reading.getRelativeHumidityPercent());
				stmt.setString(14, reading.getWindStr());
				stmt.setString(15, reading.getWindDirStr());
				stmt.setInt(16, reading.getWindDeg());
				stmt.setFloat(17, reading.getWindMph());
				stmt.setFloat(18, reading.getWindGustMph());
				stmt.setInt(19, reading.getPressureMb());
				stmt.setFloat(20, reading.getPressureIn());
				stmt.setString(21, reading.getPressureTrend());
				stmt.setInt(22, reading.getDewpointDegF());
				stmt.setInt(23, reading.getWindChillDegF());
				stmt.setInt(24, reading.getHeatIndexDegF() == null ? 0 : reading.getHeatIndexDegF());
				stmt.setInt(25, reading.getFeelsLikeDegF());
				stmt.setFloat(26, reading.getVisibilityMi());
				stmt.setInt(27, reading.getSolarRad() == null ? 0 : reading.getSolarRad());
				stmt.setFloat(28, reading.getUvRad());
				stmt.setFloat(29, reading.getPercipOneHourIn());
				stmt.setFloat(30, reading.getPercipDayIn());
				stmt.setString(31, reading.getIconStr());
				
				stmt.executeUpdate();
			conndb.close();
			log.info("Save Complete.");
		} catch (SQLException e) {
			log.error("Unable to store record in database; Will try again next record pull!", e); 
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.firebirdcss.climate_collector.collectors.Collector#shutdown()
	 */
	@Override
	public void shutdown() {
		this.running = false;
		this.interrupt();
	}
}
