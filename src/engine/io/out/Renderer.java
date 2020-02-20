package engine.io.out;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.util.EnumSet;
import java.util.Iterator;

import javax.swing.JPanel;

import engine.io.out.graphics.render.RenderableItem;
import engine.util.model.FPSCounter;

public abstract class Renderer extends JPanel
{
	protected final Rectangle _renderRect;
	
//	protected int offsetX = 0;
//	protected int offsetY = 0;
	
	private long TPF;
	
	private long t0;
	
	private final FPSCounter fpsCounter = new FPSCounter();
	
	private int viewZ = 1;
	
	private boolean _rendering;
	
	protected EnumSet<RenderHint> renderHints = EnumSet.noneOf(RenderHint.class);
	
	protected Color _background = Color.GRAY;
	
	protected AffineTransform _clearGraphics;
	
	public Renderer()
	{
		super(new GridLayout(1,1));
        setIgnoreRepaint(false);
        setDoubleBuffered(true);

        _renderRect = new Rectangle();
        
        this.addComponentListener(new RendererComponentListener());
	}
	
	protected abstract void render(Graphics2D g);
	
	public abstract void addRenderQueue(RenderableItem renderable);
	
	public abstract void rmRenderQueue(RenderableItem renderable);
	
	public abstract void clearRenderQueue();
	
	public abstract Iterator<RenderableItem> getRenderables();
	
	protected abstract void renderInit();
	
	protected abstract void renderFinalize(Graphics2D g);
	
	public final void begin()
	{
		_rendering = true;
		
		while (_rendering)
			render();
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
				g.drawString("X: " + _renderRect.x, getWidth() - 40, height+=15);
			if (hasRenderHint(RenderHint.SHOW_X))
				g.drawString("Y: " + _renderRect.y, getWidth() - 40, height+=15);
			if (hasRenderHint(RenderHint.SHOW_X))
				g.drawString("Z: " + viewZ, getWidth() - 40, height+=15);
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
		_clearGraphics.setTransform(_clearGraphics);
		g.setFont(null);
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
	
	public int getZ()
	{
		return viewZ;
	}
	
	public void setZ(int z)
	{
		viewZ = z;
	}
	
	public void incZ(int z)
	{
		viewZ += z;
	}
	
	public void setOffsetX(int x)
	{
//		offsetX = x;
		_renderRect.x = x;
	}
	
	public void incOffsetX(int x)
	{
//		offsetX += x;
		_renderRect.x += x;
	}
	
	
	public void setOffsetY(int y)
	{
//		offsetY = y;
		_renderRect.y = y;
	}
	
	public void incOffsetY(int y)
	{
		_renderRect.y += y;
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
        	_renderRect.width =  getWidth();
        	_renderRect.height = getHeight();
        }
        
        public void componentShown(ComponentEvent arg0) 
        {

        }
    }
}
