package engine.model.character;

import engine.io.out.graphics.render.RenderableTile;
import engine.model.enums.Heading;
import engine.template.character.CharacterTemplate;

public class Character extends RenderableTile
{
	protected final CharacterTemplate _charTemplate;
	
	private Heading _heading;
	
	public Character(CharacterTemplate charTemplate)
	{
		super(charTemplate.getModel());
		_charTemplate = charTemplate;
	}
	
	public void setHeading(Heading heading)
	{
		_heading = heading;
		setTile(_charTemplate.getModel(heading));
	}
	
	public Heading getHeading()
	{
		return _heading;
	}
}
