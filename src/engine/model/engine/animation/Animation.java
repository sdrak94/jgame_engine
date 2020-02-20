package engine.model.engine.animation;

import java.util.HashSet;

import engine.io.out.graphics.model.Tile;
import engine.manager.DelayManager;
import engine.model.enums.Heading;
import engine.model.enums.Speed;

public class Animation
{
	//private final static int _stepDist = 12;
	private final HashSet<Tile> tileListeners = new HashSet<>();
	//private final Tile _tile;
	protected final int _xMult;
	protected final int _yMult;
	private final int _steps;
	private final int _ticks;
	private int _dista;
	private final Heading _h;
	
	private int _nextD;
	private Animation _nextA;
	
	private int distance;
	private boolean STOP;
	
	public Animation(Tile tile, Heading h, int steps, int ticks, int dista, int nextD, Animation nextA)
	{
		_h = h;
		//_tile = tile;
		_xMult = h.getMuX();
		_yMult = h.getMuY();
		_steps = steps;
		_ticks = ticks;
		_dista = dista;
		_nextD = nextD;
		_nextA = nextA;
	}
	
	public Animation(Tile tile, Heading h, int steps, int ticks, int dista)
	{
		this(tile, h, steps, ticks, dista, 0, null);
	}
	
	public Animation(Tile tile, Heading heading, Speed speed, int dista, int nextD, Animation nextA)
	{
		this(tile, heading, speed.getSteps(), speed.getTicks(), dista, nextD, nextA);
	}
	
	public Animation(Tile tile, Heading heading, Speed speed, int dista)
	{
		this(tile, heading, speed, dista, 0, null);
	}
	
	public Animation(Tile tile, Heading h, Speed spd)
	{
		this(tile, h, spd, 0);
	}
	
	public void start()
	{	STOP = false;
		//if (_tile instanceof Sprite)
		//	_tile.asSprite().setHeading(_h);
		animationStarted();
		doAnimate();
	}
	
	private void doAnimate()
	{
		if (!STOP)
		{
			//if (_tile instanceof Sprite && distance % _stepDist == 0)
			//	_tile.asSprite().onMoveAnimation();
			final int distNext = distance + _steps;
			if (distNext > _dista)
			{
				final int absDiff = _dista - distance;
				if (absDiff > 0)
					animateNow(absDiff * _xMult, absDiff * _yMult);
				if (_nextA != null)
				{
					if (_nextD > 0)
					{
						animationEnded();
						DelayManager.getInstance().addQueue(() -> _nextA.start(), _nextD);
					}
					else
						_nextA.start();
				}
				else 
					animationEnded();
				STOP = true;
			}
			else
			{
				distance = distNext;
				animateNow(_steps * _xMult, _steps * _yMult);
				DelayManager.getInstance().addQueue(() -> doAnimate(), _ticks);
			}
		}
		else
			animationEnded();
	}
	
	public void animateNow(int x, int y)
	{
		//_tile.changeLoc(x, y);
	}
	
	public Heading getHeading()
	{
		return _h;
	}
	
	public void setNextAnimation(int nextD, Animation nextA)
	{
		_nextD = nextD;
		_nextA = nextA;
		if (STOP)
			nextA.start();
	}
	
	public void stop()
	{
		STOP = true;
	}
	
	public boolean isStopped()
	{
		return STOP;
	}
	
	public void addTileListener(Tile t)
	{
		tileListeners.add(t);
	}
	
	public void animationStarted()
	{
		//_tile.animationStarted(_tile);
		//for (Tile t : tileListeners)
		//	t.animationStarted(_tile);
	}
	
	public void animationEnded()
	{
		//_tile.animationEnded(_tile);
		//for (Tile t : tileListeners)
		//	t.animationEnded(_tile);
	}
}
