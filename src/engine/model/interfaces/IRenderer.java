package engine.model.interfaces;

import engine.io.out.graphics.render.RenderableItem;

public interface IRenderer 
{
	public void render();
	
	public void addRenderQueue(RenderableItem renderableItem);
}
