/**
 * All rights reserved, Copyright 2018 by Scott Griffis
 */
package com.firebirdcss.climate_collector;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.core.Application;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.firebirdcss.climate_collector.collectors.CollectorManager;
import com.firebirdcss.climate_collector.collectors.WeatherCollectionAgent;
import com.firebirdcss.climate_collector.utils.ParsingUtilities;

/**
 * This is the main class of the application.
 * <p>
 * The application starts it's execution within the main method of this class.
 * 
 * @author Scott Griffis
 *
 */
public class ApplicationMain {
	private static final Logger log = LogManager.getLogger(Application.class);
	private static boolean running = false;
	private static final CollectorManager collectorManager = new CollectorManager();
	
	/**
	 * APPLICATION MAIN: The main entry point of the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		running = true;
		
		Thread.currentThread().setName("ClimateCollector");
		File propFile = new File(InternalProperties.DEFAULT_PROPERTIES_FILE_PATH);
		Properties tempProp = new Properties();
		if (propFile.exists() && propFile.isFile() && propFile.canRead()) { // Use the file from this location if available...
			try (InputStream fis = new FileInputStream(propFile)) {
				tempProp.load(fis);
			} catch (Exception e) {
				log.fatal("Unable to load the Applicaiton's Property file at location ''; Application will now terminate!", e);
				
				return;
			}
		} else { // Assume file is already on the class-path...
			try (InputStream is = ApplicationMain.class.getClassLoader().getResourceAsStream("application.properties");) {
				tempProp.load(is);
			} catch (Exception e) {
				log.fatal("Unable to load the Applicaiton's Property file from class-path; Application will now terminate!", e);
				
				return;
			}
		}
		InternalProperties.propertyFile = tempProp;
		
		/* Load cycle interval milliseconds from file if exists otherwise use default value */
		String tempValue = InternalProperties.propertyFile.getProperty(InternalProperties.PROPERTY_STATS_PULL_FREQ_MILLIS, String.valueOf(InternalProperties.DEFAULT_STATS_PULL_FREQ_MILLIS));
		long cycleMillis = InternalProperties.DEFAULT_STATS_PULL_FREQ_MILLIS;
		if (ParsingUtilities.isNumeric(tempValue)) {
			cycleMillis = Long.parseLong(tempValue);
			log.info("Using the value of '" + tempValue + "' for the '" + InternalProperties.PROPERTY_STATS_PULL_FREQ_MILLIS + "' property.");
		} else {
			log.warn("There was a problem loading the property '" + InternalProperties.PROPERTY_STATS_PULL_FREQ_MILLIS + "' from the property file; Using the default value of '" + InternalProperties.DEFAULT_STATS_PULL_FREQ_MILLIS + "'"); 
		}
		
		String apiUrl = InternalProperties.propertyFile.getProperty(InternalProperties.PROPERTY_WEATHER_API_URL);
		if (apiUrl != null) {
			if (apiUrl.contains("@(")) {
				apiUrl = replacePropertyVars(apiUrl);
			};
		} else {
			log.fatal("Could not find a value for the API URL to obtain data from!");
			
			return;
		}
		
		/* Register application's primary shutdown-hook */
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
		/* Register desired Collectors */
		collectorManager.registerAndStart("WeatherCollector", (new WeatherCollectionAgent(cycleMillis, apiUrl)));
		
		/* Enter holding patter until end of the application */
		while (running) {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException ignoreAndMoveOn) {}
		}
	}
	
	private static String replacePropertyVars(String input) {
		if (input != null) {
			int varStartIndex = input.indexOf("@(");
			if (varStartIndex != -1) {
				varStartIndex += 2;
				int varEndIndex = input.indexOf(')', varStartIndex);
				if (varEndIndex != -1) {
					String tempString = input.substring(0, varStartIndex - 2);
					tempString += InternalProperties.propertyFile.getProperty(input.substring(varStartIndex, varEndIndex));
					tempString += input.substring(varEndIndex + 1, input.length());
					if (tempString.contains("@(")) {
						tempString = replacePropertyVars(tempString);
					}
					
					return tempString;
				}
			}
			
			return input;
		}
		
		return null;
	}
	
	/**
	 * Application's primary ShutdownHook.
	 * 
	 * @author Scott Griffis
	 *
	 */
	private static class ShutdownHook extends Thread {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			running = false;
		}
	}
}