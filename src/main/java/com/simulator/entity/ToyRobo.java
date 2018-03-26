package com.simulator.entity;

import org.apache.log4j.Logger;

import com.simulator.util.Directions;

/**
 * @author Sandeep.Bheemaraju
 * This class represents ToyRobo. Only Commander can execute the commands on ToyRobo.
 * ToyRobo is aware of the Table top length and its current location, facing direction.   
 */
public class ToyRobo 
{
	static Logger logger = Logger.getLogger(ToyRobo.class);
	
	//xCordinate location of ToyRobo
	private int xCordinate;
	
	//X-Axis length of Table top
	private int xLength;
	
	//yCordinate location of ToyRobo
	private int yCordinate;
	
	//Y-Axis length of Table top
	private int yLength;
	
	//facing direction of ToyRobo
	private Directions faceDirection;
	
	//Attribute which tells whether a ToyRobo is placed on Table top or not
	private boolean isToyPlaced = false;

	//ToyRobo is aware of Table top dimensions
	public ToyRobo(int xLength, int yLength)
	{
		//xLength-1 as xcoordinates start from 0
		this.xLength = xLength-1;
		//yLength-1 as ycoordinates start from 0
		this.yLength = yLength-1;
	}
	
	public boolean isToyPlaced() 
	{
		return isToyPlaced;
	}

	
	/**
	 * place method places the ToyRobo when the coordinates are with in table top
	 * when placed, marks isToyPlaced to true else false
	 * @param xCordinate
	 * @param yCordinate
	 * @param faceDirection
	 */
	public void place(String xCordinate, String yCordinate, String faceDirection)
	{
		this.xCordinate = Integer.parseInt(xCordinate);
		this.yCordinate = Integer.parseInt(yCordinate);
		this.faceDirection = Directions.valueOf(faceDirection.toUpperCase());
		
		if(isValidCordinates())
		{
			isToyPlaced = true;
			logger.info("Toy placed at ["+xCordinate+","+yCordinate+"] location, facing direction ["+faceDirection+"]");
		}
		else
		{
			isToyPlaced = false;
			logger.info("Toy placed at ["+xCordinate+","+yCordinate+"] Invalid location, facing direction ["+faceDirection+"]");
		}
	}
	
	
	/**
	 * report method checks whether ToyRobo is placed on the Table top or not
	 * If ToyRobo was not placed on the Table top, command will be ignored
	 * @return XCoordinate,YCoordinate,Direction of the ToyRobo
	 */
	public String report()
	{
		String report = null;
		
		if(isToyPlaced)
		{
			report = "Output       : "+xCordinate+","+yCordinate+","+faceDirection;
			logger.info(report);
		}
		else
			logger.info("Toy yet to be placed on the board");
				
		return report;
	}
	
	
	/**
	 * rotateLeft method checks whether ToyRobo is placed on the Table top or not
	 * When placed, changes the face direction to the left of current direction 
	 * If ToyRobo was not placed on the Table top, command will be ignored
	 */
	public void rotateLeft()
	{		
		if(isToyPlaced)
		{
			this.faceDirection = faceDirection.left();
			logger.info("Toy rotated to direction ["+faceDirection+"]");
		}
		else
			logger.info("Toy yet to be placed on the board");
	}
	
	
	/**
	 * rotateRight method checks whether ToyRobo is placed on the Table top or not
	 * When placed, changes the face direction to the right of current direction
	 * If ToyRobo was not placed on the Table top, command will be ignored
	 */
	public void rotateRight()
	{		
		if(isToyPlaced)
		{
			this.faceDirection = faceDirection.right();
			logger.info("Toy rotated to direction ["+faceDirection+"]");
		}
		else
			logger.info("Toy yet to be placed on the board");
	}
	
	
	/**
	 * move method checks whether ToyRobo is placed on the Table top or not
	 * If ToyRobo is placed, moves forward in the facing direction by 1 unit
	 * When border is reached, ignores move command
	 */
	public void move()
	{
		if(isToyPlaced)
		{
			switch(faceDirection)
			{
				case EAST:
					if(xCordinate+1 <= xLength)
					{
						xCordinate++;
						logger.info("Toy Moved from ["+(xCordinate-1)+","+yCordinate+"] to ["+xCordinate+","+yCordinate+"] location");
					}
					else
						logger.info("Toy is at boundary location ["+xCordinate+","+yCordinate+"] and will not move further in ["+faceDirection+"] direction");
					break;
					
				case WEST:
					if(xCordinate-1 >= 0)
					{
						xCordinate--;
						logger.info("Toy Moved from ["+(xCordinate+1)+","+yCordinate+"] to ["+xCordinate+","+yCordinate+"] location");
					}
					else
						logger.info("Toy is at boundary location ["+xCordinate+","+yCordinate+"] and will not move further in ["+faceDirection+"] direction");
					break;
					
				case NORTH:
					if(yCordinate+1 <= yLength)
					{
						yCordinate++;
						logger.info("Toy Moved from ["+(xCordinate)+","+(yCordinate-1)+"] to ["+(xCordinate)+","+yCordinate+"] location");
					}
					else
						logger.info("Toy is at boundary location ["+(xCordinate)+","+yCordinate+"] and will not move further in ["+faceDirection+"] direction");
					break;
					
				case SOUTH:
					if(yCordinate-1 >= 0)
					{
						yCordinate--;
						logger.info("Toy Moved from ["+(xCordinate)+","+(yCordinate+1)+"] to ["+(xCordinate)+","+yCordinate+"] location");
					}
					else
						logger.info("Toy is at boundary location ["+(xCordinate)+","+yCordinate+"] and will not move further in ["+faceDirection+"] direction");
					break;
			}
		}	
	}
	
	
	/**
	 * isValidCordinates method checks for the valid coordinates based on the Table top dimensions
	 * if coordinates fall with in Table top dimensions, returns true else false
	 * When coordinates are not valid, ToyPlaced attribute is set to false
	 * @return - whether the coordinates are valid or not
	 */
	private boolean isValidCordinates()
	{
		boolean isValid = false;
		
		if(this.xCordinate >= 0 && this.xCordinate <= xLength && this.yCordinate >= 0 && this.yCordinate <= yLength)
			isValid = true;
		
		if(!isValid)
			this.isToyPlaced = false;
		
		return isValid;
	}
}
