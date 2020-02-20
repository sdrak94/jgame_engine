package anim;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils
{
	public static boolean isCompatibleGIF(File file) throws IOException
	{
		final byte[] b = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		return b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8' && b[5] == 'a';
	}
}
