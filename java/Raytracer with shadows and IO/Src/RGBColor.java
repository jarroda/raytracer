import java.awt.*;

/**
  A class to represent RGB colors using an array 
  of three doubles. A variety of constructors are
  provided, along with get and set methods, a
  method to clamp the component values to the
  [0, 1] range, and a method to print the color
  values to standard out.
  
  @version 1 September 2006
  @author Bob Crawford
*/
public class RGBColor
{
  /**
    An array to hold the color values. It is public
	 since there are no side effects associated with
	 setting or getting the color.
  */
  public double[] color;
  
  /**
    Creates a new color with the default value of white.
  */
  public RGBColor()
  {
    color = new double[3];
    color[0] = 1.0f;
	 color[1] = 1.0f;
	 color[2] = 1.0f;
  }
  
  /**
    Creates a new color from the given red, green,
	 and blue values.
	 
	 @param r The red value for the new color.
	 @param g The green value for the new color.
	 @param b The blue value for the new color. 
  */
  public RGBColor(double r, double g, double b)
  {
    color = new double[3];
    color[0] = r;
	 color[1] = g;
	 color[2] = b;
  }
  
  /**
    Creates a new color from another RGBColor
	 value. Essentially a clone.
	 
	 @param c The RGBColor to duplicate.
  */
  public RGBColor(RGBColor c)
  {
    color = new double[3];
	 color[0] = c.color[0];
	 color[1] = c.color[1];
	 color[2] = c.color[2];
  }
  
  /**
    Creates a new color from a Java color.
	 
	 @param c The Java color to be converted to
	          an RGBColor.
  */
  public RGBColor(Color c)
  {
	 color = new double[3];
    float[] cc = null;
	 
	 cc = c.getColorComponents(cc);
	 color[0] = cc[0];
	 color[1] = cc[1];
	 color[2] = cc[2];
  }
  
  /**
    Sets the color from the given red, green,
	 and blue values.
	 
	 @param r The red value for the color.
	 @param g The green value for the color.
	 @param b The blue value for the color.
  */
  public void setRGB(double r, double g, double b)
  {
    color[0] = r;
	 color[1] = g;
	 color[2] = b;
  }
  
  /**
    Sets the color from the given RGBColor.
	 
	 @param c The RGBColor to be copied.
  */
  public void setRGB(RGBColor c)
  {
	 color[0] = c.color[0];
	 color[1] = c.color[1];
	 color[2] = c.color[2];
  }
  
  /**
    Sets the color from the given Java color.
	 
	 @param c The Java color to be used to set
	          the RGBColor.
  */
  public void setRGB(Color c)
  {
    float[] cc = null;
	 
	 cc = c.getColorComponents(cc);
	 color[0] = cc[0];
	 color[1] = cc[1];
	 color[2] = cc[2];
  }
  
  /**
    Sets the red value of the color to
	 the given double value.
	 
	 @param r The value to set the red
	          component to.
  */
  public void setRed(double r)
  {
    color[0] = r;
  }
  
  /**
    Sets the green value of the color to
	 the given double value.
	 
	 @param g The value to set the green
	          component to.
  */
  public void setGreen(double g)
  {
    color[1] = g;
  }
  
  /**
    Sets the blue value of the color to
	 the given double value.
	 
	 @param b The value to set the blue
	          component to.
  */
  public void setBlue(double b)
  {
    color[2] = b;
  }
  
  /**
    Returns the array of RGB values.
	 
	 @return The array of RGB values.
  */
  public double[] getRGB()
  {
    return color;
  }
  
  /**
    Returns the Java color value corresponding
	 to this RGB value.
	 
	 @return The Java color corresponding to
	         this RGB value.
  */
  public Color getColor()
  {
    clamp();
	 return new Color((float)color[0], (float)color[1], (float)color[2]);
  }
  
  /**
    Returns the red component of this color.
	 
	 @return The red component of this color.
  */
  public double getRed()
  {
    return color[0];
  }
  
  /**
    Returns the green component of this color.
	 
	 @return The green component of this color.
  */
  public double getGreen()
  {
    return color[1];
  }
  
  /**
    Returns the blue component of this color.
	 
	 @return The blue component of this color.
  */
  public double getBlue()
  {
    return color[2];
  }

  /**
    Clamps all of the color components to the
	 [0, 1] range.
  */  
  public void clamp()
  {
    for ( int i = 0; i < 3; i++ )
	 {
	   if ( color[i] < 0.0f )
		  color[i] = 0.0f;
		if ( color[i] > 1.0f )
		  color[i] = 1.0f;
	 }  	 	 	 	 	 	 
  }
  
  /**
    Prints the triple of color components to
	 standard out.
  */
  public void dump()
  {
    System.out.print("(" + color[0] + ", " +
	                        color[1] + ", " + 
									color[2] + ")");
  }	 
}
