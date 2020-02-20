//package creator.wnd;
//
//import static creator.init.CreatorInit.TILE_CONST;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Frame;
//import java.awt.Menu;
//import java.awt.MenuBar;
//import java.awt.MenuItem;
//import java.awt.ScrollPane;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.image.BufferedImage;
//import java.io.File;
//
//import javax.imageio.ImageIO;
//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JSplitPane;
//import javax.swing.filechooser.FileNameExtensionFilter;
//
//import engine.io.out.Renderer;
//import engine.io.out.graphics.render.RenderableTile;
//import engine.manager.PositionManager2D;
//import engine.model.Position2D;
//
//public class CreatorAWT extends Frame
//{
//	private final PositionManager2D positionManager = new PositionManager2D(40, 3, 0);
//	private final Renderer _mainRenderer;
//	private final Renderer _seleRenderer;
//	
//	public CreatorAWT(Renderer mainRenderer, Renderer seleRenderer)
//	{
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent we) {
//				System.exit(0);
//			}
//		});
//		
//		_mainRenderer = mainRenderer;
//		_seleRenderer = seleRenderer;
//		setTitle("DEngine MapCreator");
//		setSize(1300,850);
//		setLayout(new BorderLayout());
//		setLocationRelativeTo(null);
//		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		final MenuBar menuBar = new MenuBar();
//		
//		final Menu file = new Menu("File");
//		final Menu opti = new Menu("Options");
//		
//		final MenuItem newf = new MenuItem("New Map");
//		final MenuItem open = new MenuItem("Load Image");
//		final MenuItem load = new MenuItem("Load Map");
//		final MenuItem save = new MenuItem("Save");
//		final MenuItem saveas = new MenuItem("Save As");
//		final MenuItem exit = new MenuItem("Exit");
//		
//		final MenuItem clear = new MenuItem("Clear");
//		final Menu setti = new Menu("Settings");
//		final MenuItem opt0 = new MenuItem("Selection Tool");
//		final MenuItem opt1 = new MenuItem("Option 1");
//		final MenuItem opt2 = new MenuItem("Option 2");
//		final MenuItem opt3 = new MenuItem("Option 3");
//
//		newf.addActionListener((e) -> onNew());
//		clear.addActionListener((e) -> onClear());
//		open.addActionListener((e) -> onOpen());
//		exit.addActionListener((e) -> onClose());
//		
//		file.add(newf);file.add(open);file.add(load);file.add(save);file.add(saveas);file.add(exit);
//		
//		setti.add(opt0);setti.add(opt1);setti.add(opt2);setti.add(opt3);
//		opti.add(setti);opti.add(clear);
//		
//		menuBar.add(file);
//		menuBar.add(opti);
//		
//		setMenuBar(menuBar);
//		//add(menuBar, BorderLayout.NORTH);
//		
//		final JPanel UP = new JPanel(new BorderLayout());
//		UP.setBackground(Color.RED);
//		final JPanel RIGH = new JPanel(new BorderLayout());
//		RIGH.setBackground(Color.GREEN);
//		final JPanel DOWN = new JPanel(new BorderLayout());
//		RIGH.setBackground(Color.GREEN);
//		
//		final ScrollPane SCRL_SCREEN = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);//new ScrollPane(mainRenderer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		final ScrollPane SCRL_SELECT = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);//new ScrollPane(seleRenderer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		SCRL_SCREEN.add(mainRenderer);
//		SCRL_SELECT.add(seleRenderer);
//
//		final JSplitPane splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, SCRL_SCREEN, SCRL_SELECT);
//		splitPaneH.setEnabled(true);
//		splitPaneH.setDividerLocation(1025);
//		splitPaneH.setResizeWeight(0.5f);
//		
//		UP.add(splitPaneH, BorderLayout.CENTER);
//		
//		final JSplitPane splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, UP, DOWN);
//		splitPaneV.setEnabled(true);
//		splitPaneV.setDividerLocation(700);
//		
//		add(splitPaneV, BorderLayout.CENTER);
//		
//		mainRenderer.setVisible(true);
//		
//		setVisible(true);
//	}
//	
//	private void onNew()
//	{
//		
//	}
//	
//	private void onClear()
//	{
//		if (JOptionPane.showConfirmDialog(this, "Are you sure that you want to clear your canvas?\nAll progress will be lost!", "Progress not saved!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
//		{
//			_seleRenderer.clearRenderQueue();
//		}
//	}
//	
//	private void onOpen()
//	{
//		JFileChooser fileChooser = new JFileChooser();
//		fileChooser.setCurrentDirectory(new File("./"));
//		final FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
//		fileChooser.setFileFilter(filter);
//		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//		final int ans = fileChooser.showOpenDialog(CreatorAWT.this);
//		if (ans == JFileChooser.APPROVE_OPTION)
//		{
//			final File file = fileChooser.getSelectedFile();
//			if (file.isDirectory()) // load the icons onto the panel asap, they are supposed to be already cropped!
//			{	final int maxBytes = TILE_CONST * TILE_CONST * 8;
//				for (File f : file.listFiles((f, s) -> {return s.endsWith(".png");}))
//				{
//					if (f.length() > maxBytes) //ignore
//						continue;
//					final BufferedImage img = loadImage(f);
//					if (img.getHeight() == TILE_CONST && img.getWidth() == TILE_CONST)
//						appendTile(img);
//				}
//			}
//			else if (JOptionPane.showConfirmDialog(this, "Do you want to break this image into tiles? The process will take some time to finish!", "Not tiled texture!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
//				loadSelectables(file);
//		}
//	}
//	
//	private void loadSelectables(File file)
//	{
//		new Thread(() ->
//		{
//			final BufferedImage image = loadImage(file);
//			final int W = image.getWidth();
//			final int H = image.getHeight();
//			for (int chunkX=0;chunkX*18<=W;chunkX++)
//			{
//				for (int chunkY=0;chunkY*18<=H;chunkY++)
//				{
//					try
//					{
//						final BufferedImage chunk = image.getSubimage(18 * chunkX - chunkX + 1, 18 * chunkY - chunkY + 1, 16, 16);
//						//if (lazyMod && ++c>1500)
//						//	break outer;
//						
//						/*final ByteArrayOutputStream arrayIn = new ByteArrayOutputStream();
//						ImageIO.write(chunk, "png", arrayIn);
//						final int size = arrayIn.toByteArray().length;
//						if (size > 866)*/
//						appendTile(chunk);
//						//Thread.sleep(3);
//						//ImageIO.write(chunk, "png", new File("./tiles/" + chunkX + "-" + chunkY + ".png"));
//					}
//					catch (Exception e)
//					{
//						e.printStackTrace();
//					}
//				}
//				//selectionPane.setPreferredSize(positionManager.getPrefferedDimension());
//				//positionManager.forceMode();
//			}
//		}).start();
//	}
//	
//	private void appendTile(BufferedImage buffImg)
//	{
//		final RenderableTile renderableItem = new RenderableTile(buffImg);
//		final Position2D pos2d = positionManager.getNext();
//		renderableItem.setLocation(pos2d.getX(), pos2d.getY());
//		_seleRenderer.addRenderQueue(renderableItem);
//		
//	}
//	
//	private void onClose()
//	{
//		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
//	}
//	
//	private BufferedImage loadImage(File f)
//	{
//		try
//		{
//			return ImageIO.read(f);
//		}
//		catch (Exception e)
//		{
//			return new BufferedImage(TILE_CONST, TILE_CONST, BufferedImage.TYPE_BYTE_GRAY);
//		}
//	}
//}
