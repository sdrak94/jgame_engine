package engine.model.interfaces;

import java.awt.Point;

public interface ILocationable
{
	public void setLocation(int x, int y);
	public void setX(int x);
	public void setY(int y);
	public void setZ(int z);
	
	public int getX();
	public int getY();
	public int getZ();
	
	public default Point getLocation(Point p)
	{
		if (p == null)
			return new Point(getX(), getY());
		p.setLocation(getX(), getY());
		return p;
	}
	
}
