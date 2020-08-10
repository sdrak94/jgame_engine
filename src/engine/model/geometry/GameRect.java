package engine.model.geometry;


import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import engine.model.interfaces.ILocationable;
import engine.model.interfaces.ISizeable;

public class GameRect implements ILocationable, ISizeable
{
	private int z;
	
	private final Rectangle _rectangle = new Rectangle();
	
	public GameRect()
	{
		this(0, 0, 0, 0, 0);
	}
	
	public GameRect(final int locationX, final int locationY, final int locationZ, final int sizeX, final int sizeY)
	{
		_rectangle.x = locationX;
		_rectangle.y = locationY;
		z 			 = locationZ;
		_rectangle.width =  sizeX;
		_rectangle.height = sizeY;
	}
	
	@Override
	public void setLocationX(int x)
	{
		_rectangle.x = x;
	}

	@Override
	public void setLocationY(int y) 
	{
		_rectangle.y = y;
	}

	@Override
	public void setLocationZ(int z)
	{
		this.z = z;
	}

	@Override
	public int getLocationX()
	{
		return _rectangle.x;
	}

	@Override
	public int getLocationY()
	{
		return _rectangle.y;
	}

	@Override
	public int getLocationZ()
	{
		return this.z;
	}

	@Override
	public void setSizeX(int x)
	{
		_rectangle.width = x;
	}

	@Override
	public void setSizeY(int y)
	{
		_rectangle.height = y;
	}

	@Override
	public int getSizeX()
	{
		return _rectangle.width;
	}

	@Override
	public int getSizeY()
	{
		return _rectangle.height;
	}
	
	public void transform(final AffineTransform affineTransform)
	{
		final Area area = new Area();
		area.transform(affineTransform);
		_rectangle.setBounds(area.getBounds());
	}

	public boolean contains(int x, int y)
	{
		return _rectangle.contains(x, y);
	}

	public boolean intersects(final Rectangle rectangle) 
	{
		return _rectangle.intersects(rectangle);
	}
	
	public boolean intersects(final GameRect gameRect)
	{
		return _rectangle.intersects(gameRect._rectangle);
	}
}
