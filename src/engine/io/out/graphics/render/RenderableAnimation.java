package engine.io.out.graphics.render;

import java.awt.Graphics2D;

import engine.io.out.graphics.model.Animation;
import engine.io.out.graphics.model.Frame;


public class RenderableAnimation extends RenderableTile
{
	private final Animation _tileAnimation;
	
	private byte renderPosition;
	
	private int lol;
	
	public RenderableAnimation(Animation tileAnimation)
	{
		super(tileAnimation);
		_tileAnimation = tileAnimation;
	}
	
	public void nextFrame()
	{
		if (renderPosition == _tileAnimation.getLength())
			renderPosition = 0;
		final Frame nextFrame = _tileAnimation.getFrame(renderPosition++);
		_tile = nextFrame;
	}

	@Override
	public void render(Graphics2D g, int locX, int locY)
	{
		if (lol++%2==0)
			nextFrame();
		super.render(g, locX, locY);
	}
}
