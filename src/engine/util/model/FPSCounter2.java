package engine.util.model;

public class FPSCounter2 implements IFPSCounter
{
	private long curTime = System.currentTimeMillis();
	private long lastTime;
	
	@Override
	public int getFPS()
	{
		lastTime = curTime;
		curTime = System.currentTimeMillis();
		
        return (int) (1000 / (curTime - lastTime + 1));
	}
}