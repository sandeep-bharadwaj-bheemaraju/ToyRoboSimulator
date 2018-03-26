package com.simulator.controller;

import org.apache.log4j.Logger;

import com.simulator.entity.ToyRobo;
import com.simulator.util.Directions;

/**
 * @author Sandeep.Bheemaraju
 * This class is the interface between Client and the Toy Robo.
 * Issued commands will be executed through Commander.
 */
public class Commander 
{
	private static Logger logger = Logger.getLogger(Commander.class);
	
    private static final String NUMBER_REGEX           = "\\d+";
    
    //List of Commands
	private static final String PLACE                  = "PLACE";
	private static final String MOVE                   = "MOVE";
	private static final String LEFT                   = "LEFT";
	private static final String RIGHT                  = "RIGHT";
	private static final String REPORT                 = "REPORT";
	private static final String HELP                   = "HELP";
	private static final String STOP                   = "STOP";
	
	private ToyRobo toy;
	private boolean isRunning = false;
	
	public Commander(String tableSize)
	{		
		String[] coordinates = tableSize.split("X");
		
		//Validate table size and the coordinates
		if(null != tableSize && coordinates.length == 2 && isNumberOrString(coordinates[0]) && isNumberOrString(coordinates[1]))
		{
			toy = new ToyRobo(Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1]));
			System.out.println("");
			System.out.println("                         ###");
			System.out.println("                        (o o)");
			System.out.println("                    ooO--(_)--Ooo-");
			System.out.println("******************************************************");
			System.out.println("          R O B O   T O Y   S I M U L A T O R         ");
			System.out.println("******************************************************");
			System.out.println("");
			System.out.println("Robo Toy Simulator Started - Runs on "+tableSize+" tabletop");
			System.out.println("Type help for a list of commands!");
			System.out.println("");
			
			//When configuration is all set, mark isRunning to true
			isRunning = true;
		}
		else
		{
			//For invalid configuration, Help user to provide valid configuration
			System.err.println("Please check configuration in toy.robo.simulator.properties");
			System.err.println("Tabletop size should be NummberXNumber");
			System.err.println("Number should be positive and non-decimal");
			System.err.println("For example, 5X5");
			System.err.println("Please change the configuration and restart simulator");
		}
	}
	
	//Provides state of commander
	public boolean isRunning() 
	{
		return isRunning;
	}

	
	/**
	 * The execute method does the initial processing of the command and matches it against case to execute command on the toy 
	 * @param input - holds the command provided by the client
	 * @return result
	 */
	public String execute(String input)
	{
		String result = "";
		
		logger.info(" Command is : "+input);
		
		String command[] = input.split(" ");
		
		switch(command[0].toUpperCase())
		{
			case PLACE:
				logger.info("Executing PLACE command");
				place(command);
				break;
				
			case MOVE:
				logger.info("Executing MOVE command");
				move(command);
				break;
				
			case LEFT:
				logger.info("Executing LEFT command");
				left(command);
				break;
				
			case RIGHT:
				logger.info("Executing RIGHT command");
				right(command);
				break;
				
			case REPORT:
				logger.info("Executing REPORT command");
				result = report(command);
				System.out.println("");
				break;
			
			case HELP:
				help(command);
				break;
				
			case STOP:
				if(isCommandValid(command))
				{
					isRunning = false;
					System.out.println("");
					System.out.println("Robo Toy Simulator Stopped");
					System.out.println("");
				}
				else
					help(command);
				break;
				
			default:
				System.out.println("Output       : Unknown command - Type HELP for a list of commands");
				System.out.println("");
		}
		
		logger.info("");
		return result;
	}
	
	
	
	/**
	 * place method expects command to be provided in format PLACE XCoordinate,YCoordinate,Direction
	 * Validates -
	 * 	1. XCoordinate and YCoordinate should be non-decimal positive number
	 * 	2. Direction can be EAST,WEST,NORTH,SOUTH
	 * When command is invalid, displays syntax for the command
	 * When command is valid and coordinates fall with in the table top, then toy robo will be placed successfully
	 * @param command - holds the command provided by the client
	 */
	private void place(String ...command)
	{
		boolean isCommandValid = false;
		
		if(command.length == 2)
		{			
			String[] options = command[1].split(",");
			
			if(options.length == 3)
			{
				if(isNumberOrString(options[0]) && isNumberOrString(options[1]) && !isNumberOrString(options[2]))
				{
					if(options[2].toUpperCase().equals(Directions.NORTH.toString()) ||
					   options[2].toUpperCase().equals(Directions.EAST.toString()) ||
					   options[2].toUpperCase().equals(Directions.WEST.toString()) ||
					   options[2].toUpperCase().equals(Directions.SOUTH.toString()))
					{
						isCommandValid = true;
					}
				}				
			}
			
			//Place the toy here
			if(isCommandValid)
			{
				logger.info("Command valid format..");
				toy.place(options[0], options[1], options[2]);
			}			
		}
		
		if(!isCommandValid)
			help(command);
	}
	
	
	/**
	 * report validates command for the syntax and checks if toy was placed
	 * if report is executed without a valid place command, report command execution will be ignored
	 * @param command - holds the command provided by the client
	 * @return report XCoordinate,YCoordinate,Direction
	 */
	private String report(String ...command)
	{
		String report = "";
		
		if(isCommandValid(command) && toy.isToyPlaced())
		{
			report = toy.report();
			
			if(null != report)
				System.out.println(report);
		}
		
		return report;
	}
	
	
	/**
	 * left validates command for the syntax and checks if toy was placed
	 * if left is executed without a valid place command, left command execution will be ignored
	 * @param command - holds the command provided by the client
	 */
	private void left(String ...command)
	{		
		if(isCommandValid(command) && toy.isToyPlaced())
			toy.rotateLeft();
	}
	
	
	/**
	 * right validates command for the syntax and checks if toy was placed
	 * if right is executed without a valid place command, right command execution will be ignored
	 * @param command - holds the command provided by the client
	 */
	private void right(String ...command)
	{		
		if(isCommandValid(command) && toy.isToyPlaced())
			toy.rotateRight();
	}
	
	
	/**
	 * move validates command for the syntax and checks if toy was placed
	 * if move is executed without a valid place command, move command execution will be ignored
	 * @param command - holds the command provided by the client
	 */
	private void move(String ...command)
	{		
		if(isCommandValid(command) && toy.isToyPlaced())
			toy.move();
	}

	
	/**
	 * isCommandValid checks for the valid length
	 * When command is invalid, displays syntax for the command  
	 * @param command - holds the command provided by the client
	 * @return
	 */
	private boolean isCommandValid(String... command) 
	{
		boolean isCommandValid = false;
		
		if(command.length == 1)
			isCommandValid = true;
		
		if(!isCommandValid)
			help(command);
		
		return isCommandValid;
	}
	
	
	/**
	 * isNumberOrString checks whether the provided option is Number or String
	 * when option is number, returns true
	 * when option is string, returns false
	 * @param option
	 * @return
	 */
	private boolean isNumberOrString(String option)
	{		
		return option.matches(NUMBER_REGEX);
	}
	
	
	/**
	 * help prints the syntax of provided command or provides help to the client to list commands that can be executed
	 * @param command
	 */
	private void help(String ...command) 
	{
		switch(command[0].toUpperCase())
		{
			case PLACE:
					System.out.println("  PLACE command format is - PLACE <Number>,<Number>,<Direction>");
					System.out.println("  Number cannot be a decimal and negitive");
					System.out.println("  Direction can be - NORTH, EAST, WEST, SOUTH");
					System.out.println("  For Example, PLACE 1,1,EAST");
					System.out.println("");
					break;
					
			case MOVE:
					System.out.println("  MOVE command format is - MOVE");
					System.out.println("  For Example, MOVE");
					System.out.println("");
					break;

			case LEFT:
					System.out.println("  LEFT command format is - LEFT");
					System.out.println("  For Example, LEFT");
					System.out.println("");
					break;
				
			case RIGHT:
					System.out.println("  RIGHT command format is - RIGHT");
					System.out.println("  For Example, RIGHT");
					System.out.println("");
					break;
				
			case REPORT:
					System.out.println("  REPORT command format is - REPORT");
					System.out.println("  For Example, REPORT");
					System.out.println("");
					break;
					
			case STOP:
					System.out.println("  STOP command format is - STOP");
					System.out.println("  For Example, STOP");
					System.out.println("");
					break;
					
			case HELP:
					System.out.println("");
					System.out.println("  The Toy Robot understands the following commands");
					System.out.println("  1. PLACE command   -   Places Toy in specified location in the given direction");
					System.out.println("  2. MOVE command    -   Moves Toy by one unit in the current direction");
					System.out.println("  3. LEFT command    -   Rotates Toy to the left of current direction");
					System.out.println("  4. RIGHT command   -   Rotates Toy to the right of current direction");
					System.out.println("  5. REPORT command  -   Reports current Toy location and direction");
					System.out.println("  6. STOP command    -   Stops the Simulator");
					System.out.println("");
					break;
		}        
	}
	
}
