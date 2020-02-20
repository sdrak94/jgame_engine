package engine.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DelayManager
{
	private final ScheduledExecutorService _threadPool = Executors.newScheduledThreadPool(5);
	
	public ScheduledFuture<?> addQueue(Runnable r, long delay)
	{
		return _threadPool.schedule(r, delay, TimeUnit.MILLISECONDS);
	}
	
	public ScheduledFuture<?> addQueueAtFixedRate(Runnable r, long delay, long ticks)
	{
		return _threadPool.scheduleAtFixedRate(r, delay, ticks, TimeUnit.MILLISECONDS);
	}

	public void execute(Runnable r)
	{
		_threadPool.execute(r);
	}
	
	public static class InstanceHolder
	{
		protected static DelayManager mg = new DelayManager();
	}
	
	public static DelayManager getInstance()
	{
		return InstanceHolder.mg;
	}
	
}