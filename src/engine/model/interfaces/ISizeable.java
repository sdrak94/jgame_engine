package engine.model.interfaces;

public interface ISizeable
{
	/**
	 *   x----->
	 *   y 
	 *   |
	 *   v
	 */
	
	
	public void setSizeX(final int x);
	public void setSizeY(final int y);
	
	public int getSizeX();
	public int getSizeY();
	
	public default void setSize(final int x, final int y)
	{
		setSizeX(x);
		setSizeY(y);
	}
	
	public default void incSizeX(final int x)
	{
		setSizeX(getSizeX() + x);
	}	
	
	public default void incSizeY(final int y)
	{
		setSizeY(getSizeY() + y);
	}
	
	public default void decSizeX(final int x)
	{
		setSizeX(getSizeX() - x);
	}	
	
	public default void decSizeY(final int y)
	{
		setSizeY(getSizeY() - y);
	}
}
