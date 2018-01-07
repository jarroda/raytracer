/*
  This class models a disk of radius 1 lying in the
  xz-plane, centered at the origin. The visible side of
  the disk points up, so that the normals are vertical.
*/
public class Disk extends Surface
{
  public Disk(RGBColor color)
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
	
	double distanceFromOrigin2 = record.point.body[0] * record.point.body[0] + 
	                                                   record.point.body[2] * record.point.body[2]; 
																		
  if ( distanceFromOrigin2 > 1 )
    return false;

   record.point = mm.image(record.point);																		
   return true;
	  
  }
  
  public boolean shadowHit(Ray ray, double tMin, double tMax, double time)
  {
    Ray r = getLocalRay(ray);
	 
	 if ( Math.abs(r.direction.body[1]) <= Util.nearlyZero )
	   return false;
		
    double t = - r.origin.body[1] / r.direction.body[1];	 
	 
	 boolean outOfRange = (t < tMin) || (t > tMax);

	 if ( outOfRange  )
      return false;
					 
	Vector3 point = r.pointAt(t);
	
	double distanceFromOrigin2 = point.body[0] * point.body[0] + 
	                                                    point.body[2] * point.body[2]; 
																		
  if ( distanceFromOrigin2 > 1 )
    return false;
	 																		
   return true;
  }
  
  public Vector3 normalAt(Vector3 point)
  {
    Vector3 v =  new Vector3(0, 1, 0);

	 return mm.transformNormal(v);
   }
  
  public void dump()
  {
    System.out.println("Disk ");
	 System.out.println("Radius 1, centered at origin, in xz-plane");
	 System.out.println();
	 System.out.print("Color: ");
	 color.dump();
	 System.out.println();
  }
}
