package engine.data;

import javax.swing.ImageIcon;

public class ImageTable extends PackageLoader<ImageIcon>
{
	public ImageTable()
	{
		loadPackage("graphics");
		loadPackage("sprites");
		loadPackage("player");
		loadPackage("maps");
	}
	
	@Override
	protected void populate(String name, byte[] dataBuffer) throws Exception 
	{	
		final ImageIcon image = new ImageIcon(dataBuffer);
		resources.put(name, image);
//		
//		if (name.contains("png")) try (ByteArrayInputStream in = new ByteArrayInputStream(dataBuffer))
//		{
//			System.out.print("Loading " + name + "... OK");
////			final Texture texture = TextureLoader.getTexture("PNG", in);
////			resources.put(name, texture);
//			final DecodedImage image = new DecodedImage(in);
//			resources.put(name, image);
//			
//		}
//		catch (Exception e)
//		{
//			System.out.println("FAILED");
//			//e.printStackTrace();
//		}
	}
	
	public ImageIcon getImage(String strId)
	{
		final ImageIcon image = resources.get(strId);
		if (image == null)
			throw new NullPointerException("Image with strId: [" + strId + "] no found!");
		return image;
	}
	
	private static class InstanceHolder
	{
		private static final ImageTable _instance = new ImageTable();
	}
	
	public static ImageTable getInstance()
	{
		return InstanceHolder._instance;
	}
}
