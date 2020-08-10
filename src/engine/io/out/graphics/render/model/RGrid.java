package engine.io.out.graphics.render.model;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.io.out.graphics.render.RenderableItem;
import engine.model.enums.ItemState;

public class RGrid extends RenderableItem
{
	private final int _iMax;
	private final int _jMax;
	private final int _size;
	
	public RGrid(int iMax, int jMax, int size)
	{
		super(size, size);
		_iMax = iMax;
		_jMax = jMax;
		_size = size;
	}
	
	{
		this.setItemState(ItemState.SEALED, true);
		this.setItemState(ItemState.DEBUG_CLICKED, false);
		this.setItemState(ItemState.DEBUG_HOVERED, false);
	}
	
	@Override
	public void render(Graphics2D g, int locX, int locY)
	{
		g.setColor(Color.BLACK);

		for (int i = 0; i < _iMax; i++)
			g.drawLine(i * _size + locX, locX, locX + i * _size, 1024);
		
		for (int j = 0; j < _jMax; j++)
			g.drawLine(locX, locY + j * _size, 1024, locY + j * _size);
		
	}
}
