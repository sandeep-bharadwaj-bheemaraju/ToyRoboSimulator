package com.simulator.util;

/**
 * Directions enum has 4 directions EAST,WEST,NORTH,SOUTH 
 * @author Sandeep.Bheemaraju
 */
public enum Directions 
{
	EAST,WEST,NORTH,SOUTH;
	
	/**
	 * @return - left direction of current direction by 90 degrees
	 */
	public Directions left()
	{
		Directions leftDirection = Directions.valueOf(this.toString());
		
		switch(this)
		{
			case EAST:
				leftDirection = Directions.NORTH;
				break;
				
			case WEST:
				leftDirection = Directions.SOUTH;
				break;
				
			case NORTH:
				leftDirection = Directions.WEST;
				break;
				
			case SOUTH:
				leftDirection = Directions.EAST;
				break;
			
			default:
		}
		
		return leftDirection;
	}
	
	
	/**
	 * @return - right direction of current direction by 90 degrees
	 */
	public Directions right()
	{
		Directions rightDirection = Directions.valueOf(this.toString());
		
		switch(this)
		{
			case EAST:
				rightDirection = Directions.SOUTH;
				break;
				
			case WEST:
				rightDirection = Directions.NORTH;
				break;
				
			case NORTH:
				rightDirection = Directions.EAST;
				break;
				
			case SOUTH:
				rightDirection = Directions.WEST;
				break;
			
			default:
		}
		
		return rightDirection;
	}
}
