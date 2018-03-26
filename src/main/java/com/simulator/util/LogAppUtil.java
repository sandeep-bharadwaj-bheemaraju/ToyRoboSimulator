package com.simulator.util;

import org.apache.log4j.PropertyConfigurator;

/**
 * LogAppUtil initializes the logging framework
 * @author Sandeep.Bheemaraju
 */
public class LogAppUtil 
{
	private static LogAppUtil log;

	private LogAppUtil(String path) 
	{
		PropertyConfigurator.configure(path);
	}

	public static LogAppUtil getInstance(String path) 
	{
		log = new LogAppUtil(path);
		return log;
	}

}
