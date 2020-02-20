package engine.model.character;

import engine.model.enums.Heading;
import engine.template.character.CharacterTemplate;
import engine.util.Rnd;

public class Player extends Character
{
	protected final CharacterTemplate _charTemplate;
	
	public Player(CharacterTemplate charTemplate)
	{
		super(charTemplate);
		_charTemplate = charTemplate;
	}
	
	public void rndAnime()
	{
		setTile(_charTemplate.getModel(Heading.values()[Rnd.get(3)]));
	}
}
