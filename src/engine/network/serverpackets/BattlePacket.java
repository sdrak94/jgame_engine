package engine.network.serverpackets;

import engine.gui.misc.BattleInterface;
import engine.gui.wnd.BattleWnd;
import engine.model.Battle;

public class BattlePacket
{
	//mine
	private final int _myPokeId;
	private final byte _myPokeLvl;
	private final int _myPokeMaxHp;
	private final int _myPokeCurHp;
	private final long _myPokeExpToNext;
	private final long _myPokeCurExp;
	
	//enemy
	private final int _enPokeId;
	private final byte _enPokeLvl;
	private final int _enPokeHp;
	
	public BattlePacket()
	{	_myPokeId = 6;
		_myPokeLvl = 96;
		_myPokeMaxHp = 306;
		_myPokeCurHp = 296;
		_myPokeExpToNext = 316661;
		_myPokeCurExp = 150651;
		
		_enPokeId = 160;
		_enPokeLvl = 92;
		_enPokeHp = 100;
		run();
	}
	
	public void run()
	{
		final Battle battleData = new Battle();
		
		battleData.setMyPokeId(_myPokeId);
		battleData.setMyLevel(_myPokeLvl);
		battleData.setMaxHp(_myPokeMaxHp);
		battleData.setCurHp(_myPokeCurHp);
		battleData.setExpToNext(_myPokeExpToNext);
		battleData.setCurExp(_myPokeCurExp);
		
		battleData.setEnPokeId(_enPokeId);
		battleData.setEnLevel(_enPokeLvl);
		battleData.setEnHp(_enPokeHp);
		
		new BattleWnd(new BattleInterface(battleData));
	}
}
