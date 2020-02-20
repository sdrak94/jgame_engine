package engine.data;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import engine.io.out.graphics.model.Animation;
import engine.io.out.graphics.model.Frame;
import engine.io.out.graphics.model.Tile;

public class AnimatedTileTable extends PackageLoader
{
	private final HashMap<String, ArrayList<Frame>> frameGroups = new HashMap<>();
	
	private final HashMap<String, Animation> animations = new HashMap<>();
	
	public AnimatedTileTable()
	{
		loadPackage("weather");
	}
	
	@Override
	protected void populate(String name, byte[] dataBuffer) throws Exception 
	{	
		final String[] data = name.split("-");
		final String groupName = data[0];
		final int position = Integer.parseInt(data[1].split("\\.")[0]);
		ArrayList<Frame> tileGroup = frameGroups.get(groupName);
		if (tileGroup == null)
		{
			tileGroup = new ArrayList<>();
			frameGroups.put(groupName, tileGroup);
		}
		tileGroup.add(new Frame(new Tile(ImageIO.read(new ByteArrayInputStream(dataBuffer))), 0, position));
	}
	
	public Animation getAnimation(String strId)
	{
		final Animation animation = animations.get(strId);
		if (animation == null)
			throw new NullPointerException("Animation with strId: [" + strId + "] no found!");
		return animation;
	}
	
	@Override
	protected void finished(int loadedCount, long t0)
	{
		final Comparator<Frame> comparator = new FrameComparator();
		for (Map.Entry<String, ArrayList<Frame>> entry : frameGroups.entrySet())
		{
			final ArrayList<Frame> value = entry.getValue();
			value.sort(comparator);
			animations.put(entry.getKey(), new Animation(value.toArray(new Frame[value.size()])));
		}
		super.finished(loadedCount, t0);
	}
	
	private static class InstanceHolder
	{
		private static final AnimatedTileTable _instance = new AnimatedTileTable();
	}
	
	public static AnimatedTileTable getInstance()
	{
		return InstanceHolder._instance;
	}
	
	private static class FrameComparator implements Comparator<Frame>
	{
		@Override
		public int compare(Frame f1, Frame f2) 
		{
			return f1.getPosition() - f2.getPosition();
		}
	}
}
