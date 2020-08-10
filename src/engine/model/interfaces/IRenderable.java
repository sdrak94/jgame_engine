package engine.model.interfaces;

import java.awt.AWTEvent;
import java.awt.Graphics2D;

import engine.model.enums.ItemState;
import engine.model.event.enums.EventType;
import engine.model.geometry.GameRect;

public interface IRenderable {

	public void render(final Graphics2D g, final int currX, final int currY);
	
	public void setLocationX(int x);

	public void setLocationY(int y);

	public void setLocationZ(int z);

	public int getLocationX();

	public int getLocationY();

	public int getLocationZ();

	public void setSizeX(int x);

	public void setSizeY(int y);

	public int getSizeX();

	public int getSizeY();

	public void renderInit(Graphics2D g, int offsetX, int offsetY);

	public void renderState(Graphics2D g, int currX, int currY);

	public void setAlpha(float alpha);

	public void setScale(float scaleX, float scaleY);

	public void incScale(float scale);

	public void setScaleX(float scaleX);

	public void setScaleY(float scaleY);

	public void setRotationDegrees(double rotationDegrees);

	public void incRotation(double inc);

	public double getRotationDegrees();

	public float getScaleX();

	public float getScaleY();

	public boolean contains(int x, int y);

	public boolean getItemState(ItemState ... newStates);
	
//	public void setItemState(ItemState newState, boolean set);

	boolean isRenderable(GameRect renderRect);
	
	public void handleEvent(final EventType eventType, final AWTEvent awtEvent);

}