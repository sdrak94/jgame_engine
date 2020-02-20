package engine.io.out.graphics.model;

import java.awt.image.BufferedImage;

public class Tile extends BufferedImage
{
	public Tile(BufferedImage bufferedImage)
	{
		super(bufferedImage.getColorModel(), bufferedImage.getRaster(), bufferedImage.getColorModel().isAlphaPremultiplied(), null);
	}
}
