package com.simulator.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;


/** 
 * PropertyReader is a utility class that provides the operation to read configuration from a property file
 * @author Sandeep.Bheemaraju
 *
 */
public enum PropertyReader 
{
	INSTANCE;
	
	static Logger logger = Logger.getLogger(PropertyReader.class.getName());
	
	/**
	 * @param propertyFileName
	 * @return - Map of Unmodifiable configuration attributes
	 * @throws Exception
	 */
	public Map loadProperty(String propertyFileName) throws Exception
	{
		Hashtable<String, String> propertyHashtable = new Hashtable<String, String>();
		
		logger.info("loading Property File : "+propertyFileName);
		
		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		Map<String,String> propertyMap = null;
		
		try 
		{
			fileInputStream = new FileInputStream(propertyFileName);
			properties.load(fileInputStream);
			Set<?> keys = properties.keySet();
			
			for (Iterator<?> iterator = keys.iterator(); iterator.hasNext();) 
			{
				String propertyKey = (String) iterator.next();
				propertyHashtable.put(propertyKey, (String) properties.get(propertyKey));
			}
			
			fileInputStream.close();
		} 
		catch (FileNotFoundException fnfex) 
		{
			logger.error("Property File not found "+propertyFileName);
			throw fnfex;
		} 
		catch (IOException ioex) 
		{
			logger.error("IOException occured while loading Property "+propertyFileName);
			throw ioex;
		} 
		catch (Exception ex) 
		{
	    	logger.error("Unknown Exception occured while loading Property "+ex.getMessage());
	    	throw ex;
		} 
		finally 
		{
			if (fileInputStream != null) 
			{
				try 
				{
					fileInputStream.close();
		        } 
				catch (Exception ex) 
				{
					// nothing to do here as we are in a finally clause
		        } 
		    }
		}
		
		propertyMap = new LinkedHashMap(properties);
		
		return Collections.unmodifiableMap(propertyMap);
	}  
}
