package engine.manager;

public class AnimationManager 
{
	public static int distanceT;
	
	/*
	public static void animate(Tile tile, Heading h, int distance, int steps, int ticks)
	{
		System.out.println("ANIMATING");
		tile.animationStarted();
		startAnimation(tile, h, distance, steps, ticks);
	}
	
	public static void startAnimation(Tile tile, Heading h, int distance, int steps, int ticks)
	{
		if (distance <= steps)
		{
			final int diff = steps - distance;
			tile.changeLoc(diff * h.getMuX(), diff * h.getMuY());
			tile.animationEnded();
		}
		else
		{
			tile.changeLoc(steps * h.getMuX(), steps * h.getMuY());
			DelayManager.getInstance().addQueue(() -> startAnimation(tile, h, distance - steps, steps, ticks), ticks);
		}
	}
	*/
}
