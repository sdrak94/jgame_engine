package engine.io.out.graphics.render;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.EnumSet;

import engine.model.enums.ItemState;
import engine.model.event.enums.EventType;
import engine.model.geometry.GameRect;
import engine.model.interfaces.ILocationable;
import engine.model.interfaces.IRenderable;
import engine.model.interfaces.ISizeable;

public abstract class RenderableItem implements IRenderable, ILocationable, ISizeable, Comparable<RenderableItem>
{
	protected final static AlphaComposite CLEAR_ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
	
	protected final EnumSet<ItemState> knownStates = EnumSet.of(ItemState.IDLE, ItemState.CLICKABLE, ItemState.MOVEABLE, ItemState.DEBUG_CLICKED, ItemState.DEBUG_HOVERED);
	
	private final GameRect _renderRect = new GameRect();
	
	protected double _rotationRads;
	
	protected AlphaComposite _alpha;
	
//	protected boolean clickable = true;
	
	protected float _scaleX = 1f;
	protected float _scaleY = 1f;

	public RenderableItem(int width, int heigh)
	{
		_renderRect.setSize(width, heigh);
	}
	
	@Override
	public void setLocationX(int x)
	{
		_renderRect.setLocationX(x);
	}
	
	@Override
	public void setLocationY(int y)
	{
		_renderRect.setLocationY(y);
	}
	
	@Override
	public void setLocationZ(int z)
	{
		_renderRect.setLocationZ(z);
	}
	
	@Override
	public int getLocationX()
	{
		return _renderRect.getLocationX();
	}
	
	@Override
	public int getLocationY()
	{
		return _renderRect.getLocationY();
	}
	
	@Override
	public int getLocationZ()
	{
		return _renderRect.getLocationZ();
	}

	@Override
	public void setSizeX(int x)
	{
		_renderRect.setSizeX(x);
	}

	@Override
	public void setSizeY(int y)
	{
		_renderRect.setSizeY(y);
	}

	@Override
	public int getSizeX()
	{
		return 0;
	}

	@Override
	public int getSizeY()
	{
		return 0;
	}
	
	@Override
	public final void renderInit(Graphics2D g, int offsetX, int offsetY)
	{
		if (getItemState(ItemState.SEALED))
		{
			offsetX = 0;
			offsetY = 0;
		}
		
		final int currX = getLocationX() - offsetX, currY = getLocationY() - offsetY; 
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
	
	@Override
	public void renderState(Graphics2D g, int currX, int currY)
	{
		final int width = _renderRect.getSizeX();
		final int heigh = _renderRect.getSizeY();
		
		if (getItemState(ItemState.CLICKED, ItemState.DEBUG_CLICKED))
		{
			g.setColor(new Color(255,255,255,160));
			g.fillRect(currX + width, currY, 34, 20);
			g.setColor(Color.RED);
			g.drawRect(currX, currY, width - 1, heigh - 1);
			g.setColor(new Color(255, 0, 0, 120));
			g.drawRect(currX + width - 1, currY, 34, 20);
			g.drawLine(currX, currY, currX + width - 1, heigh + currY - 1);
			g.drawLine(currX + width, currY, currX, currY + heigh);
			g.setColor(new Color(255, 0, 0, 30));
			g.fillRect(currX, currY, width, heigh);
			
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("Calibri", 5, 15));
			g.drawString("Z: " + getLocationZ(), currX + width, currY + 15);
		}
		
		else if (getItemState(ItemState.HOVERED, ItemState.DEBUG_HOVERED))
		{
			g.setColor(new Color(255, 0, 0, 50));
			g.fillRect(currX, currY, width, heigh);
		}
	}
	
//	public void setLocation(int x, int y, int z)
//	{
//		_locX = x;
//		_locY = y;
//		_locZ = z;
//	}
//	
//	public void setLocation(int x, int y)
//	{	
//		_locX = x;
//		_locY = y;
//	}
//	
//	public void setX(int locX)
//	{
//		_locX = locX;
//	}
//	
//	public void setY(int locY)
//	{
//		_locY = locY;
//	}
//	
//	public void setZ(int locZ)
//	{
//		_locZ = locZ;
//	}
	
	@Override
	public void setAlpha(float alpha)
	{
		_alpha =alpha > .99 ? null : AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	}
	
	@Override
	public void setScale(float scaleX, float scaleY)
	{
		_scaleX = scaleX;
		_scaleY = scaleY;
	}
	
	@Override
	public void incScale(float scale)
	{
		_scaleX += scale;
		_scaleY += scale;
	}
	
	@Override
	public void setScaleX(float scaleX)
	{
		_scaleX = scaleX;
	}
	
	@Override
	public void setScaleY(float scaleY)
	{
		_scaleY = scaleY;
	}
	
	@Override
	public void setRotationDegrees(double rotationDegrees)
	{
		_rotationRads = Math.toRadians(rotationDegrees);
	}
	
	@Override
	public void incRotation(double inc)
	{
		_rotationRads += Math.toRadians(inc);
	}
	
	@Override
	public double getRotationDegrees()
	{
		return Math.toDegrees(_rotationRads);
	}
	
	@Override
	public float getScaleX()
	{
		return _scaleX;
	}
	
	@Override
	public float getScaleY()
	{
		return _scaleY;
	}
	
//	public int getX()
//	{
//		return _locX;
//	}
//	
//	public int getY()
//	{
//		return _locY;
//	}
//	
//	public int getZ()
//	{
//		return _locZ;
//	}
//	
//	public int getWidth()
//	{
//		return (int) (_width * _scaleX);
//	}
//	
//	public int getHeight()
//	{
//		return (int) (_heigh * _scaleY);
//	}
	
	@Override
	public boolean contains(int x, int y)
	{
		return _renderRect.contains(x, y);
	}
	
//	public void translate(int x, int y)
//	{
////		final Rectangle r = new Rectangle(_locX, _locY, getWidth(), getHeight());
//		r.translate(x, y);
//		setLocation((int)r.getX(), (int)r.getY());
//	}
	

	@Override
	public boolean getItemState(ItemState ... newStates)
	{
//		boolean containsAll = false;
		for (final ItemState newState : newStates)
			if (!knownStates.contains(newState))
				return false;
		return true;
//		return knownStates.contains(newState);
	}
	
//	@Override
	public void setItemState(ItemState newState, boolean set)
	{
		if (set)
			knownStates.add(newState);
		else
			knownStates.remove(newState);
	}
	
//	@Override
//	public void setClickable(boolean bool)
//	{
//		clickable = bool;
//	}
//	
//	@Override
//	public boolean isClickable()
//	{
//		return clickable;
//	}
	
	@Override
	public int compareTo(RenderableItem rend2)
	{
		return getLocationZ() - rend2.getLocationZ();
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ",x=" + getLocationX() + ",y=" + getLocationY() + ",z=" + getLocationZ();
	}
//	
//	public boolean isRenderable(int offsetX, int offsetY)
//	{
//		return _locX + _heigh > offsetX && _locY + _width > offsetY;
//	}
	
	@Override
	public boolean isRenderable(final GameRect renderRect)
	{
		if (getItemState(ItemState.SEALED))
			return true;
		if (_renderRect.getLocationZ() <= renderRect.getLocationZ())
			return _renderRect.intersects(renderRect);
		return false;
	}
	
	@Override
	public void handleEvent(final EventType eventType, final AWTEvent awtEvent)
	{
		System.out.println(toString() + eventType);
		
		switch (eventType)
		{

			case EVT_MOUSE__ITEM_HOVERED:
				break;
			case EVT_MOUSE__ITEM_HOVERED_INIT:
				setItemState(ItemState.HOVERED, true);
				break;
			case EVT_MOUSE__ITEM_HOVERED_END:
				setItemState(ItemState.HOVERED, false);
				break;
		
		}
	}
	
}
