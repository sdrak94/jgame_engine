package engine.gui.wnd;

import java.awt.Dimension;

import javax.swing.JFrame;

import engine.gui.misc.BattleInterface.PokeLabel;
import engine.io.out.graphics.model.Tile;

public abstract class Wnd extends JFrame
{
	Tile MAP;
	
	public Wnd(String pathToBg)
	{
		setLayout(null);
		if (pathToBg != null)
		{
			MAP = addImage(pathToBg, 0, 0, 0);
			setSize(new Dimension(MAP.getWidth(),MAP.getHeight()));
		}
		else
		{
			setSize(new Dimension(600, 600));
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		//addKeyListener(new IKeyboard());
	}
	
	public Tile getMAP()
	{
		return MAP;
	}
	
	protected void add(final PokeLabel pl)
	{
		getLayeredPane().add(pl, new Integer(pl.getZ()));
	}

	protected Tile addImage(String path, int x, int y, int z)
	{
		//BufferedImage ic = ImageTable.getInstance().getImage(path);
		//final Tile tile = new Tile(ic, x, y);
		//addTile(tile, z);
		return null;
	}
}
