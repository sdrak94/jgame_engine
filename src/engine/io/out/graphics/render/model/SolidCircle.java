package engine.io.out.graphics.render.model;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.io.out.graphics.render.RenderableItem;

public class SolidCircle extends RenderableItem
{
	private final Color _baseColor;
	private final int _radius;
	
	public SolidCircle(final int radius, final Color baseColor)
	{
		super(radius, radius);
		_radius = radius;
		_baseColor = baseColor;
	}
	
	@Override
	public void render(Graphics2D g, int locX, int locY)
	{
		g.setColor(_baseColor);
		g.fillOval(locX, locY, _radius, _radius);
	}
	
	public int getLocationZ()
	{
		return 255;
	}
}
