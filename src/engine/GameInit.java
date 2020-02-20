package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
//import java.util.concurrent.ScheduledFuture;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import engine.data.AnimatedTileTable;
import engine.data.TileTable;
import engine.gui.wnd.GameWnd;
import engine.io.in.Keyboard;
import engine.io.out.HeavyRenderer;
import engine.io.out.RenderHint;
import engine.io.out.Renderer;
import engine.io.out.graphics.render.RenderableAnimation;
import engine.io.out.graphics.render.RenderableTile;
import engine.manager.DelayManager;
import engine.model.character.Player;
import engine.template.character.CharacterTemplate;
import engine.util.Rnd;

public class GameInit
{
	private final Keyboard keyIn = new Keyboard();

	public GameInit()
	{
		final GameWnd gWnd = GameWnd.getInstance();
		final Renderer screen = new HeavyRenderer();
		
		screen.setPreferredSize(new Dimension(1600, 900));
		
		screen.addHint(RenderHint.SHOW_FPS);
		screen.addHint(RenderHint.SHOW_X);
		screen.addHint(RenderHint.SHOW_Y);
		screen.addHint(RenderHint.SHOW_Z);
		
		screen.setRenderBackground(Color.BLUE);
		
//		final JScrollPane SCRL_SCREEN = new JScrollPane(screen, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
//		SCRL_SCREEN.setPreferredSize(new Dimension(4000, 4000));
//		SCRL_SCREEN.setPreferredSize(new Dimension(1600, 900));
		
		gWnd.add(screen);
		
		final BufferedImage buffImg = TileTable.getInstance().getImage("AzureTown.png");
		final RenderableTile map = new RenderableTile(buffImg);
		map.setZ(-10);
		map.setAlpha(1f);
		map.setClickable(false);
		screen.addRenderQueue(map);
		
		gWnd.setSize(new Dimension(1600, 900));
		gWnd.setLocationRelativeTo(null);
		gWnd.setVisible(true);
		
		final RenderableAnimation weather1 = new RenderableAnimation(AnimatedTileTable.getInstance().getAnimation("SNOW"));
		weather1.setLocation(0, 0, 1);
		screen.addRenderQueue(weather1);
		
		for (int i=0;i<2;i++)
		{
			final RenderableAnimation weather2 = new RenderableAnimation(AnimatedTileTable.getInstance().getAnimation("SNOW"));
			weather2.setLocation(Rnd.get(200), Rnd.get(200), 1);
			screen.addRenderQueue(weather2);
		}
		
		//gWnd.addKeyListener(keyIn);
		
		int loops = 20000;
		while (loops-->0)
		{
			final Player cha = new Player(new CharacterTemplate("bugcatcher"));
			cha.setLocation(Rnd.get(0, 1000), Rnd.get(0, 1000), 5);
			screen.addRenderQueue(cha);
		}
		
//		DelayManager.getInstance().addQueueAtFixedRate(() ->
//		{
//			cha.rndAnime();
//		}, 500, 400);
		
		
//		final ScheduledFuture<?> test = DelayManager.getInstance().addQueueAtFixedRate(() ->
//		{
//			screen.incOffsetX(1);
//			screen.incOffsetY(1);
//		}, 40, 40);
//		
//		DelayManager.getInstance().addQueueAtFixedRate(() ->
//		{
//			test.cancel(true);
//			screen.incOffsetX(-1);
//			screen.incOffsetY(-1);
//		}, 4000, 40);
		
		DelayManager.getInstance().execute(screen::begin);
	}
	
	public void gameUpdate()
	{
		keyIn.update();
		System.out.println(keyIn.exit);
	}
	
	public static final long getTicks()
	{
		return System.currentTimeMillis();
	}
	
	public static void main(String[] args) throws Exception
	{
		//System.setProperty("sun.java2d.opengl", "true");
		//System.setProperty("sun.awt.noerasebackground", "true");
		//Pokedex.getInstance();
		//ImageTable.getInstance();
		TileTable.getInstance();
		AnimatedTileTable.getInstance();
		//FontTable.getInstance();
		//MusicTable.getInstance();
//		new BattlePacket();
		GameInit.getInstance();

		
	}
	
	private static class InstanceHolder
	{
		private static final GameInit _instance = new GameInit();
	}
	
	public static GameInit getInstance()
	{
		return InstanceHolder._instance;
	}
	
	public static void throwErrorMessage(String title, String msg)
	{
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
}
