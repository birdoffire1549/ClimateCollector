/**
 * All rights reserved, Copyright 2018 by Scott Griffis
 */
package com.firebirdcss.climate_collector.collectors;

/**
 * 
 * @author Scott Griffis
 *
 */
public abstract class Collector extends Thread {
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public abstract void run();
	
	/**
	 * Used to shutdown the current collector.
	 * 
	 */
	public abstract void shutdown();
}
