package engine.io.out.animation;

import engine.model.interfaces.IRenderable;

public class Animation2D implements Runnable
{
	private final IRenderable _renderableItem;
	
	public Animation2D(IRenderable renderableItem)
	{
		_renderableItem = renderableItem;
	}
	
	@Override
	public void run()
	{
		
	}
}
