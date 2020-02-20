package engine.template.character;

import java.util.HashMap;

import engine.data.TileTable;
import engine.io.out.graphics.model.Tile;
import engine.model.enums.Heading;

public class CharacterTemplate 
{
	private final HashMap<Heading, Tile[]> modelsByHeading = new HashMap<>();

	public CharacterTemplate(String modelName)
	{
		final Tile[] modelsUP = new Tile[3];
		modelsUP[0] = new Tile(TileTable.getInstance().getImage(modelName + "up.png"));
		modelsUP[1] = new Tile(TileTable.getInstance().getImage(modelName + "up1.png"));
		modelsUP[2] = new Tile(TileTable.getInstance().getImage(modelName + "up2.png"));
		modelsByHeading.put(Heading.UP, modelsUP);
		
		final Tile[] modelsDO = new Tile[3];
		modelsDO[0] = new Tile(TileTable.getInstance().getImage(modelName + "do.png"));
		modelsDO[1] = new Tile(TileTable.getInstance().getImage(modelName + "do1.png"));
		modelsDO[2] = new Tile(TileTable.getInstance().getImage(modelName + "do2.png"));
		modelsByHeading.put(Heading.DO, modelsDO);
		
		final Tile[] modelsLE = new Tile[3];
		modelsLE[0] = new Tile(TileTable.getInstance().getImage(modelName + "le.png"));
		modelsLE[1] = new Tile(TileTable.getInstance().getImage(modelName + "le1.png"));
		modelsLE[2] = new Tile(TileTable.getInstance().getImage(modelName + "le2.png"));
		modelsByHeading.put(Heading.LE, modelsLE);
		
		final Tile[] modelsRI = new Tile[3];
		modelsRI[0] = new Tile(TileTable.getInstance().getImage(modelName + "ri.png"));
		modelsRI[1] = new Tile(TileTable.getInstance().getImage(modelName + "ri1.png"));
		modelsRI[2] = new Tile(TileTable.getInstance().getImage(modelName + "ri2.png"));
		modelsByHeading.put(Heading.RI, modelsRI);
	}
	
	public Tile getModel()
	{
		return modelsByHeading.get(Heading.DO)[0];
	}
	
	public Tile getModel(Heading h)
	{
		return modelsByHeading.get(h)[0];
	}

	public Tile[] getModels(Heading h)
	{
		return modelsByHeading.get(h);
	}
}
