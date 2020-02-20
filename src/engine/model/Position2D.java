package engine.model;

public class Position2D 
{
	private final int _x;
	private final int _y;
	
	public Position2D(int x, int y)
	{
		_x = x;
		_y = y;
	}
	
	public int getX()
	{
		return _x;
	}
	
	public int getY()
	{
		return _y;
	}
}
