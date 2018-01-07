public abstract class Surface
{
  public MatrixPair mm = new MatrixPair(); // The modeling matrix for this object.
  public RGBColor color = new RGBColor();
  public RGBColor specularColor = new RGBColor();
  public double shininess = 0.0f;
  public double reflectivity = -1.0f;
  
  public abstract boolean hit(Ray r, double tMin, double tMax, double time, HitRecord record);
  
  public abstract boolean shadowHit(Ray r, double tMin, double tMax, double time);
  
  public abstract Vector3 normalAt(Vector3 point);
  
  public RGBColor getColorAt(Vector3 point)
  {
    return color;
  }
  
  public RGBColor getSpecularColorAt(Vector3 point)
  {
    return specularColor;
  }
  
  public double getShininessAt(Vector3 point)
  {
    return shininess;
  }
  
  public abstract void dump();
  
  public Ray getLocalRay(Ray r)
  {
    Vector3 worldOrigin = r.getOrigin();
	 Vector3 worldDirection = r.getDirection();
    Vector3 localOrigin = mm.Inv.image(worldOrigin);
	 Vector3 localDir = mm.Inv.directionImage(worldDirection);
	 
	 Ray ray = new Ray(localOrigin, localDir);
	 ray.normalize();
	 
	 return ray;
  }
}