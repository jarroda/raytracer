import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
  A class to manipulate arrays of RGBColors - 
  effectively arrays of pixels. Constructors are
  provided for varying background colors, as 
  well as methods for getting and setting 
  individual pixel values, clamping the values
  in the entire array to the [0, 1] range,
  converting the array to a one or two
  dimensional array of packed pixels in ARGB
  format, creating a BufferedImage from the
  array, and saving the array to disk as a
  png file.
  
  @version 1 September 2006
  @author Bob Crawford
*/
public class RGBArray
{
  /**
    The array holding the RGBColors.
  */
  public RGBColor[][] body;
  /**
    The dimensions of the array.
  */
  public int width, height;
  
  /**
    Creates an RGBArray with the given dimensions,
	 filled with the default background color, white.
	 
	 @param width The width for the array.
	 @param height The height for the array.
  */
  public RGBArray(int width, int height)
  {
    this.width = width;
	 this.height = height;
    body = new RGBColor[width][height];  
	 for ( int x = 0; x < width; x++ )
	   for ( int y = 0; y < height; y++ )
		  body[x][y] = new RGBColor();
  }
  
  /**
    Creates an RGBArray with the given dimensions,
	 filled with the given background color.
	 
	 @param width The width for the array.
	 @param height The height for the array.
	 @param red The red component of the background.
	 @param green The green component of the background.
	 @param blue The blue component of the background.
  */
  public RGBArray(int width, int height, 
                  double red, double green, double blue)
  {
    this.width = width;
	 this.height = height;
    body = new RGBColor[width][height];
	 for ( int x = 0; x < width; x++ )
	   for ( int y = 0; y < height; y++ )
		  body[x][y] = new RGBColor(red, green, blue);
  }
  
  /**
    Sets the given entry to the given RGB color value.
	 
	 @param x The x coordinate of the point to be set.
	 @param y The y coordinate of the point to be set.
	 @param red The red component of the new color.
	 @param green The green component of the new color.
	 @param blue The blue component of the new color.
  */
  public void setRGB(int x, int y,
                     double red, double green, double blue)
  {
    body[x][y] = new RGBColor(red, green, blue);
  }
  
  /**
    Sets the given entry to the given RGBColor value.
	 
	 @param x The x coordinate of the point to be set.
	 @param y The y coordinate of the point to be set.
	 @param c The new color of the point.
  */
  public void setRGB(int x, int y, RGBColor c)
  {
    body[x][y] = new RGBColor(c);
  }
  
  /**
    Sets the given entry to the given Java color value.
	 
	 @param x The x coordinate of the point to be set.
	 @param y The y coordinate of the point to be set.
	 @param c The new Java color of the point.
  */
  public void setRGB(int x, int y, Color c)
  {
    body[x][y] = new RGBColor(c);
  }
  
  /**
    Returns the color at the given location
	 as an RGBColor.
	 
	 @param x The x coordinate of the location.
	 @param y The y coordinate of the location.
	 @return The color at the point (x, y).
  */
  public RGBColor getRGBColor(int x, int y)
  {
    return body[x][y];
  }
  
  /**
    Returns the color at the given location
	 as a Java color.
	 
	 @param x The x coordinate of the location.
	 @param y The y coordinate of the location.
	 @return The color at the point (x, y).
  */
  public Color getColor(int x, int y)
  {
    return (body[x][y]).getColor();
  }
  
  /**
    Returns the red component of the color 
	 at the given location.
	 
	 @param x The x coordinate of the location.
	 @param y The y coordinate of the location.
	 @return The red component of the color at 
	         the point (x, y).
  */
  public double getRed(int x, int y)
  {
    return (body[x][y]).getRed();
  }
  
  /**
    Returns the green component of the color 
	 at the given location.
	 
	 @param x The x coordinate of the location.
	 @param y The y coordinate of the location.
	 @return The green component of the color at 
	         the point (x, y).
  */
  public double getGreen(int x, int y)
  {
    return (body[x][y]).getGreen();
  }
  
  /**
    Returns the blue component of the color 
	 at the given location.
	 
	 @param x The x coordinate of the location.
	 @param y The y coordinate of the location.
	 @return The blue component of the color at 
	         the point (x, y).
  */
  public double getBlue(int x, int y)
  {
    return (body[x][y]).getBlue();
  }
  
  /**
    Clamps all the colors in the array to the
	 [0, 1] range.
  */
  public void clamp()
  {
	 for ( int y = 0; y < height; y++ )
	   for ( int x = 0; x < width; x++ )
		  body[x][y].clamp();
  }
  
  /**
    Returns the colors in the array
	 as a two-dimensional array of
	 RGB packed pixels.
  */
  public int[][] getPixels()
  {
    int r, g, b;
    int[][] pixels = new int[width][height];
	 
	 for ( int y = 0; y < height; y++ )
	   for ( int x = 0; x < width; x++ )
		{
        r = (int)Math.floor(256 * body[x][y].color[0]);  
		  g = (int)Math.floor(256 * body[x][y].color[1]);
		  b = (int)Math.floor(256 * body[x][y].color[2]);
		  if ( r < 0 )
		    r = 0;
		  if ( g < 0 )
		    g = 0;
		  if ( b < 0 )
		    b = 0;
		  if ( r > 255 )
		    r = 255;
		  if ( g > 255 )
		    g = 255;
		  if ( b > 255 )
		    b = 255;
		  pixels[x][y] = ( 255 << 24 ) | ( r << 16 ) | ( g << 8 ) | b;	 
		}
    return pixels;
  }	 
  
  /**
    Returns the colors in the array
	 as a one-dimensional array of
	 RGB packed pixels in row major order.
  */
  public int[] getPixelList()
  {
    int r, g, b;
    int[] pixels = new int[width * height];
	 int index = 0;
	 
	 for ( int y = 0; y < height; y++ )
	   for ( int x = 0; x < width; x++ )
		{
        r = (int)Math.floor(256 * body[x][y].color[0]);  
		  g = (int)Math.floor(256 * body[x][y].color[1]);
		  b = (int)Math.floor(256 * body[x][y].color[2]);
		  if ( r < 0 )
		    r = 0;
		  if ( g < 0 )
		    g = 0;
		  if ( b < 0 )
		    b = 0;
		  if ( r > 255 )
		    r = 255;
		  if ( g > 255 )
		    g = 255;
		  if ( b > 255 )
		    b = 255;
		  pixels[index++] = ( 255 << 24 ) | ( r << 16 ) | ( g << 8 ) | b;	 
		}
    return pixels;
  }
  
  /**
    Returns the array interpreted as a
	 BufferedImage of type TYPE_INT_ARGB.
	 
	 @return The array as a buffered image.
  */
  public BufferedImage getImage()
  {
    int[] pixels = getPixelList();
	 BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	 image.setRGB(0, 0, width, height, pixels, 0, width);
	 return image;
  }
  
  /**
    Writes the array to the given file as
	 a png file.
	 
	 @param fileName The name of the file to be 
	                 written.
  */
  public void writePNG(String fileName)
  {
    BufferedImage image = getImage();
	 try
	 {
	   ImageIO.write((RenderedImage)image, "png", new File(fileName));
    }
	 catch ( IOException e )
	 {
	   System.out.println(e);
    }				
  }
  
  /**
    Reads an image from the given file into
	 the array.
	 
	 @param fileName The name of the file containing
	                 the image.
  */
  public void readImage(String fileName)
  {
    BufferedImage image;
	 int[] pixels;
	 
	 try
	 {
	   image = ImageIO.read(new File(fileName));
		width = image.getWidth();
		height = image.getHeight();
		body = new RGBColor[width][height];
	   for ( int x = 0; x < width; x++ )
	     for ( int y = 0; y < height; y++ )
		    body[x][y] = new RGBColor();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	   for ( int y = 0; y < height; y++ )
	     for ( int x = 0; x < width; x++ )
		  {
		    body[x][y].color[0] = pixels[y * width + x] & 0xFF0000;
		    body[x][y].color[1] = pixels[y * width + x] & 0x00FF00;
		    body[x][y].color[2] = pixels[y * width + x] & 0x0000FF;
		  }
	 }
	 catch ( IOException e )
	 {
	   System.out.println(e);
	 }
  }	 
}