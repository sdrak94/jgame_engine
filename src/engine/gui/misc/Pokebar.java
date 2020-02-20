package engine.gui.misc;

import java.awt.Color;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import engine.gui.misc.BattleInterface.PokeLabel;
import engine.model.Battle;


public class Pokebar extends JProgressBar
{
	private int physicalValue;
	private final PokeLabel _health;
	
	public Pokebar(Battle battleData, PokeLabel health)
	{	super(0, battleData.getMaxHp());
		setValue(battleData.getCurHp());
		physicalValue = battleData.getCurHp();
		_health = health;
		setDoubleBuffered(true);
		setBackground(new Color(100, 100, 100));
		refresh();
	}
	
	public void update(int newHp)
	{	physicalValue = newHp;
		new BarWorker().execute();
	}
	
	private float getPercent()
	{
		return getValue() * 100 / getMaximum();
	}
	
	public void refresh()
	{	if (_health != null)
			_health.setText(getValue() + " / " + getMaximum());
		final float percent = getPercent();
		if (percent <= 25)
			setForeground(Color.RED);
		else if (percent <= 50)
			setForeground(Color.ORANGE);
		else if (getForeground() != Color.GREEN)
			setForeground(Color.GREEN);
	}
	
	public class BarWorker extends SwingWorker<Integer, String>
	{
		@Override
		protected Integer doInBackground() throws Exception
		{
			final int physHp = physicalValue;
			final int barVal = getValue();
			int steps = 3;
			if (physHp < barVal)
			{	
				final int diff = barVal - physHp;
				if (diff < 3)
					steps =  diff;
				setValue(barVal - steps);
				refresh();
				Thread.sleep(40);
				return doInBackground();
			}
			else if (physHp > barVal)
			{	
				final int diff = physHp - barVal;
				if (diff < 3)
					steps = diff;
				setValue(barVal + steps);
				refresh();
				Thread.sleep(40);
				return doInBackground();
			}
			cancel(false);
			return 0;
		}
	}
}