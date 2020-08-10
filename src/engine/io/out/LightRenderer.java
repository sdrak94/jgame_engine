package engine.io.out;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.io.out.graphics.render.RenderableItem;
import engine.model.interfaces.IRenderable;

public class LightRenderer extends Renderer
{
	private final List<RenderableItem> renderQueue = new CopyOnWriteArrayList<>();
	
	{
	}
	
	@Override
	protected void renderInit()
	{
		repaint();
	}

	@Override
	public void addRenderQueue(RenderableItem renderable)
	{
		if (!renderQueue.contains(renderable))
			renderQueue.add(renderable);
	}
	
	@Override
	public void rmRenderQueue(IRenderable renderable)
	{
		renderQueue.remove(renderable);
	}
	
	@Override
	public void clearRenderQueue()
	{
		renderQueue.clear();
	}
	
	@Override
	public Iterator<RenderableItem> getRenderables()
	{
		return renderQueue.iterator();
	}
		
	@Override
	public void paintComponent(Graphics g1d)
	{
		Graphics2D g = (Graphics2D) g1d;
		renderStart(g);
		render(g);
		renderEnd(g);
	}

	@Override
	protected void render(Graphics2D g) 
	{
		for (final RenderableItem renderable : renderQueue)
		{
			if (renderable.getLocationZ() > getRenderZ())
				break;
//			renderable.render(g);
//			renderable.renderState(g);
			clearGraphics(g);
		}
	}

	@Override
	protected void renderFinalize(Graphics2D g) {
		
	}

}
