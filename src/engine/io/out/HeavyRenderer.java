package engine.io.out;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.io.out.graphics.render.RenderableItem;
import engine.model.enums.ItemState;

public class HeavyRenderer extends Renderer
{
	private final Canvas _canvas = new Canvas();
	
	private RenderableItem knownItem;
	
	//private final RenderQueue renderQueue = new RenderQueue(3000);
	private BufferStrategy bufferStrategy;
	
	private final List<RenderableItem> renderQueue = new CopyOnWriteArrayList<>();
	
	public HeavyRenderer()
	{
//		_canvas.setPreferredSize(new Dimension(2080, 1024));
//		setPreferredSize(new Dimension(2080, 1024));
		add(_canvas);//, BorderLayout.CENTER);
		final EngineMouse mouse = new EngineMouse();
		_canvas.setIgnoreRepaint(true);
		_canvas.requestFocus();
		
		_canvas.addMouseListener(mouse);
		_canvas.addMouseMotionListener(mouse);
		
		addMouseWheelListener(new MouseWheelListener()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				final int rot = -e.getWheelRotation();
				if (knownItem == null)
					incZ(rot);//viewZ += rot;
				else
				{
					knownItem.setZ(knownItem.getZ() + rot);
					//viewZ = knownItem.getZ();
					setZ(knownItem.getZ());
					/*synchronized (renderables)
					{
						
						rmRenderQueue(knownItem);
						addRenderQueue(knownItem);
					}*/
				}
			}
		});
	}
	
	private int getMaxZ()
	{
		int maxZ = -Integer.MAX_VALUE;
		for (RenderableItem renderable : renderQueue)
			if (maxZ < renderable.getZ()) maxZ = renderable.getZ();
		return maxZ;
	}
	
	private RenderableItem getTop(int x, int y)
	{
		x += _renderRect.x;
		y += _renderRect.y;
		
		RenderableItem top = null;
		for (RenderableItem renderable : renderQueue)
		{
			if (renderable.isClickable() && renderable.getZ() <= getZ() && renderable.contains(x, y))
				top = renderable;
		}
		return top;
	}
	
	@Override
	protected void renderInit()
	{
		final Graphics2D g = (Graphics2D) fetchBufferStrategy().getDrawGraphics();
		if (_clearGraphics == null )
			_clearGraphics = g.getTransform();
		renderStart(g);
		render(g);
		renderEnd(g);
	}
	
	@Override
	protected void renderFinalize(Graphics2D g)
	{
		bufferStrategy.show();
		g.dispose();
	}
	
	@Override
	protected void render(Graphics2D g)
	{

		renderQueue.parallelStream()
			.filter((renderableItem) -> renderableItem.isRenderable(_renderRect))
			.sorted()
			.forEachOrdered((renderableItem) ->
			{
				renderableItem.renderInit(g, _renderRect.x, _renderRect.y);
				clearGraphics(g);
			});
//		synchronized (renderQueue)
//		{	Collections.sort(renderQueue);
//		}	
//		for (final RenderableItem renderable : renderQueue)
//		{
//			if (renderable.getZ() > getZ())
//				break;
//			
//			if (renderable.isRenderable(_renderRect))
//			{
//				renderable.renderInit(g, _renderRect.x, _renderRect.y);
//				clearGraphics(g);
//			}
//		}
	}
	
	private BufferStrategy fetchBufferStrategy()
	{
		bufferStrategy = _canvas.getBufferStrategy();
		if (bufferStrategy == null)
		{	_canvas.createBufferStrategy(2);
			return bufferStrategy = _canvas.getBufferStrategy();
		}
		return bufferStrategy;
	}

	@Override
	public void addRenderQueue(RenderableItem renderable)
	{
		if (!renderQueue.contains(renderable) && renderQueue.add(renderable))
			setZ(getZ() < renderable.getZ() ? renderable.getZ() : getZ());
	}
	
	@Override
	public void rmRenderQueue(RenderableItem renderable)
	{	
		renderQueue.remove(renderable);
	}
	
	@Override
	public void clearRenderQueue()
	{
		renderQueue.clear();
	}
	
	@Override
	public Iterator<RenderableItem> getRenderables()
	{
		return renderQueue.iterator();
	}
	
	public class EngineMouse extends MouseAdapter
	{
		private RenderableItem hover;
		private int x, y, dx, dy;
		
		@Override
		public void mousePressed(MouseEvent e) 
		{
			if (e.getButton() == 1)
			{
				final RenderableItem newItem = getTop(e.getX(), e.getY());
				System.out.println(newItem);
				if (newItem == null)
				{
					if (knownItem != null)
						knownItem.setItemState(ItemState.CLICKED, false);
				}
				else
				{
					newItem.setItemState(ItemState.CLICKED, true);
					if (knownItem != newItem && knownItem != null)
						knownItem.setItemState(ItemState.CLICKED, false);
				}
				
				knownItem = newItem;
				x = e.getX();
				y = e.getY();
			}
			if (e.getButton() == 2)
//				viewZ = renderQueue.isEmpty() ? 0 : getMaxZ();
				setZ(renderQueue.isEmpty() ? 0 : getMaxZ());
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			if (knownItem != null)
			{
				dx = e.getX() - x;
				dy = e.getY() - y;
				x = e.getX();
				y = e.getY();
				knownItem.setLocation(knownItem.getX() + dx, knownItem.getY() + dy);
			}
			else
			{
				dx = e.getX() - x;
				dy = e.getY() - y;
				x = e.getX();
				y = e.getY();
				incOffsetX((int) (-dx));
				incOffsetY((int) (-dy));
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e)
		{
			final RenderableItem item = getTop(e.getX(), e.getY());
			if (hover != null && hover != item)
				hover.setItemState(ItemState.IDLE, true);
			if (item != null)
			{
				item.setItemState(ItemState.HOVER, false);
				hover = item;
			}
		}
	}
}
