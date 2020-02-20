package anim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import anim.model.Decoder;
import anim.model.FrameIterator;
import engine.io.out.HeavyRenderer;
import engine.io.out.RenderHint;
import engine.io.out.graphics.model.Frame;


public class Animator extends JFrame
{
	private final HeavyRenderer RENDER = new HeavyRenderer()
	{{
	}};
	
	private final Color defaultColor = getBackground();
	
	private final JPanel CENTER;
	private final JPanel SOUTH;
	
	private final JLabel POS_TEXT = new JLabel("Frame ?", SwingConstants.CENTER);
	
	
	private final FrameIterator frameIterator = new FrameIterator();
	
	private int renderSleep = 1000 / 30;
	private ASlider SLI;
	
	public Animator() throws IOException
	{	final long t0 = System.currentTimeMillis();
		POS_TEXT.setText("Frame 0");
		POS_TEXT.setFont(new Font("Times New Roman", 1, 20));
		setLayout(new BorderLayout());
		CENTER = new JPanel(new BorderLayout())
		{{
			add(RENDER, BorderLayout.CENTER);
		}};
//		PREV = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/backward.png")));
//		PLAY = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/play.png")));
//		PAUS = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/pause.png")));
//		STOP = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/stop.png")));
//		NEXT = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/forward.png")));
//		
//		PREVH = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/backward_h.png")));
//		PLAYH = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/play_h.png")));
//		PAUSH = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/pause_h.png")));
//		STOPH = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/stop_h.png")));
//		NEXTH = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/anim/model/forward_h.png")));
		
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		/**************************************************************************************************************************************************************************************************
		***************************************************************************************************************************************************************************************************
		**************************************************************************************************************************************************************************************************/
		//POS_TEXT.setVisible(false);
		RENDER.addHint(RenderHint.SHOW_FPS);
		add(CENTER, BorderLayout.CENTER);
		final JMenuBar menuBar = new JMenuBar()
		{{	add(new JMenu("File")
			{{	add(new AMenuItem("New", Animator.this::New));
				add(new AMenuItem("Open", Animator.this::Open));
				add(new AMenuItem("Clear", Animator.this::Clear));
				add(new JSeparator());
				add(new AMenuItem("Exit", Animator.this::Exit));
			}});
			add(new JMenu("Colors")
			{{	add(new AMenuItem(null, Animator.this::WHI){{setIcon(new ImageIcon(createIcon(Color.WHITE)));}});
				add(new AMenuItem(null, Animator.this::DEF){{setIcon(new ImageIcon(createIcon(defaultColor)));}});
				add(new AMenuItem(null, Animator.this::GRA){{setIcon(new ImageIcon(createIcon(Color.LIGHT_GRAY)));}});
				add(new AMenuItem(null, Animator.this::BLA){{setIcon(new ImageIcon(createIcon(Color.BLACK)));}});
				add(new AMenuItem(null, Animator.this::YEL){{setIcon(new ImageIcon(createIcon(Color.YELLOW)));}});
				add(new AMenuItem(null, Animator.this::RED){{setIcon(new ImageIcon(createIcon(Color.RED)));}});
			}});
			add(new JMenu("FPS")
			{{	add(new AMenuItem("30", () -> renderSleep = 1000/30));
				add(new AMenuItem("60", () -> renderSleep = 1000/60));
				add(new AMenuItem("120", () -> renderSleep = 1000/120));
				add(new AMenuItem("144", () -> renderSleep = 1000/144));
				add(new AMenuItem("165", () -> renderSleep = 1000/165));
				add(new AMenuItem("300", () -> renderSleep = 1000/300));
				add(new AMenuItem("Uncapped", () -> renderSleep = 0));
			}});
		}};
		add(new JPanel(new BorderLayout())
		{{
			add(menuBar, BorderLayout.NORTH);
			add(POS_TEXT, BorderLayout.SOUTH);
		}}, BorderLayout.NORTH);
		
		SOUTH = new JPanel(new BorderLayout())
		{{	
			add(new JPanel(new BorderLayout())
			{{
				add(new JSpinner()
				{{
					setPreferredSize(new Dimension(100, 20));
				}}, BorderLayout.WEST);
				add(new JPanel(new FlowLayout(FlowLayout.LEFT, 33, 5))
				{{
					//add(new AButton(PREV, PREVH, Animator.this::Play));
					//add(new AButton(PLAY, PLAYH, Animator.this::Play));
					//add(new AButton(NEXT, NEXTH, Animator.this::showLoop));
				}}, BorderLayout.EAST);
			}}, BorderLayout.SOUTH);
		}};
		add(SOUTH, BorderLayout.SOUTH);
		/**************************************************************************************************************************************************************************************************
		***************************************************************************************************************************************************************************************************
		**************************************************************************************************************************************************************************************************/
		System.out.println(System.currentTimeMillis() - t0);
		//pack();
		setVisible(true);
		final Thread renderLoop = new Thread(() ->
		{
			long t1,dt;
			while(true) try
			{	
				t1 = System.currentTimeMillis();
				RENDER.render();
				dt = (int) (renderSleep * 1.1) - (System.currentTimeMillis() - t1);
				if (dt > 0)
					Thread.sleep(dt);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		});
		renderLoop.start();
//		RENDER.setVisible(false);
	}
	
	private void Play()
	{
		while (frameIterator.hasNext()) try
		{
			Thread.sleep(frameIterator.get().getDuration());
			frameIterator.nextNow();
			SLI.toggleIgnore();
			SLI.setValue(frameIterator.getPosition() + 1);
			SLI.toggleIgnore();
			Update();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void showLoop()
	{
		final FrameIterator tempIter = new FrameIterator(frameIterator);
		final JFrame dlg = new JFrame();
		final JLabel tempRENDER = new JLabel(new ImageIcon(tempIter.get(0))); 
		dlg.add(tempRENDER);
		dlg.setLocationRelativeTo(this);
		dlg.setVisible(true);
		dlg.pack();
		new Thread(() ->
		{
			Frame frame = tempIter.get();
			while (dlg.isVisible()) try
			{
				System.out.println("Playing " + tempIter.getPosition());
				dlg.setTitle(tempIter.getPosition() + "/" + (tempIter.getSize() - 1));
				Thread.sleep(frame.getDuration());
				tempRENDER.setIcon(new ImageIcon(frame));
				frame = tempIter.hasNext() ? tempIter.next() : tempIter.reset();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}).start();
	}

	private void New()
	{
        JFileChooser chooser = new JFileChooser();
//        chooser.setCurrentDirectory(new File("C:/Data/Projects2/PokeAssets"));
        chooser.setFileFilter(new FileNameExtensionFilter("GIF File","gif"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
        	final File file = chooser.getSelectedFile();
        	try
        	{
        		final long t0 = System.currentTimeMillis();
        		if (Utils.isCompatibleGIF(file))
        		{	
        			Decoder de = new Decoder();
        			int i = de.read(new FileInputStream(file));
        			if (i != 0)
        				throw new Error();
            		System.out.println("Gif decoded " + de.getFrameCount() + " frames in " + (System.currentTimeMillis() - t0) + " ms.");
            		for (Frame frame : de)
            			frameIterator.add(frame);
            		
            		if (SLI != null)
            			SOUTH.remove(SLI);
            		SLI = new ASlider();
            		SOUTH.add(SLI, BorderLayout.NORTH);
            		Update();
        		}
        		else
        		{
        			JOptionPane.showMessageDialog(this, "That file is not compatible!", "Load failed!", JOptionPane.ERROR_MESSAGE);
        		}
        		
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        }
	}
	
	private void Open()
	{
	}
	
	private void Exit()
	{
		System.exit(1);
	}
	
	private void Clear()
	{
		frameIterator.clear();
		//RENDER.setIcon(null);
		SOUTH.remove(SLI);
		SLI = null;
	}
	
	private void Update()
	{
		POS_TEXT.setText("Frame " + frameIterator.getPosition() + "/" + (frameIterator.getSize() - 1));
		//RENDER.setIcon(new ImageIcon(frameIterator.get().getImage()));
	}
	
	private void RED(){RENDER.setRenderBackground(Color.RED);}
	private void YEL(){RENDER.setRenderBackground(Color.YELLOW);}
	private void GRA(){RENDER.setRenderBackground(Color.LIGHT_GRAY);}
	private void WHI(){RENDER.setRenderBackground(Color.WHITE);}
	private void DEF(){RENDER.setRenderBackground(defaultColor);}
	private void BLA(){RENDER.setRenderBackground(Color.BLACK);}
	
	private static class AMenuItem extends JMenuItem
	{
		public AMenuItem(String name, Runnable r)
		{
			super(name);
			addActionListener((a) -> r.run());
		}
	}
	
	private class ASlider extends JSlider
	{
		private volatile boolean ignore;
		
		public ASlider()
		{
			super(JSlider.VERTICAL, frameIterator.getSize());
			setValue(frameIterator.getPosition());
			setFocusable(false);
			setMajorTickSpacing(1);
			setPaintTicks(true);
			//setValue(POS);
			addChangeListener(new ChangeListener()
			{	@Override
				public void stateChanged(ChangeEvent arg0)
				{	
					if (!ignore && frameIterator.setPosition(getValue() - 1))
						Update();
				}
			});
		}
		
		public void toggleIgnore()
		{
			ignore = !ignore;
		}
	}
	
	private static class AButton extends JLabel
	{
		private final ImageIcon _icon;
		private final ImageIcon _iconh;
		
		private volatile boolean running;
		
		public AButton(ImageIcon icon, ImageIcon iconh, Runnable r)
		{
			super(icon);
			_icon = icon;
			_iconh = iconh;
			addMouseListener(new MouseListener()
			{
				@Override
				public void mouseClicked(MouseEvent arg0)
				{
					if (running)
						return;
					running = true;
					System.out.print("Start... ");
					new Thread(() ->
					{
						r.run();
						System.out.println("OK");
						running = false;
					}).start();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					setIcon(_iconh);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setIcon(_icon);
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}
			});
		}
		
		
	}
	
	public Image createIcon(Color color)
	{
		BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, 16, 16);
		return img;
	}

	public static void main(String[] args)
	{
		try
		{
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        System.setProperty("sun.awt.noerasebackground", "true");
	        new Animator();
	    }
		catch(Exception ex)
		{
	        ex.printStackTrace();
	    }
	}
}
