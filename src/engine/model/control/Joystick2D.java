package engine.model.control;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import engine.io.out.graphics.render.RenderableItem;
import engine.model.enums.ItemState;

public class Joystick2D extends RenderableItem
{

	private final int _radius;
	
	public Joystick2D(int radius) 
	{
		super(radius, radius);
		
		_radius = radius;
		
		setItemState(ItemState.MOVEABLE, false);
		setItemState(ItemState.SEALED, true);
		setItemState(ItemState.CLICKABLE, true);
		setItemState(ItemState.DEBUG_CLICKED, false);
	}

	@Override
	public void render(Graphics2D g, int currX, int currY) 
	{
		final RenderingHints prevRenderinghints = g.getRenderingHints();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(255, 255, 255, 155));
		g.fillOval(currX, currY, _radius, _radius);
		
		g.setColor(Color.BLACK);
		g.drawOval(currX, currY, _radius, _radius);
		
		int halfRad = _radius / 2;
		
		if (getItemState(ItemState.HOVERED))
			halfRad += 10;
		
		g.setColor(new Color(205, 205, 205, 145));
		g.fillOval(currX + halfRad / 2 - 3, currY + halfRad / 2, halfRad, halfRad);

		g.setColor(Color.BLACK);
		g.drawOval(currX + halfRad / 2 - 2, currY + halfRad / 2, halfRad, halfRad);
		
		g.setRenderingHints(prevRenderinghints);
	}
	
	@Override
	public int getLocationZ()
	{
		return 255;
	}

}
