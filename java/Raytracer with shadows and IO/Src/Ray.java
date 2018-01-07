/**
  This class models a ray, which has an origin and
  a direction. Methods are provided to set the ray
  from origin and direction vectors, to set the origin
  and direction separately, to get the origin and
  direction, to find jthe point corresponding to a
  given parameter value, to normalize the ray (make
  the direction have unit length), and to print the
  ray information to standard out.
*/
public class Ray
{
  /**
    The origin of the ray.
  */
  public Vector3 origin;
  /**
    The direction of the ray.
  */
  public Vector3 direction;
  
  /**
    Creates a ray from the given origin
	 and direction.
	 
	 @param start The origin of the ray.
	 @param dir The direction of the ray.
  */
  public Ray(Vector3 start, Vector3 dir)
  {
    origin = new Vector3(start);
	 direction = new Vector3(dir);
  }	 
  
  /**
    Sets the ray from the given origin
	 and direction.
	 
	 @param start The origin of the ray.
	 @param dir The direction of the ray.
  */
  public void set(Vector3 start, Vector3 dir)
  {
    origin = new Vector3(start);
	 direction = new Vector3(dir);
  }	 
  
  /**
    Sets the origin of the ray.
	 
	 @param start The new origin of the ray.
  */
  public void setOrigin(Vector3 start)
  {
    origin = new Vector3(start);
  }
  
  /**
    Sets the direction of the ray.
	 
	 @param dir The new direction of the ray.
  */
  public void setDirection(Vector3 dir)
  {
	 direction = new Vector3(dir);
  }	 
  
  /**
    Returns the origin of the ray.
	 
	 @return The origin of the ray.
  */
  public Vector3 getOrigin()
  {
    return new Vector3(origin);
  }
  
  /**
    Returns the direction of the ray.
	 
	 @return The direction of the ray.
  */
  public Vector3 getDirection()
  {
    return new Vector3(direction);
  }
  
  /**
    Returns the point on the ray corresponding to
	 the given value.
	 
	 @param A "ray-coordinate" value.
	 @return The point on the ray corresponding to
	         the given value.
  */
  public Vector3 pointAt(double t)
  {
    return Vector3Util.sum(origin, Vector3Util.scalarMultiple(t, direction));
  }
  
  /**
    Normalizes the ray - makes the direction have
	 unit length.
	 
	 @return The normalized version of the ray.
  */
  public Ray normalize()
  {
    return new Ray(origin, Vector3Util.normalize(direction));
  }
  
  /**
    Print the ray information to standard out.
	 Primarily for debugging.
  */
  public void dump()
  {
    System.out.print("Origin: ");
	 origin.dump();
	 System.out.println();
	 System.out.print("Direction: ");
	 direction.dump();
	 System.out.println();
  }
}