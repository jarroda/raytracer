public class Sphere extends Surface
{
  public Vector3 center;
  public double radius;
  
  public Sphere(Vector3 center, double radius, RGBColor color)
  {
    this.center = center;
	 this.radius = radius;
	 this.color = color;
  }
  
  public boolean hit(Ray ray, double tMin, double tMax, double time, HitRecord record)
  {
    Ray r = getLocalRay(ray);
    Vector3 originMinusCenter = Vector3Util.difference(r.origin, center);
	 
	 double a = Vector3Util.dotProduct(r.direction, r.direction);
	 double b = Vector3Util.dotProduct(Vector3Util.scalarMultiple(2.0, r.direction), originMinusCenter);
	 double c = Vector3Util.dotProduct(originMinusCenter, originMinusCenter) - radius * radius;
	 
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
    Vector3 originMinusCenter = Vector3Util.difference(r.origin, center);
	 
	 double a = Vector3Util.dotProduct(r.direction, r.direction);
	 double b = Vector3Util.dotProduct(Vector3Util.scalarMultiple(2.0, r.direction), originMinusCenter);
	 double c = Vector3Util.dotProduct(originMinusCenter, originMinusCenter) - radius * radius;
	 
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
    Vector3 rawNormal = Vector3Util.difference(point, center);
	 Vector3 normal = mm.transformNormal(rawNormal);
	 return Vector3Util.normalize(normal);
  }
  
  public void dump()
  {
    System.out.println("Sphere of radius " + radius);
	 System.out.print("Centered at ");
	 center.dump();
	 System.out.println();
	 System.out.print("Color: ");
	 color.dump();
	 System.out.println();
  }
}
