package engine.util.model;

public class FPSCounter
{
	private int FPS = 0;
	private int frames = 0;
	private long totalTime = 0;
	private long curTime = System.currentTimeMillis();
	private long lastTime = curTime;
	
	public int getFPS()
	{
		lastTime = curTime;
        curTime = System.currentTimeMillis();
        totalTime += curTime - lastTime;
        if(totalTime > 1000)
        {
          totalTime -= 1000;
          FPS = frames;
          frames = 0;
        } 
        ++frames;
        return FPS;
	}
}