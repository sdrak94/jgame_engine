package engine.model;

public class Battle
{
	private int _pokeId;
	private byte _level;
	private int _maxHp;
	private int _curHp;
	private long _expToNext;
	private long _curExp;
	
	//enemy
	private int _pokeId2;
	private byte _level2;
	private int _maxHp2;
	
	public void setMyPokeId(int pokeId)
	{
		_pokeId = pokeId;
	}
	public void setMyLevel(byte level)
	{
		_level = level;
	}
	public void setMaxHp(int maxHp)
	{
		_maxHp = maxHp;
	}
	public void setCurHp(int curHp)
	{
		_curHp = curHp;
	}
	public void setExpToNext(long expToNext)
	{
		_expToNext = expToNext;
	}
	public void setCurExp(long curExp)
	{
		_curExp = curExp;
	}
	public void setEnPokeId(int pokeId2)
	{
		_pokeId2 = pokeId2;
	}
	public void setEnLevel(byte level2)
	{
		_level2 = level2;
	}
	public void setEnHp(int maxHp2)
	{
		_maxHp2 = maxHp2;
	}
	
	public int getMyPokeId()
	{
		return _pokeId;
	}

	public byte getMyLevel()
	{
		return _level;
	}

	public int getMaxHp()
	{
		return _maxHp;
	}

	public int getCurHp()
	{
		return _curHp;
	}

	public long getExpToNext()
	{
		return _expToNext;
	}

	public long getCurExp()
	{
		return _curExp;
	}

	public int getEnPokeId()
	{
		return _pokeId2;
	}

	public byte getEnLevel()
	{
		return _level2;
	}

	public int getEnHp()
	{
		return _maxHp2;
	}
}
