package anim.model;

import java.util.Arrays;
import java.util.Iterator;

import engine.io.out.graphics.model.Frame;

public class FrameIterator implements Iterator<Frame>
{
	private Frame[] frames;
	
	private int ADD_POS;
	private int PREVIEW_POS;
	
	public FrameIterator(FrameIterator frameIterator)
	{
		frames = Arrays.copyOf(frameIterator.frames, frameIterator.ADD_POS);
		ADD_POS = frameIterator.ADD_POS;
	}
	
	public FrameIterator()
	{
		frames = new Frame[16];
	}
	
	public void add(Frame frame)
	{
		if (ADD_POS >= frames.length)
		{
			frames = Arrays.copyOf(frames, frames.length + 16);
		}
		frames[ADD_POS++] = frame;
	}
	
	public Frame get(int index)
	{
		return frames[PREVIEW_POS];
	}
	
	public Frame get()
	{
		return get(PREVIEW_POS);
	}
	
	public boolean hasPrevious()
	{
		return PREVIEW_POS > 0;
	}
	
	public boolean hasNext()
	{
		return PREVIEW_POS < ADD_POS - 1;
	}
	
	public Frame next()
	{
		return get(++PREVIEW_POS);
	}
	
	public Frame previous()
	{
		return get(--PREVIEW_POS);
	}
	
	public int nextNow()
	{
		return ++PREVIEW_POS;
	}
	
	public int previousNow()
	{
		return --PREVIEW_POS;
	}
	
	public boolean setPosition(int POS)
	{
		if (PREVIEW_POS != POS)
		{
			PREVIEW_POS = POS;
			return true;
		}
		return false;
	}
	
	public int getPosition()
	{
		return PREVIEW_POS;
	}
	
	public int getSize()
	{
		return ADD_POS;
	}
	
	public Frame reset()
	{
		return frames[PREVIEW_POS = 0];
	}
	
	public void clear()
	{
		reset();
		ADD_POS = 0;
		frames = new Frame[16];
	}
}
