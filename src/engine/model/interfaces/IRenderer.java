package engine.model.interfaces;

public interface IRenderer 
{
	public void render();
	
	public void addRenderQueue(IRenderable renderableItem);
}
