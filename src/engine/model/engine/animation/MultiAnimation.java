package engine.model.engine.animation;

import engine.model.TileGroup;
import engine.model.enums.Heading;

public class MultiAnimation extends Animation
{
	final TileGroup _tileGroup;
	
	public MultiAnimation(TileGroup tileGroup, Heading h, int steps, int ticks, int dista, int nextD, Animation nextA)
	{
		super(tileGroup.getModel(), h, steps, ticks, dista, nextD, nextA);
		_tileGroup = tileGroup;
	}
	
	@Override
	public void animateNow(int x, int y)
	{
		_tileGroup.changeLoc(x, y);
	}
}
