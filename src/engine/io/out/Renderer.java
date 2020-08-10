package engine.io.out;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.util.EnumSet;
import java.util.Iterator;

import javax.swing.JPanel;

import engine.io.out.graphics.render.RenderableItem;
import engine.model.geometry.GameRect;
import engine.model.interfaces.IRenderable;
import engine.util.model.FPSCounter2;
import engine.util.model.IFPSCounter;

public abstract class Renderer extends JPanel
{
	protected final GameRect _renderRect = new GameRect();
	
//	protected int offsetX = 0;
//	protected int offsetY = 0;
	
	private long TPF;
	
	private long t0;
	
	private final IFPSCounter fpsCounter = new FPSCounter2();
	
	private boolean _rendering;
	
	protected EnumSet<RenderHint> renderHints = EnumSet.noneOf(RenderHint.class);
	
	protected Color _background = Color.GRAY;
	
	protected AffineTransform _clearGraphics;
	
	public Renderer()
	{
		super(new GridLayout(1,1));
        setIgnoreRepaint(false);
        setDoubleBuffered(true);

        this.addComponentListener(new RendererComponentListener());
	}
	
	protected abstract void render(Graphics2D g);
	
	public abstract void addRenderQueue(RenderableItem renderable);
	
	public abstract void rmRenderQueue(IRenderable renderable);
	
	public abstract void clearRenderQueue();
	
	public abstract Iterator<RenderableItem> getRenderables();
	
	protected abstract void renderInit();
	
	protected abstract void renderFinalize(Graphics2D g);
	
	public final void begin()
	{
		_rendering = true;
		
		while (_rendering)
			render();
		
		_rendering = false;
	}
	
	public final void render()
	{
		t0 = System.nanoTime();
		validateRect();
		renderInit();
	}
	
	public void validateRect()
	{
	}
	
	protected void renderStart(Graphics2D g)
	{
		if (!renderHints.contains(RenderHint.NO_ERASE))//CLEAR
		{
			g.setColor(_background);
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}
	}
	
	protected void renderEnd(Graphics2D g)
	{
		if (renderHints.contains(RenderHint.SHOW_FPS))//fps counter
		{
			g.setColor(new Color(255, 255, 255, 150));
			g.fillRect(0, 0, 40, 20);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Calibri", 1 , 15));
			g.drawString(fpsCounter.getFPS() + "", 1, 15);
		}
		renderXYZ(g);
//		if (renderHints.contains(RenderHint.SHOW_Z))//z view
//		{
//			g.setColor(new Color(255, 255, 255, 150));
//			g.fillRect(getWidth() - 35, 0, 35, 20);
//			g.setColor(Color.BLACK);
//			g.setFont(new Font("Calibri", 1 , 15));
//			g.drawString("Z: " + viewZ, getWidth() - 30, 15);
//		}
		renderFinalize(g);
		TPF = System.nanoTime() - t0;
		//System.out.println(TPF);
		try
		{
//			Thread.sleep(16);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
//		if (_rendering)
//			render();
	}
	
	private boolean hasRenderHint(final RenderHint renderHint)
	{
		return renderHints.contains(renderHint);
	}
	
	private void renderXYZ(Graphics2D g)
	{
		int level = 0;

		level += hasRenderHint(RenderHint.SHOW_X) ? 1 : 0;
		level += hasRenderHint(RenderHint.SHOW_Y) ? 1 : 0;
		level += hasRenderHint(RenderHint.SHOW_Z) ? 1 : 0;
		
		if (level > 0)
		{
			g.setColor(new Color(255, 255, 255, 150));
			g.fillRect(getWidth() - 45, 0, 50, 18 * level);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Calibri", 1 , 15));
//			g.drawString("Z: " + viewZ, getWidth() - 30, 15);
			
			int height = 0;
			
			if (hasRenderHint(RenderHint.SHOW_X))
				g.drawString("X: " + getRenderX(), getWidth() - 40, height+=15);
			if (hasRenderHint(RenderHint.SHOW_Y))
				g.drawString("Y: " + getRenderY(), getWidth() - 40, height+=15);
			if (hasRenderHint(RenderHint.SHOW_Z))
				g.drawString("Z: " + getRenderZ(), getWidth() - 40, height+=15);
		}
	}
	
	public void setRenderBackground(Color color)
	{
		_background = color;
	}
	
	public void addHint(RenderHint renderHint)
	{
		renderHints.add(renderHint);
	}
	
	public void rmHint(RenderHint renderHint)
	{
		renderHints.remove(renderHint);
	}
	
	public void toggleHint(RenderHint renderHint)
	{
		if (renderHints.contains(renderHint))
			renderHints.remove(renderHint);
		else renderHints.add(renderHint);
	}
	
	public void clearGraphics(Graphics2D g)
	{
		g.setTransform(_clearGraphics);
		g.setFont(null);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public long getTPF()
	{
		return TPF;
	}
	
	public void setRendering(boolean rendering)
	{
		_rendering = rendering;
	}
	
	public boolean isRendering()
	{
		return _rendering;
	}
	
	public void setRenderX(int x)
	{
		_renderRect.setLocationX(x);
	}
	
	public void setRenderY(int y)
	{
		_renderRect.setLocationY(y);
	}
	
	public void setRenderZ(int z)
	{
		_renderRect.setLocationZ(z);
	}
	
	public void incRenderX(int x)
	{
		_renderRect.incX(x);
	}
	
	public void incRenderY(int y)
	{
		_renderRect.incY(y);
	}
	
	public void incRenderZ(int z)
	{
		_renderRect.incZ(z);
	}
	
	public int getRenderX()
	{
		return _renderRect.getLocationX();
	}
	
	public int getRenderY()
	{
		return _renderRect.getLocationY();
	}
	
	public int getRenderZ()
	{
		return _renderRect.getLocationZ();
	}
	
	private class RendererComponentListener implements ComponentListener
	{
        public void componentHidden(ComponentEvent arg0) 
        {
        }
        
        public void componentMoved(ComponentEvent arg0)
        {   
        }
        
        public void componentResized(ComponentEvent arg0)
        {
        	_renderRect.setSizeX(getWidth());
        	_renderRect.setSizeY(getHeight());
        }
        
        public void componentShown(ComponentEvent arg0) 
        {

        }
    }
}
