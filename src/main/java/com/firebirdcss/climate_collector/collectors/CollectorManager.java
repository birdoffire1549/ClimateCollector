/**
 * All rights reserved, Copyright 2018 by Scott Griffis
 */
package com.firebirdcss.climate_collector.collectors;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages {@link Collector}s which are registered with this class.
 * 
 * @author Scott Griffis
 *
 */
public class CollectorManager {
	private static final Map<String/*Key*/, Collector> registeredCollectors = new HashMap<>();
	
	/**
	 * Register a new collector.
	 * 
	 * @param key
	 * @param collector
	 * @return
	 */
	public Collector registerAndStart(String key, Collector collector) {
		collector.start();
		
		return registeredCollectors.put(key, collector);
	}
	
	/**
	 * Causes the graceful shutdown of all registered {@link Collector}s.
	 * 
	 */
	public void shutdown() {
		registeredCollectors.forEach((key, collector) -> collector.shutdown());
	}
}
