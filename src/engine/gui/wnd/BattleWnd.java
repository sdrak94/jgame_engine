package engine.gui.wnd;

import java.awt.Color;

import javax.swing.JProgressBar;

import engine.gui.misc.BattleInterface;
import engine.gui.misc.Pokebar;
import engine.manager.DelayManager;
import engine.model.TileGroup;
import engine.model.animation.MultiAnimation;
import engine.model.enums.Heading;

public class BattleWnd extends Wnd
{
	public BattleWnd(BattleInterface bInt)
	{	
		super(null);
		setTitle("Battle vs ???");
		add(bInt.getMyIcon());
		add(bInt.getEnIcon());
		add(bInt.getMyName());
		add(bInt.getMyLevel());
		add(bInt.getMyHealth());
		add(bInt.getEnName());
		add(bInt.getEnLevel());
		
		Pokebar bar1 = new Pokebar(bInt.getBattleInfo(), bInt.getMyHealth());
		bar1.setBounds(329, 200, 103, 20);
		getLayeredPane().add(bar1, 5);
		
		Pokebar bar2 = new Pokebar(bInt.getBattleInfo(), null);
		bar2.setBounds(102, 38, 100, 50);
		getLayeredPane().add(bar2, 5);
		
		JProgressBar expbar = new JProgressBar(0, 100);
		expbar.setBounds(50, 50, 132, 9);
		expbar.setForeground(Color.CYAN);
		expbar.setBackground(new Color(230, 230, 250));
		expbar.setValue(50);
		getLayeredPane().add(expbar, 5);

		final TileGroup tileGroup0 = new TileGroup(addImage("UserInterface.png", 225, 170, 6));
		//tileGroup0.attach(bar1,expbar,bInt.getMyName(),bInt.getMyLevel(),bInt.getMyHealth());
		
		final TileGroup tileGroup1 = new TileGroup(addImage("EnemyInterface.png", 20, 23, 6));
		//tileGroup1.attach(bar2,bInt.getEnName(),bInt.getEnLevel());
		
		tileGroup0.changeLoc(-400, 10);
		tileGroup1.changeLoc(400, 10);
		
		DelayManager.getInstance().addQueue(() -> bar1.update(1), 8000);
		
		final MultiAnimation multAnim0 = new MultiAnimation(tileGroup0, Heading.RI, 2, 9, 420, 0, null);
		
		final MultiAnimation multAnim1 = new MultiAnimation(tileGroup1, Heading.LE, 2, 9, 420, 0, null);

		multAnim0.start();
		multAnim1.start();
		
		//MusicTable.getInstance().playSound("allah.mp3");
		
		setVisible(true);
	}
}
