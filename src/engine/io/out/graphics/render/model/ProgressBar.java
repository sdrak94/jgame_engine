package engine.io.out.graphics.render.model;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.io.out.graphics.render.RenderableItem;

public class ProgressBar extends RenderableItem
{
	private final int _maxValue;
	private int _value;
	
	public ProgressBar(int width, int heigh, int maxValue, int value)
	{
		super(width, heigh);
		_maxValue = maxValue;
		_value = value;
	}
	
	public int changeValue(int value)
	{
		return setValue(value > _maxValue ? _maxValue : value < 0 ? 0 : value);
	}
	
	public int incValue(int inc)
	{
		return changeValue(_value + inc);
	}
	
	public int decValue(int dec)
	{
		return changeValue(_value - dec);
	}
	
	private int setValue(int value)
	{
		return _value = value;
	}
	
	private float getLambda()
	{
		return (float) (_maxValue - _value) /  _maxValue;
	}
	
	@Override
	public void render(Graphics2D g, int locX, int locY)
	{
		final float lambda = 1 - getLambda();
		if (lambda > 0)
		{
			final int lambdPerc = (int) (lambda * _width);
			g.setColor(new Color(255, 0, 0));
			g.fillRect(_locX + lambdPerc, _locY, _width - lambdPerc, _heigh);
			g.setColor(Color.GREEN);
			g.fillRect(_locX, _locY, lambdPerc, _heigh);
		}
		else
		{
			g.setColor(Color.RED);
			g.fillRect(_locX, _locY, _width, _heigh);
		}
		g.setColor(Color.BLACK);
		g.drawRect(_locX, _locY, _width, _heigh);
	}
}
