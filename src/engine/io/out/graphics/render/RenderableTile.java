package engine.io.out.graphics.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.io.out.graphics.model.Tile;

public class RenderableTile extends RenderableItem
{
	protected Tile _tile;
	
	public RenderableTile(Tile tile)
	{
		super(tile.getWidth(), tile.getHeight());
		_tile = tile;
	}
	
	public RenderableTile(BufferedImage buffImg)
	{
		this(new Tile(buffImg));
	}

	@Override
	public void render(Graphics2D g, int locX, int locY)
	{
		g.drawImage(_tile, locX, locY, null);
	}
	
	public void setTile(Tile tile)
	{
		_tile = tile;
	}
}
