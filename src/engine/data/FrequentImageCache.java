package engine.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import engine.io.out.graphics.render.RenderableTile;

public class FrequentImageCache 
{
	public HashMap<String, RenderableTile> renderables = new HashMap<>();
	
	public FrequentImageCache()
	{
		File f = new File("./images");
		if (!f.exists())
			throw new RuntimeException("Could not find images folder");
		for (File img : f.listFiles())
		{
			try
			{
				final BufferedImage buffImg = ImageIO.read(img);
				final RenderableTile renderableItem = new RenderableTile(buffImg);
				renderables.put(img.getName(), renderableItem);
			}
			catch (Exception e)
			{
				
			}
		}
	}
	
	public HashMap<String, RenderableTile> getRenderables()
	{
		return renderables;
	}
	
	public RenderableTile getRenderable(String key)
	{
		final RenderableTile renderable = renderables.get(key);
		if (renderable == null)
			return renderables.get("selection");
		return renderable;
	}
	
	public static class InstanceHolder
	{
		private static final FrequentImageCache _instance = new FrequentImageCache();
	}
	
	public static FrequentImageCache getInstance()
	{
		return InstanceHolder._instance;
	}
}
