public class InfiniteCylinder extends Surface
{
  public InfiniteCylinder(RGBColor color)
  {
	 this.color = color;
  }
  
  public boolean hit(Ray ray, double tMin, double tMax, double time, HitRecord record)
  {
    Ray r = getLocalRay(ray);
	 
	 double a = r.direction.body[0] * r.direction.body[0] + r.direction.body[2] * r.direction.body[2];
	 double b = 2 * (r.origin.body[0] * r.direction.body[0] + r.origin.body[2] * r.direction.body[2]);
	 double c = r.origin.body[0] * r.origin.body[0] + r.origin.body[2] * r.origin.body[2] - 1;
	 
	 DoubleList roots = Util.solveQuadraticPositive(a, b, c);
	 
	 int numRoots = roots.size();
	 
	 if ( numRoots == 0 )
	   return false;
		
	 double t = roots.get(0);
	 boolean outOfRange = (t < tMin) || (t > tMax);
	 if ( outOfRange && (numRoots == 2) )
	 {
	   t = roots.get(1);
		outOfRange = (t < tMin) || (t > tMax);
	 }
	 
    if ( outOfRange )
	   return false; 
	 else
	 {
	   record.t = t;
		record.point = mm.image(r.pointAt(t));
		record.s = this;
	 
      return true;
	 }
	  
  }
  
  public boolean shadowHit(Ray ray, double tMin, double tMax, double time)
  {
    Ray r = getLocalRay(ray);
	 
	 double a = r.direction.body[0] * r.direction.body[0] + r.direction.body[2] * r.direction.body[2];
	 double b = 2 * (r.origin.body[0] * r.direction.body[0] + r.origin.body[2] * r.direction.body[2]);
	 double c = r.origin.body[0] * r.origin.body[0] + r.origin.body[2] * r.origin.body[2] - 1;
	 
	 DoubleList roots = Util.solveQuadraticPositive(a, b, c);
	 
	 int numRoots = roots.size();
	 
	 if ( numRoots == 0 )
	   return false;
		
	 double t = roots.get(0);
	 boolean outOfRange = (t < tMin) || (t > tMax);
	 if ( outOfRange && (numRoots == 2) )
	 {
	   t = roots.get(1);
		outOfRange = (t < tMin) || (t > tMax);
	 }
	 
    return !outOfRange;
  }
  
  public Vector3 normalAt(Vector3 point)
  {
    Vector3 rawNormal = new Vector3(point.body[0], 0, point.body[2]);
	 Vector3 normal = mm.transformNormal(rawNormal);
	 return Vector3Util.normalize(normal);
  }
  
  public void dump()
  {
    System.out.println("Infinite Cylinder ");
	 System.out.println("Along y-axis, radius 1");
	 System.out.println();
	 System.out.print("Color: ");
	 color.dump();
	 System.out.println();
  }
}
