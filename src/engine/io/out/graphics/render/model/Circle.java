package engine.io.out.graphics.render.model;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.io.out.graphics.render.RenderableItem;
import engine.util.Rnd;

public class Circle extends RenderableItem
{
	private final Color color = new Color(Rnd.get(0xFFFFFF));
	private int _radius;
	
	public Circle(int radius)
	{
		super(radius, radius);
		_radius = radius;
	}
	
	public void setRadius(int radius)
	{
		_radius = radius;
	}
	
	public int getRadius()
	{
		return _radius;
	}
	
	@Override
	public void render(Graphics2D g, int locX, int locY)
	{
		g.setColor(color);
		g.drawOval(locX, locY, _radius, _radius);
	}
}
