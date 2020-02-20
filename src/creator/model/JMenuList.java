package creator.model;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class JMenuList extends JMenu
{
	public JMenuList(String str)
	{
		super(str);
	}
	
	public void add(JMenuItem ... item)
	{
		for (JMenuItem it : item)
			add(it);
	}
}
