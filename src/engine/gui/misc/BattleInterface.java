package engine.gui.misc;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import engine.data.FontTable;
import engine.data.ImageTable;
import engine.model.Battle;
import engine.template.PokemonTemplate;
import pokedex.Pokedex;

public class BattleInterface
{
	private final Battle _battleData;
	
	private final PokeLabel myIcon;
	private final PokeLabel enIcon;
	
	private final PokeLabel myName;
	private final PokeLabel enName;
	
	private final PokeLabel myLevel;
	private final PokeLabel enLevel;
	
	private final PokeLabel myHealth;
	
	public BattleInterface(Battle battleData)
	{	
		_battleData = battleData;
		final PokemonTemplate myTemplate = Pokedex.getInstance().getPokemonByID(battleData.getMyPokeId());
		final PokemonTemplate enTemplate = Pokedex.getInstance().getPokemonByID(battleData.getEnPokeId());
		
		
		myIcon = addImage(myTemplate.getName() + "b.gif", 50, 100, 200);
		enIcon = addImage(enTemplate.getName() + ".gif", 305, 75, 200);
		
		myName = addString(myTemplate.getName().toUpperCase(), 270, 173, 10, FontTable.getInstance().getNameFont());
		enName = addString(enTemplate.getName().toUpperCase(), 42, 30, 300, FontTable.getInstance().getNameFont());
	
		myLevel = addString("Lv" + String.valueOf(battleData.getMyLevel()), 388, 173, 10, FontTable.getInstance().getLevelFont());
		enLevel = addString("Lv" + String.valueOf(battleData.getEnLevel()), 166, 30, 10, FontTable.getInstance().getLevelFont());
		
		myHealth = addString(battleData.getCurHp() + " / " + battleData.getMaxHp(), 355, 207, 10, FontTable.getInstance().getHealFont());
	
	}
	
	public PokeLabel getEnIcon()
	{
		return enIcon;
	}
	
	public PokeLabel getMyIcon()
	{
		return myIcon;
	}
	
	public PokeLabel getMyName()
	{
		return myName;
	}
	
	public PokeLabel getEnName()
	{
		return enName;
	}
	
	public PokeLabel getMyLevel()
	{
		return myLevel;
	}
	
	public PokeLabel getEnLevel()
	{
		return enLevel;
	}
	
	public PokeLabel getMyHealth()
	{
		return myHealth;
	}
	
	public Battle getBattleInfo()
	{
		return _battleData;
	}
	
	private PokeLabel addString(String str, int x, int y, int z, Font f)
	{
		final PokeLabel label = new PokeLabel();
		label.setText(str);
		label.setBounds(x, y, 300, 30);
		label.setZ(z);
		if (f != null)
			label.setFont(f);
		return label;
	}
	
	private PokeLabel addImage(String path, int x, int y, int z)
	{	final ImageIcon imageIcon = ImageTable.getInstance().getImage(path);
		final PokeLabel label = new PokeLabel();
		label.setDoubleBuffered(true);
		label.setIcon(imageIcon); //TODO fix
		label.setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		label.setZ(z);
		return label;
	}
	
	public static class PokeLabel extends JLabel
	{
		private int Z;
		
		public void setZ(int val)
		{
			Z = val;
		}
		
		public int getZ()
		{
			return Z;
		}
	}
}
