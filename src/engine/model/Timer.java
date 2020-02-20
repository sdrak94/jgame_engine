package engine.model;

public class Timer
{	private long elapse;
	private long startTime;
	
	public void startOver()
	{	elapse = 0;
		startTime = System.currentTimeMillis();
	}
	
	public void reset()
	{
		elapse = 0;
		startTime = 0;
	}
	
	public void start()
	{	startTime = System.currentTimeMillis();
	}
	
	public void pause()
	{	if (startTime == 0)
			throw new RuntimeException("Pause requested on paused Timer..");
		elapse += System.currentTimeMillis() - startTime;
		startTime = 0;
	}
	
	public long elapsed()
	{	return elapse + startTime > 0 ? System.currentTimeMillis() - startTime : 0;
	}
}