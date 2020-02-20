package engine.io.out.graphics.model;

import java.awt.image.BufferedImage;

public class Frame extends Tile
{
	private final int _duration;
	private final int _position;
	
	public Frame(final BufferedImage tile, int duration, int position)
	{
		super(tile);
		_duration = duration;
		_position = position;
	}
	
	public int getDuration()
	{
		return _duration;
	}
	
	public int getPosition()
	{
		return _position;
	}
}
