package creator.init;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import creator.wnd.CreatorWnd;
import engine.io.out.HeavyRenderer;
import engine.io.out.RenderHint;
import engine.io.out.Renderer;

public class CreatorInit 
{
	public static long TPF;
	
	public static final int TILE_CONST = 16;
	
	public CreatorInit()
	{
		final Renderer mainRender = new HeavyRenderer();
		final Renderer seleRender = new HeavyRenderer();
		
		mainRender.addHint(RenderHint.SHOW_Z);
		mainRender.addHint(RenderHint.SHOW_FPS);
		
		seleRender.addHint(RenderHint.SHOW_FPS);
		
		seleRender.setRenderBackground(Color.CYAN);
		
		final CreatorWnd cWnd = new CreatorWnd(mainRender, seleRender);
		newThread(mainRender::begin);
		newThread(seleRender::begin);
	}
	
	private void newThread(final Runnable runnable)
	{
		new Thread(runnable).start();
	}
	
	public static void main(String[] args) throws Exception
	{
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        System.setProperty("sun.awt.noerasebackground", "true");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//FrequentImageCache.getInstance();
		//BufferedImageTable.getInstance();
		new CreatorInit();
	}
}
 