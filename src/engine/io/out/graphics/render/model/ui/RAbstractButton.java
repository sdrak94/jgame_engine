package engine.io.out.graphics.render.model.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import all.settings.GlobalConstants;
import engine.io.out.graphics.render.RenderableItem;
import engine.model.enums.ItemState;

public abstract class RAbstractButton extends RenderableItem {

	private String _text;
	
	private char[] _chars;
	
	private int _textLen;

	{
		this.setItemState(ItemState.DEBUG_HOVERED, false);
	}
	
	public RAbstractButton(int width, int heigh)
	{
		super(width, heigh);
	}

	public void setText(final String value)
	{
		_text = value;
		
		_chars = value.toCharArray();
		
		_textLen = value.length();
	}
	
	public String getText()
	{
		return _text;
	}
	
	public int getTextLenght()
	{
		return _textLen;
	}
	
	@Override
	public void render(Graphics2D g, int currX, int currY)
	{
		g.setColor(Color.GREEN);

		if (getItemState(ItemState.HOVERED))
			g.setFont(new Font("Arial", 1, 16));
		else
			g.setFont(new Font("Arial", 0, 16));
		
		g.drawChars(_chars, 0, _textLen, currX + GlobalConstants.TILE_CONST + 2, currY + GlobalConstants.TILE_CONST - 2);
	}

}