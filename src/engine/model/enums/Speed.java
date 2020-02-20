package engine.model.enums;
public enum Speed
{
	WALK(1, 14, 133),
	RUNN(2, 20, 166),
	BIKE(3, 25, 200);
	
	private final int steps;
	private final int ticks;
	private final int moveA;
	
	private Speed(int _steps, int _ticks, int _moveA)
	{
		steps = _steps;
		ticks = _ticks;
		moveA = _moveA;
	}
	
	public int getSteps()
	{
		return steps;
	}
	
	public int getTicks()
	{
		return ticks;
	}
	
	public int getMove()
	{
		return moveA;
	}
}