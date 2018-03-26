package com.simulator.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class CommanderTest 
{
	private Map<String,List<String>> testCaseMap = new LinkedHashMap<String,List<String>>();
	private Commander commander;
	
	@Before
	public void setup() 
	{
		Scanner scanner = null;
		List<String> commandList = null;
		commander = new Commander("5X5");
		
		File directory = new File("./src/test/resources");
		
        for (File file : directory.listFiles())
        {
        	try 
        	{
				scanner = new Scanner(file);
				commandList = Arrays.asList(scanner.useDelimiter("\\Z").next().split("\\n"));
				testCaseMap.put(file.getName(), commandList);
			} 
        	catch (FileNotFoundException e) 
        	{
				e.printStackTrace();
			}
        }
        
        if(null != scanner)
        	scanner.close();
	}
	
	@Test
	public void tc1_executeTest() throws FileNotFoundException
	{
		List<String> commandList = testCaseMap.get("tc1.txt");
		String result = "";
		
		for(String command : commandList)
			result = commander.execute(command.trim());
		
		org.junit.Assert.assertTrue((result.contains("0,1,NORTH")));
	}
	
	@Test
	public void tc2_executeTest() throws FileNotFoundException
	{
		List<String> commandList = testCaseMap.get("tc2.txt");
		String result = "";
		
		for(String command : commandList)
			result = commander.execute(command.trim());
		
		org.junit.Assert.assertTrue((result.contains("0,0,WEST")));
	}
	
	
	@Test
	public void tc3_executeTest() throws FileNotFoundException
	{
		List<String> commandList = testCaseMap.get("tc3.txt");
		String result = "";
		
		for(String command : commandList)
			result = commander.execute(command.trim());
		
		org.junit.Assert.assertTrue((result.contains("3,3,NORTH")));
	}
	
	@Test
	public void tc4_executeTest() throws FileNotFoundException
	{
		List<String> commandList = testCaseMap.get("tc4.txt");
		String result = "";
		
		for(String command : commandList)
			result = commander.execute(command.trim());
		
		org.junit.Assert.assertTrue((result.contains("")));
	}
	
	@Test
	public void tc5_executeTest() throws FileNotFoundException
	{
		List<String> commandList = testCaseMap.get("tc5.txt");
		String result = "";
		
		for(String command : commandList)
			result = commander.execute(command.trim());
		
		org.junit.Assert.assertTrue((result.contains("4,0,NORTH")));
	}
}
