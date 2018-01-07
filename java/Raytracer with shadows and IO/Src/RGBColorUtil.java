/**
  A class of static methods to operate on RGB colors.
  Methods are provided for arithmetic operations on
  RGB colors. In all cases, new RGBColor values are
  returned, as opposed to modifying the arguments.
  
  @version 1 September 2006
  @author Bob Crawford
*/
public class RGBColorUtil
{
  /**
    Returns the negative of the given RGBColor.
	 
	 @param c The RGBColor to be negated.
	 @return The negative of the given RGBColor.
  */
  public static RGBColor negate(RGBColor c)
  {
    return new RGBColor(-c.color[0], -c.color[1], -c.color[2]);
  }
  
  /**
    Multiplies the given color by the given scalar.
	 
	 @param c The color to be multiplied by a scalar.
	 @param k The scalar multiplier.
	 @return The given color multiplied by the given scalar.
  */
  public static RGBColor multiplyBy(RGBColor c, double k)
  {
    return new RGBColor(k * c.color[0], k * c.color[1], k * c.color[2]);
  }
  
  /**
    Multiplies the given color by the given scalar.
	 
	 @param k The scalar multiplier.
	 @param c The color to be multiplied by a scalar.
	 @return The given color multiplied by the given scalar.
  */
  public static RGBColor multiplyBy(double k, RGBColor c)
  {
    return new RGBColor(k * c.color[0], k * c.color[1], k * c.color[2]);
  }

  /**
    Divides the given color by the given scalar.
	 
	 @param c The color to be divided by a scalar.
	 @param k The scalar divisor.
	 @return The given color multiplied by the given scalar.
  */  
  public static RGBColor divideBy(RGBColor c, double k)
  {
    return new RGBColor(c.color[0] / k, c.color[1] / k, c.color[2] / 2);
  }
  
  /**
    Adds together two given colors, term by term.
	 
	 @param a The first color summand.
	 @param b The second color summand.
	 @return The term by term sum of the two given colors.
  */
  public static RGBColor plus(RGBColor a, RGBColor b)
  {
    return new RGBColor(a.color[0] + b.color[0], a.color[1] + b.color[1], a.color[2] + b.color[2]);
  }
  
  /**
    Finds the term by term difference between two given colors.
	 
	 @param a The first color.
	 @param b The color to be subtracted from the first color.
	 @return The first color minus the second color, term by term.
  */
  public static RGBColor minus(RGBColor a, RGBColor b)
  {
    return new RGBColor(a.color[0] - b.color[0], a.color[1] - b.color[1], a.color[2] - b.color[2]);
  }
  
  /**
    Finds the term by term product of two colors.
	 
	 @param a The first color.
	 @param b The second color.
	 @return The term by term product of the two given colors.
  */
  public static RGBColor times(RGBColor a, RGBColor b)
  {
    return new RGBColor(a.color[0] * b.color[0], a.color[1] * b.color[1], a.color[2] * b.color[2]);
  }
  
  /**
    Finds the term by term quotient of two colors.
	 
	 @param a The first (dividend) color.
	 @param b The second (divisor) color.
	 @return The term by term quotient of the first color
	         divided by the second color.
  */
  public static RGBColor div(RGBColor a, RGBColor b)
  {
    return new RGBColor(a.color[0] / b.color[0], a.color[1] / b.color[1], a.color[2] / b.color[2]);
  }
}