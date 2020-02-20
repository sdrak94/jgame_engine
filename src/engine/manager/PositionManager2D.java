package engine.manager;

import static creator.init.CreatorInit.TILE_CONST;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import engine.model.Position2D;

public class PositionManager2D 
{
	private final int _initX;
	private final int _initY;
	
	private int swit;

	private int currentX;
	private int currentY;
	private int maxX;
	private int maxY;
	
	private int mode;
	private boolean forceMode;
	
	public PositionManager2D(int initX, int initY, int swit)
	{
		_initX = initX;
		_initY = initY;
		
		currentX = initX;
		currentY = initY;
	}
	
	public PositionManager2D(int initX, int initY, final BufferedImage image)
	{
		this(initX, initY, image.getWidth() / TILE_CONST );
	}
	
	public PositionManager2D(int swit)
	{
		_initX = 0;
		_initY = 0;
	}
	
	public Position2D getNext()
	{
		final Position2D pos2d = new Position2D(currentX, currentY);
		if (forceMode || swit != 0 && ++mode % swit == 0)
		{	
			maxX = currentX;
			currentX = _initX;
			currentY += TILE_CONST + 1;
			forceMode = false;
		}
		else
			currentX += TILE_CONST + 1;
		return pos2d;
	}
	
	public void forceMode()
	{
		forceMode = true;
	}
	
	public int getScaleX()
	{
		return maxX + 20;
	}
	
	public int getScaleY()
	{
		return currentY + 20;
	}
	
	public Dimension getPrefferedDimension()
	{
		return new Dimension(maxX * 2, currentY + 20);
	}
	
	public void reset()
	{
		mode = 0;
		currentX = _initX;
		currentY = _initY;
	}
	
	public void setMaxWidth(final int width)
	{
		swit = width / 18 +1;
	}
}
