package engine.io.out.graphics.render.model;

import java.awt.Graphics2D;

import engine.io.out.graphics.render.RenderableItem;
import engine.model.enums.Heading;
import engine.model.enums.WeatherType;

public class WeatherFilter extends RenderableItem
{
	private WeatherType _weatherType = WeatherType.NORMAL;
	//private Heading _heading = Heading.DO_LE;
	
	public WeatherFilter(int width, int heigh)
	{
		super(width, heigh);
	}

	@Override
	public void render(Graphics2D g, int locX, int locY) 
	{
		switch(_weatherType)
		{
			//case BLIZZARD:
			//	int snowballs = 1000;
			//	g.setColor(Color.WHITE);
			//	while(snowballs-->0)
			//		g.fillOval(Rnd.get(0, 800), Rnd.get(0, 800), Rnd.get(1, 3), Rnd.get(1, 3));
			//	break;
		}
	}
	
	public void setWeather(WeatherType weatherType)
	{
		_weatherType = weatherType;
	}
	
	public void setHeading(Heading heading)
	{
		//_heading = heading;
	}
}
