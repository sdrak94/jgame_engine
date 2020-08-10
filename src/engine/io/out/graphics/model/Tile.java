package engine.io.out.graphics.model;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.io.out.graphics.render.RenderableTile;
import engine.model.enums.ItemState;
import engine.model.event.enums.EventType;
import engine.model.geometry.GameRect;
import engine.model.interfaces.IRenderable;

public class Tile extends BufferedImage implements IRenderable
{
	private final IRenderable _renderableTile;
	
	public Tile(BufferedImage bufferedImage)
	{
		super(bufferedImage.getColorModel(), bufferedImage.getRaster(), bufferedImage.getColorModel().isAlphaPremultiplied(), null);
		
		_renderableTile = new RenderableTile(this);
	}

	@Override
	public void render(Graphics2D g, int currX, int currY)
	{
		_renderableTile.render(g, currX, currY);
	}

	@Override
	public void setLocationX(int x) 
	{
		_renderableTile.setLocationX(x);
		
	}

	@Override
	public void setLocationY(int y) 
	{
		_renderableTile.setLocationY(y);
	}

	@Override
	public void setLocationZ(int z)
	{
		_renderableTile.setLocationZ(z);
	}

	@Override
	public int getLocationX() 
	{
		return _renderableTile.getLocationX();
	}

	@Override
	public int getLocationY()
	{
		return _renderableTile.getLocationY();
	}

	@Override
	public int getLocationZ()
	{
		return _renderableTile.getLocationZ();
	}

	@Override
	public void setSizeX(int x)
	{
		_renderableTile.setSizeX(x);
	}

	@Override
	public void setSizeY(int y)
	{
		_renderableTile.setSizeY(y);
	}

	@Override
	public int getSizeX() 
	{
		return _renderableTile.getSizeX();
	}

	@Override
	public int getSizeY() 
	{
		return _renderableTile.getSizeY();
	}

	@Override
	public void renderInit(Graphics2D g, int offsetX, int offsetY)
	{
		_renderableTile.renderInit(g, offsetX, offsetY);
	}

	@Override
	public void renderState(Graphics2D g, int currX, int currY) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAlpha(float alpha)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScale(float scaleX, float scaleY)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incScale(float scale) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScaleX(float scaleX) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScaleY(float scaleY) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRotationDegrees(double rotationDegrees)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incRotation(double inc) 
	{
		
	}

	@Override
	public double getRotationDegrees() 
	{
		return 0;
	}

	@Override
	public float getScaleX() 
	{
		return 0;
	}

	@Override
	public float getScaleY() 
	{
		return 0;
	}

	@Override
	public boolean contains(int x, int y)
	{
		return false;
	}

	@Override
	public boolean getItemState(ItemState newState) 
	{
		return false;
	}
	
//	@Override
	public void setItemState(ItemState newState, boolean set)
	{
		
	}

	@Override
	public boolean isRenderable(GameRect renderRect)
	{
		return false;
	}

	@Override
	public void handleEvent(EventType eventType, AWTEvent awtEvent) {
		// TODO Auto-generated method stub
		
	}

}
