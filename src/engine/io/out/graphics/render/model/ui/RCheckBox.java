package engine.io.out.graphics.render.model.ui;

import static all.settings.GlobalConstants.TILE_CONST;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import engine.model.enums.ItemState;
import engine.model.event.enums.EventType;

public class RCheckBox extends RAbstractButton
{
	
	private boolean _value;
	
	private static final char[] _tick = "\u2713".toCharArray();	
	{
		this.setItemState(ItemState.DEBUG_CLICKED, false);
		this.setItemState(ItemState.DEBUG_HOVERED, false);
		this.setItemState(ItemState.MOVEABLE, false);
	}
	
	public RCheckBox(boolean def)
	{
		super(TILE_CONST, TILE_CONST);
		_value = def;
	}
	
	public RCheckBox()
	{
		this(false);
	}
	
	@Override
	public void render(Graphics2D g, int currX, int currY)
	{

		super.render(g, currX, currY);
		
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.BLACK);
		g.draw3DRect(currX, currY, TILE_CONST, TILE_CONST, true);
		
		if (_value)
		{
			g.setColor(Color.GREEN);
			g.setFont(new Font("Arial Unicode MS", 1, 20));
			g.drawChars(_tick, 0, 1, currX, currY + TILE_CONST);
			g.setFont(null);
		}
		

	}
	
	@Override
	public void handleEvent(final EventType eventType, final AWTEvent awtEvent)
	{
		super.handleEvent(eventType, awtEvent);
		switch (eventType)
		{
			case EVT_MOUSE__ITEM_SELECTED:
				_value = !_value;
		}
	}
}
