package engine.gui.wnd;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class GameWnd extends JFrame
{
	public GameWnd()
	{
		super();
		setLayout(new GridLayout(1,1));
		//setAlwaysOnTop( true );
//		setSize(768, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMaximumSize(new Dimension(3000,3000));
//		this.setUndecorated(true);
	}
	
	private static class InstanceHolder
	{
		private static final GameWnd _instance = new GameWnd();
	}
	
	public static GameWnd getInstance()
	{
		return InstanceHolder._instance;
	}
}
