package creator.wnd;

import static creator.init.CreatorInit.TILE_CONST;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import creator.model.JMenuList;
import engine.io.out.Renderer;
import engine.io.out.graphics.render.RenderableTile;
import engine.io.out.graphics.render.model.RGrid;
import engine.io.out.graphics.render.model.ui.RCheckBox;
import engine.manager.PositionManager2D;
import engine.model.Position2D;
import engine.model.enums.ItemState;

public class CreatorWnd extends JFrame
{
	private final PositionManager2D positionManager = new PositionManager2D(40, 3, 0);
	
	private final Renderer _mainRenderer;
	private final Renderer _seleRenderer;
	
	private final RCheckBox rShowGrid;
	
	public CreatorWnd(Renderer viewRenderer, Renderer seleRenderer)
	{
		_mainRenderer = viewRenderer;
		_seleRenderer = seleRenderer;
		
		setTitle("DEngine MapCreator");
		setSize(1300,850);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_mainRenderer.setSize(new Dimension(4024,4024));
		
		rShowGrid = new RCheckBox(false);
		rShowGrid.setItemState(ItemState.SEALED, true);
		rShowGrid.setText("Show Grid");
		rShowGrid.setLocation(50, 5);
		rShowGrid.setSizeX(100);
		
		_mainRenderer.addRenderQueue(rShowGrid);
		
		final RGrid rGrid = new RGrid(100, 100, TILE_CONST);
		rGrid.setLocation(5, 50);
		_mainRenderer.addRenderQueue(rGrid);
		
		
		final JMenuBar menuBar = new JMenuBar();
		
		final JMenuList file = new JMenuList("File");
		final JMenuList opti = new JMenuList("Options");
		
		final JMenuItem newf = new JMenuItem("New Map");
		final JMenuItem open = new JMenuItem("Load Image");
		final JMenuItem load = new JMenuItem("Load Map");
		final JMenuItem save = new JMenuItem("Save");
		final JMenuItem saveas = new JMenuItem("Save As");
		final JMenuItem exit = new JMenuItem("Exit");
		
		final JMenuItem clear = new JMenuItem("Clear");
		final JMenuList setti = new JMenuList("Settings");
		final JMenuItem opt0 = new JMenuItem("Selection Tool");
		final JMenuItem opt1 = new JMenuItem("Option 1");
		final JMenuItem opt2 = new JMenuItem("Option 2");
		final JMenuItem opt3 = new JMenuItem("Option 3");

		newf.addActionListener((e) -> onNew());
		clear.addActionListener((e) -> onClear());
		open.addActionListener((e) -> onOpen());
		exit.addActionListener((e) -> onClose());
		
		file.add(newf, open, load, save, saveas, exit);
		
		setti.add(opt0, opt1, opt2, opt3);
		opti.add(setti);opti.add(clear);
		
		menuBar.add(file);
		menuBar.add(opti);
		
		add(menuBar, BorderLayout.NORTH);
		
		final JPanel UP = new JPanel(new BorderLayout());
		UP.setBackground(Color.RED);
		final JPanel RIGH = new JPanel(new BorderLayout());
		RIGH.setBackground(Color.GREEN);
		final JPanel DOWN = new JPanel(new BorderLayout());
		DOWN.setBackground(Color.YELLOW);
		
		RIGH.add(seleRenderer, BorderLayout.CENTER);
		
//		final JScrollPane SCRL_SCREEN = new JScrollPane(viewRenderer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		final JScrollPane SCRL_SELECT = new JScrollPane(RIGH, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

//		SCRL_SCREEN.setSize(new Dimension(10000,1000));
//		SCRL_SCREEN.setPreferredSize(new Dimension(1000,1000));
//		
//		SCRL_SELECT.setSize(new Dimension(1000,1000));
//		SCRL_SELECT.setPreferredSize(new Dimension(1000,1000));
		
		final JSplitPane splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewRenderer, RIGH);
		//splitPaneH.setEnabled(false);
		splitPaneH.setDividerLocation(1025);
		
		UP.add(splitPaneH, BorderLayout.CENTER);
		
		final JSplitPane splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, UP, DOWN);
		//splitPaneV.setEnabled(false);
		splitPaneV.setDividerLocation(700);
		
		add(splitPaneV, BorderLayout.CENTER);
		
		//renderer.setVisible(false);
		
		setVisible(true);
	}
	
	private void onNew()
	{
		
	}
	
	private void onClear()
	{
		if (JOptionPane.showConfirmDialog(this, "Are you sure that you want to clear your canvas?\nAll progress will be lost!", "Progress not saved!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			clearMain();
	}
	
	private void onOpen()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("./"));
		final FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		final int ans = fileChooser.showOpenDialog(CreatorWnd.this);
		if (ans == JFileChooser.APPROVE_OPTION)
		{
			final File file = fileChooser.getSelectedFile();
			if (file.isDirectory()) // load the icons onto the panel asap, they are supposed to be already cropped!
			{	final int maxBytes = TILE_CONST * TILE_CONST * 8;
				for (File f : file.listFiles((f, s) -> {return s.endsWith(".png");}))
				{
					if (f.length() > maxBytes) //ignore
						continue;
					final BufferedImage img = loadImage(f);
					if (img.getHeight() == TILE_CONST && img.getWidth() == TILE_CONST)
						appendTile(img);
				}
			}
			else if (JOptionPane.showConfirmDialog(this, "Do you want to break this image into tiles? The process will take some time to finish!", "Not tiled texture!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				loadSelectables(file);
		}
	}
	
	private void loadSelectables(File file)
	{
		new Thread(() ->
		{
			final BufferedImage image = loadImage(file);
			final int W = image.getWidth();
			final int H = image.getHeight();
			positionManager.setMaxWidth(W);
			for (int chunkY=0;chunkY*18<=H;chunkY++)
			{
				for (int chunkX=0;chunkX*18<=W;chunkX++)
				{
					appendSubimage(chunkX, chunkY, image);
				}
			}
		}).start();
	}
	
	private void appendSubimage(final int chunkX, final int chunkY, final BufferedImage image)
	{
		try
		{
			final BufferedImage chunk = image.getSubimage(18 * chunkX - chunkX + 1, 18 * chunkY - chunkY + 1, 16, 16);
			appendTile(chunk);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void appendTile(BufferedImage buffImg)
	{
		final RenderableTile renderableItem = new RenderableTile(buffImg);
		final Position2D pos2d = positionManager.getNext();
		renderableItem.setLocation(pos2d.getX(), pos2d.getY());
		_seleRenderer.addRenderQueue(renderableItem);
	}
	
	private void onClose()
	{
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	private BufferedImage loadImage(File f)
	{
		try
		{
			return ImageIO.read(f);
		}
		catch (Exception e)
		{
			return new BufferedImage(TILE_CONST, TILE_CONST, BufferedImage.TYPE_BYTE_GRAY);
		}
	}
	
	private void clearMain()
	{
		positionManager.reset();
		_mainRenderer.clearRenderQueue();
	}
}
