package engine.model;

import java.util.Collections;
import java.util.LinkedList;

import engine.io.out.graphics.render.RenderableItem;
import engine.manager.DelayManager;
import engine.model.interfaces.IRenderable;

public class RenderQueue extends LinkedList<RenderableItem>
{
	public RenderQueue(int tick)
	{
		DelayManager.getInstance().addQueueAtFixedRate(() -> sortRoutine(), 1000, tick);
	}
	
	public void addRenderQueue(RenderableItem renderable)
	{
		if (!contains(renderable))
			add(renderable);
	}
	
	public void rmRenderQueue(IRenderable renderable)
	{
		remove(renderable);
	}
	
	private void sortRoutine()
	{
		if (isEmpty())
			return;
		Collections.sort(this);
	}
}
