package engine.io.out;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.io.out.graphics.render.RenderableItem;
import engine.model.enums.ItemState;
import engine.model.event.enums.EventType;
import engine.model.interfaces.IRenderable;

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
					incRenderZ(rot);//viewZ += rot;
				else
				{
					knownItem.setLocationZ(knownItem.getLocationZ() + rot);
					//viewZ = knownItem.getZ();
					setRenderZ(knownItem.getLocationZ());
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
			if (maxZ < renderable.getLocationZ()) maxZ = renderable.getLocationZ();
		return maxZ;
	}
	
	
	private RenderableItem getTop(int x, int y, boolean calcOffset, boolean sealedOnly)
	{
		if (calcOffset)
		{
			x += _renderRect.getLocationX();
			y += _renderRect.getLocationY();
		}
		
		RenderableItem top = null;
		for (RenderableItem renderable : renderQueue)
		{
			if (sealedOnly && !renderable.getItemState(ItemState.SEALED))
				continue;
			
			if (renderable.getItemState(ItemState.CLICKABLE) && renderable.getLocationZ() <= getRenderZ() && renderable.contains(x, y))
			{
				if (top == null)
					top = renderable;
				else if (top.getLocationZ() < renderable.getLocationZ())
					top = renderable;
			}
				
		}
		
		if (top == null && calcOffset == false)
			return getTop(x, y, true, false);
		
		return top;
	}
	
	@Override
	protected void renderInit()
	{
		final Graphics2D g = (Graphics2D) fetchBufferStrategy().getDrawGraphics();

		if (_clearGraphics == null )
			_clearGraphics = new AffineTransform(g.getTransform());
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
				renderableItem.renderInit(g, _renderRect.getLocationX(), _renderRect.getLocationY());
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
			setRenderZ(getRenderZ() < renderable.getLocationZ() ? renderable.getLocationZ() : getRenderZ());
	}
	
	@Override
	public void rmRenderQueue(IRenderable renderable)
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
		private IRenderable hover;
		private int x, y, dx, dy;
		
		@Override
		public void mousePressed(MouseEvent e) 
		{
			if (e.getButton() == 1)
			{
				final RenderableItem newItem = getTop(e.getX(), e.getY(), false, true);
//				System.out.println(newItem);
				if (newItem == null)
				{
					if (knownItem != null)
					{

						knownItem.setItemState(ItemState.CLICKED, false);
					}
				}
				else
				{
					newItem.setItemState(ItemState.CLICKED, true);
					newItem.handleEvent(EventType.EVT_MOUSE__ITEM_SELECTED, e);
					if (knownItem != newItem && knownItem != null)
						knownItem.setItemState(ItemState.CLICKED, false);
				}
				
				knownItem = newItem;
				x = e.getX();
				y = e.getY();
			}
			if (e.getButton() == 2)
//				viewZ = renderQueue.isEmpty() ? 0 : getMaxZ();
				setRenderZ(renderQueue.isEmpty() ? 0 : getMaxZ());
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			if (knownItem != null && knownItem.getItemState(ItemState.MOVEABLE))
			{
				dx = e.getX() - x;
				dy = e.getY() - y;
				x = e.getX();
				y = e.getY();
				knownItem.setLocation(knownItem.getLocationX() + dx, knownItem.getLocationY() + dy);
			}
			else
			{
				dx = e.getX() - x;
				dy = e.getY() - y;
				x = e.getX();
				y = e.getY();
				incRenderX((int) (-dx));
				incRenderY((int) (-dy));
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e)
		{
			final IRenderable item = getTop(e.getX(), e.getY(), false, true);
			if (hover != null)
			{
				if (hover == item)
					hover.handleEvent(EventType.EVT_MOUSE__ITEM_HOVERED, e);
				else
				{
					hover.handleEvent(EventType.EVT_MOUSE__ITEM_HOVERED_END, e);
					hover = null;
				}
			}
			if (item != null && item != hover)
			{
				hover = item;
				hover.handleEvent(EventType.EVT_MOUSE__ITEM_HOVERED_INIT, e);
			}
		}
	}
}
