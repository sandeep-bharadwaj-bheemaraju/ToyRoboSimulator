package com.simulator;

import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.simulator.controller.Commander;
import com.simulator.util.LogAppUtil;
import com.simulator.util.PropertyReader;

/**
 * @author Sandeep.Bheemaraju
 * This class is the main entry class for the Toy Robo Simulator.  
 * It loads log4j configuration and simulator configuration.
 */
public class ToyRoboSimulator 
{
	static Logger logger = Logger.getLogger(ToyRoboSimulator.class);
	
	public static void main(String[] args) 
	{
		Scanner scanner = null;
		
		try
		{
			//For Development
			LogAppUtil.getInstance("D:\\Sandeep\\Work\\MyWorkspace\\ToyRoboSimulator\\src\\main\\resources\\log4j.properties");
			Map<String,String> propertyMap = PropertyReader.INSTANCE.loadProperty("D:\\Sandeep\\Work\\MyWorkspace\\ToyRoboSimulator\\src\\main\\resources\\toy.robo.simulator.properties");
			
			//For standalone Jar
			//LogAppUtil.getInstance("log4j.properties");
			//Map<String,String> propertyMap = PropertyReader.INSTANCE.loadProperty("toy.robo.simulator.properties");
			
			Commander commander = null;
			
			scanner = new Scanner(System.in);
						
			if(propertyMap.keySet().contains("table.size"))
				commander = new Commander(propertyMap.get("table.size"));
			else
				System.err.println("toy.robo.simulator.properties should contain table.size attribute");
			
			while(null != commander && commander.isRunning())
			{
				System.out.print("Enter Command: ");
				commander.execute(scanner.nextLine());
			}
		}
		catch(Exception ex)
		{
			System.err.println("Please check if toy.robo.simulator.properties is available or not");
			logger.error(ex);
			ex.printStackTrace();
		}
		finally
		{
			if(null != scanner)
				scanner.close();
		}
	}
}
