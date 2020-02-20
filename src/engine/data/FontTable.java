package engine.data;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.ByteArrayInputStream;

public class FontTable extends PackageLoader
{
	private final Font nameFont;
	private final Font levlFont;
	private final Font healFont;
	
	private final GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
	
	public FontTable()
	{
		loadPackage("fonts");
		nameFont = new Font("Power Red and Blue", 0, 18);
		levlFont = new Font("Power Red and Blue", 0, 20);
		healFont = new Font("Pokemon FireLeaf", 0, 18);
	}
	
	@Override
	protected void populate(String name, byte[] dataBuffer) throws Exception
	{
		Font font = Font.createFont(Font.TRUETYPE_FONT, new ByteArrayInputStream(dataBuffer));
		ge.registerFont(font);
	}

	public Font getNameFont()
	{
		return nameFont;
	}
	
	public Font getLevelFont()
	{
		return levlFont;
	}
	
	public Font getHealFont()
	{
		return healFont;
	}
	
	private static class InstanceHolder
	{
		private static final FontTable _instance = new FontTable();
	}
	
	public static FontTable getInstance()
	{
		return InstanceHolder._instance;
	}
}
