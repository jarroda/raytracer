/*
  This class models a checkerboard (the xz plane divided into 1 x 1 squares).
*/

public class Checkerboard extends Surface
{
  public RGBColor checkColor;
  
  public Checkerboard(RGBColor color, RGBColor checkColor)
  {
	 this.color = color;
	 this.checkColor = checkColor;
  }
  
  public boolean hit(Ray ray, double tMin, double tMax, double time, HitRecord record)
  {
    Ray r = getLocalRay(ray);
	 r = r.normalize();
	 
	 if ( Math.abs(r.direction.body[1]) <= Util.nearlyZero )
	   return false;
		
    double t = - r.origin.body[1] / r.direction.body[1];	 
	 
	 boolean outOfRange = (t < tMin) || (t > tMax);

	 if ( outOfRange  )
      return false;
			 
   record.t = t;
	record.point = mm.image(r.pointAt(t));
	record.s = this;
	 
   return true;
  }
  
  public boolean shadowHit(Ray ray, double tMin, double tMax, double time)
  {
    Ray r = getLocalRay(ray);	 
	 if ( Math.abs(r.direction.body[1]) <= Util.nearlyZero )
	   return false;
		
    double t = - r.origin.body[1] / r.direction.body[1];	 
	 
	 boolean outOfRange = (t < tMin) || (t > tMax);

    return ( !outOfRange );
	}
  
  public Vector3 normalAt(Vector3 point)
  {
	 return new Vector3(0, 1, 0);
  }

  public RGBColor getColorAt(Vector3 point)
  {
    Vector3 q = mm.inverseImage(point);
    int ix;
    int iz;

    if ( q.body[0] >= 0 )
      ix = (int)q.body[0];
    else
      ix = (int)(q.body[0] - 1);
    if ( q.body[2] >= 0 )
      iz = (int)q.body[2];
    else
      iz = (int)(q.body[2] - 1);

    if ( (ix + iz) % 2 == 0 )
      return color;
    else
      return checkColor;
  }
    
  public void dump()
  {
    System.out.println("A checkerboard");
	 System.out.println();
	 System.out.print("Base color: ");
	 color.dump();
	 System.out.println();
	 System.out.print("Check color: ");
	 checkColor.dump();
	 System.out.println();
  }
}
