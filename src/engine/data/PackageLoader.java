package engine.data;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;

public abstract class PackageLoader<E>
{
	protected final HashMap<String, E> resources = new HashMap<>();
	
	protected void loadPackage(String name)
	{
		System.out.print("Loading " + name + "... ");
		final long t0 = System.nanoTime();
		File f = new File("./data/" + name + ".package");
		byte buffer[] = new byte[(int)f.length()];
		int c=0;
		try(FileInputStream in = new FileInputStream(f))
		{
			in.read(buffer);
			for (int i=0;i<buffer.length;i++,c++)
			{
				byte[] nameBuffer = Arrays.copyOfRange(buffer, i, i+32);
				byte[] sizeBuffer = Arrays.copyOfRange(buffer, i+32, i+48);
				final int size = Integer.parseInt(new String(sizeBuffer).trim());
				byte[] dataBuffer = Arrays.copyOfRange(buffer, i+48, i+size+48);
				i += size + 47;
				populate(new String(nameBuffer).trim(), dataBuffer);
			}
			finished(c, t0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void finished(int loadedCount, long t0)
	{
		System.out.println("[OK], Loaded " + loadedCount + " in: " + (double) (System.nanoTime() - t0) / 1000000 + " ms.");
	}
	
	protected abstract void populate(String name, byte[] dataBuffer) throws Exception;
}
