public class RGBUtil
{
   public static RGBColor termProduct(RGBColor a, RGBColor b)
	{
	  return new RGBColor(a.color[0] * b.color[0],
	                                       a.color[1] * b.color[1],
														a.color[2] * b.color[2]);
	}
	
	public static RGBColor scalarMultiple(double k, RGBColor c)
	{
	  return new RGBColor(k * c.color[0], k * c.color[1], k * c.color[2]);
	}
	
	public static RGBColor sum(RGBColor a, RGBColor b)
	{
	  return new RGBColor(a.color[0] + b.color[0],
	                                       a.color[1] + b.color[1],
														a.color[2] + b.color[2]);
	}
}