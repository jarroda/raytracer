/*
  This class models the xz plane (in a single color)
*/

public class Plane extends Surface
{
  public Plane(RGBColor color)
  {
	 this.color = color;
  }
  
  public boolean hit(Ray ray, double tMin, double tMax, double time, HitRecord record)
  {
    Ray r = getLocalRay(ray);
	 
	 if ( Math.abs(r.direction.body[1]) <= Util.nearlyZero )
	   return false;
		
    double t = - r.origin.body[1] / r.direction.body[1];	 
	 
	 boolean outOfRange = (t < tMin) || (t > tMax);

	 if ( outOfRange  )
      return false;
			 
   record.t = t;
	record.point = r.pointAt(t);
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
  
  public void dump()
  {
    System.out.println("The xz-plane");
	 System.out.println();
	 System.out.print("Color: ");
	 color.dump();
	 System.out.println();
  }
}
