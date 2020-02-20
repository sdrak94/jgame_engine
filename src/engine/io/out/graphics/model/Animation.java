package engine.io.out.graphics.model;

public class Animation extends Tile
{
	private final Frame[] _frames;
	
	public Animation(Frame[] frames)
	{
		super(frames[0]);
		_frames = frames;
	}
	
	public Frame getFrame(int position)
	{
		return _frames[position];
	}
	
	public int getLength()
	{
		return _frames.length;
	}
}
