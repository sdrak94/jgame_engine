package engine.data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TileTable extends PackageLoader
{
	private final HashMap<String, BufferedImage> imageBuffer = new HashMap<>();
	
	public TileTable()
	{
		loadPackage("graphics");
		//loadPackage("sprites");
		loadPackage("player");
		loadPackage("maps");
	}
	
	@Override
	protected void populate(String name, byte[] dataBuffer) throws Exception 
	{	
		imageBuffer.put(name, ImageIO.read(new ByteArrayInputStream(dataBuffer)));
	}
	
	public BufferedImage getImage(String strId)
	{
		final BufferedImage image = imageBuffer.get(strId);
		if (image == null)
			throw new NullPointerException("Image with strId: [" + strId + "] no found!");
		return image;
	}
	
	private static class InstanceHolder
	{
		private static final TileTable _instance = new TileTable();
	}
	
	public static TileTable getInstance()
	{
		return InstanceHolder._instance;
	}
}
