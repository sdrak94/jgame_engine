package engine.model;

import java.util.ArrayList;
import java.util.Iterator;

import engine.io.out.graphics.model.Tile;

public class TileGroup implements Iterable<Tile>
{
	private final ArrayList<Tile> _tiles = new ArrayList<>();
	private final Tile _model;
	
	public TileGroup(Tile model)
	{
		_model = model;
	}

	public void attach(Tile tile)
	{
		_tiles.add(tile);
	}
	
	public void attach(Tile ... tiles)
	{
		for (Tile tile : tiles)
			_tiles.add(tile);
	}
	
	public void changeLoc(int x, int y)
	{	//_model.setLocation(x, y);
		//for (Tile tile : _tiles)
		//	tile.setLocation(tile.getX() + x, tile.getY() + y);
	}
	
	public Tile getModel()
	{
		return _model;
	}

	@Override
	public Iterator<Tile> iterator() 
	{
		return _tiles.iterator();
	}
}
