package pokedex;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

public class Test
{

	
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("You have 4 seconds to target the item!");
		Thread.sleep(4000);
		final Point p = MouseInfo.getPointerInfo().getLocation();
		
		final int x = (int) p.getX();
		final int y = (int) p.getY();
		
		System.out.println("Point registered");
		Thread.sleep(4000);
		
		final Random rnd = new Random();
		final Robot r = new Robot();
		
		while(true)
		{
			final int x1 = x + rnd.nextInt(20);
			final int y1 = y + rnd.nextInt(20);
			
			r.mouseMove(x1, y1);
			r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			Thread.sleep(20 + rnd.nextInt(10));
			r.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			
			Thread.sleep(100 + rnd.nextInt(20));
			r.mouseMove(x1 + 15, y1 + 20);
			Thread.sleep(10 + rnd.nextInt(20));
			r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(20 + + rnd.nextInt(10));
			r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(2200 + + rnd.nextInt(200));
		}
	}
}
