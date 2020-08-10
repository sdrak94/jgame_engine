package engine.model.interfaces;

public interface ILocationable
{
	public void setLocationX(int x);
	public void setLocationY(int y);
	public void setLocationZ(int z);
	
	public int getLocationX();
	public int getLocationY();
	public int getLocationZ();
	

	public default void setLocation(int x, int y)
	{
		setLocationX(x);
		setLocationY(y);
	}
	
	public default void setLocation(int x, int y, int z)
	{
		setLocationX(x);
		setLocationY(y);
		setLocationZ(z);
	}

	public default void incX(int x)
	{
		setLocationX(getLocationX() + x);
	}
	
	public default void incY(int y)
	{
		setLocationY(getLocationY() + y);
	}
	
	public default void incZ(int z)
	{
		setLocationZ(getLocationZ() + z);
	}

	public default void decX(int x)
	{
		setLocationX(getLocationX() - x);
	}
	
	public default void decY(int y)
	{
		setLocationY(getLocationY() - y);
	}
	
	public default void decZ(int z)
	{
		setLocationZ(getLocationZ() - z);
	}
}
