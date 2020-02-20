package engine.io.out.graphics.render;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.EnumSet;

import engine.model.enums.ItemState;
import engine.model.interfaces.ILocationable;

public abstract class RenderableItem implements ILocationable, Comparable<RenderableItem>
{
	protected final static AlphaComposite CLEAR_ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
	
	protected final EnumSet<ItemState> knownStates = EnumSet.of(ItemState.IDLE);
	
	protected int _locX;
	protected int _locY;
	protected int _locZ;
	
	protected double _rotationRads;
	
	protected AlphaComposite _alpha;
	
	protected boolean clickable = true;
	
	protected float _scaleX = 1f;
	protected float _scaleY = 1f;

	protected final int _width;
	protected final int _heigh;
	
	public RenderableItem(int width, int heigh)
	{
		_width = width;
		_heigh = heigh;
	}
	
	public final void renderInit(Graphics2D g, int offsetX, int offsetY)
	{
		final int currX = getX() - offsetX, currY = getY() - offsetY; 
		final float scaleX = getScaleX(), scaleY = getScaleY();

		if (scaleX != 1f || scaleY != 1f)
		{
			g.translate(currX, currY);
			g.scale(scaleX, scaleY);
			g.translate(-currX, -currY);
		}
		if (_rotationRads != 0)
		{
			g.translate(currX, currY);
			g.rotate(_rotationRads);
			g.translate(-currX, -currY);
		}
		if (_alpha != null)
		{
			g.setComposite(_alpha);
			render(g, currX, currY);
			g.setComposite(CLEAR_ALPHA);
		}
		else
			render(g, currX, currY);
		
		renderState(g, currX, currY);
	}
	
	public void renderState(Graphics2D g, int currX, int currY)
	{
		if (knownStates.contains(ItemState.CLICKED))
		{
			g.setColor(new Color(255,255,255,160));
			g.fillRect(currX + _width, currY, 34, 20);
			g.setColor(Color.RED);
			g.drawRect(currX, currY, _width - 1, _heigh - 1);
			g.setColor(new Color(255, 0, 0, 120));
			g.drawRect(currX + _width - 1, currY, 34, 20);
			g.drawLine(currX, currY, currX + _width - 1, _heigh + currY - 1);
			g.drawLine(currX + _width, currY, currX, currY + _heigh);
			g.setColor(new Color(255, 0, 0, 30));
			g.fillRect(currX, currY, _width, _heigh);
			
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("Calibri", 5, 15));
			g.drawString("Z: " + getZ(), currX + _width, currY + 15);
		}
	}
	
	public abstract void render(Graphics2D g, int locX, int locY);
	
	public void setLocation(int x, int y, int z)
	{
		_locX = x;
		_locY = y;
		_locZ = z;
	}
	
	public void setLocation(int x, int y)
	{	
		_locX = x;
		_locY = y;
	}
	
	public void setX(int locX)
	{
		_locX = locX;
	}
	
	public void setY(int locY)
	{
		_locY = locY;
	}
	
	public void setZ(int locZ)
	{
		_locZ = locZ;
	}
	
	public void setAlpha(float alpha)
	{
		_alpha =alpha > .99 ? null : AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	}
	
	public void setScale(float scaleX, float scaleY)
	{
		_scaleX = scaleX;
		_scaleY = scaleY;
	}
	
	public void incScale(float scale)
	{
		_scaleX += scale;
		_scaleY += scale;
	}
	
	public void setScaleX(float scaleX)
	{
		_scaleX = scaleX;
	}
	
	public void setScaleY(float scaleY)
	{
		_scaleY = scaleY;
	}
	
	public void setRotationDegrees(double rotationDegrees)
	{
		_rotationRads = Math.toRadians(rotationDegrees);
	}
	
	public void incRotation(double inc)
	{
		_rotationRads += Math.toRadians(inc);
	}
	
	public double getRotationDegrees()
	{
		return Math.toDegrees(_rotationRads);
	}
	
	public float getScaleX()
	{
		return knownStates.contains(ItemState.HOVER) ? _scaleX + .3f : _scaleX;
	}
	
	public float getScaleY()
	{
		return knownStates.contains(ItemState.HOVER) ? _scaleY + .3f : _scaleY;
	}
	
	public int getX()
	{
		return _locX;
	}
	
	public int getY()
	{
		return _locY;
	}
	
	public int getZ()
	{
		return _locZ;
	}
	
	public int getWidth()
	{
		return (int) (_width * _scaleX);
	}
	
	public int getHeight()
	{
		return (int) (_heigh * _scaleY);
	}
	
	public boolean contains(int x, int y)
	{
		final Rectangle r = new Rectangle(_locX, _locY, getWidth(), getHeight());
		return r.contains(x, y);
	}
	
	public void translate(int x, int y)
	{
		final Rectangle r = new Rectangle(_locX, _locY, getWidth(), getHeight());
		r.translate(x, y);
		setLocation((int)r.getX(), (int)r.getY());
	}
	
	public Rectangle toRectangle()
	{
		return new Rectangle(_locX, _locY, getWidth(), getHeight());	
	}
	
	public void setItemState(ItemState newState, boolean set)
	{
		if (set)
			knownStates.add(newState);
		else
			knownStates.remove(newState);
	}
	
	public void setClickable(boolean bool)
	{
		clickable = bool;
	}
	
	public boolean isClickable()
	{
		return clickable;
	}
	
	@Override
	public int compareTo(RenderableItem rend2)
	{
		return getZ() - rend2.getZ();
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ",x=" + getX() + ",y=" + getY() + ",z=" + getZ();
	}
//	
//	public boolean isRenderable(int offsetX, int offsetY)
//	{
//		return _locX + _heigh > offsetX && _locY + _width > offsetY;
//	}
	
	public boolean isRenderable(final Rectangle screenRectangle)
	{
		final Rectangle r = new Rectangle(_locX, _locY, getWidth(), getHeight());
		return r.intersects(screenRectangle);
//		return _locX + _heigh > offsetX && _locY + _width > offsetY;
	}
}
