//package engine.model;
//
//import static org.lwjgl.opengl.GL11.*;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//
//import de.matthiasmann.twl.utils.PNGDecoder;
//import de.matthiasmann.twl.utils.PNGDecoder.Format;
//
//public class DecodedImage
//{
//	private final ByteBuffer buffer;
//	private final int _w;
//	private final int _h;
////	private final int _id;
//	
//	public DecodedImage(ByteArrayInputStream data) throws IOException
//	{
//		PNGDecoder dec = new PNGDecoder(data);
//		_w = dec.getWidth();
//		_h = dec.getHeight();
//		
//		buffer = ByteBuffer.allocateDirect(4* _w * _h);
//		dec.decode(buffer, _w * 4, Format.RGBA);
//		buffer.flip();
////		
////		_id = glGenTextures();
////		glBindTexture(GL_TEXTURE_2D, _id);
////		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, _w, _h, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
////		glBindTexture(GL_TEXTURE_2D, 0);	
//	}
//	
//	public ByteBuffer getImage()
//	{
//		return buffer;
//	}
//	
//	public int getWidth()
//	{
//		return _w;
//	}
//	
//	public int getHeight()
//	{
//		return _h;
//	}
//	
//	public int getId()
//	{
//		return _id;
//	}
//	
//	public void bind()
//	{
//		glBindTexture(GL_TEXTURE_2D, _id);
//	}
//	
//	public void unbind()
//	{
//		glBindTexture(GL_TEXTURE_2D, 0);
//	}
//}
