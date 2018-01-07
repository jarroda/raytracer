/**
  A class to model three-dimensional vectors. Methods
  are provided to set and get the components and to
  print the vector to standard out.
  
  @version 1 October 2006
  @author Bob Crawford
*/
public class Vector3
{
  /**
    An array to hold the components of the vector.
  */
  public double[] body;
  
  /**
    Constructs a new vector with the default value
	 (0, 0, 0).
  */ 
  public Vector3()
  {
    body = new double[3];
    body[0] = 0.0;
	 body[1] = 0.0;
	 body[2] = 0.0;
  }
  
  /**
    Constructs a new vector with the given
	 components.
	 
	 @param x The x-component of the vector.
	 @param y The y-component of the vector.
	 @param z The z-component of the vector.
  */
  public Vector3(double x, double y, double z)
  {
    body = new double[3];
    body[0] = x;
	 body[1] = y;
	 body[2] = z;
  }
  
  /**
    Constructs a new vector which is a copy
	 of the given vector. Essentially a clone.
	 
	 @param v The vector to be copied.
  */
  public Vector3(Vector3 v)
  {
    body = new double[3];
	 body[0] = v.body[0];
	 body[1] = v.body[1];
	 body[2] = v.body[2];
  }
  
  /**
    Sets the components of the vector to the
	 given values.
	 
	 @param x The x-component of the vector.
	 @param y The y-component of the vector.
	 @param z The z-component of the vector.
  */
  public void set(double x, double y, double z)
  {
    body[0] = x;
	 body[1] = y;
	 body[2] = z;
  }
  
  /**
    Sets the x-component of the vector to 
	 the given value.
	 
	 @param x The x-component of the vector.
  */
  public void setX(double x)
  {
    body[0] = x;
  }
  
  /**
    Sets the y-component of the vector to 
	 the given value.
	 
	 @param y The y-component of the vector.
  */
  public void setY(double y)
  {
    body[1] = y;
  }
  
  /**
    Sets the z-component of the vector to 
	 the given value.
	 
	 @param z The z-component of the vector.
  */
  public void setZ(double z)
  {
    body[2] = z;
  }	 
  
  /**
    Returns the x-component of the vector.
	 
	 @return The x-component of the vector.
  */
  public double getX()
  {
    return body[0];
  }	 	 	
  
  /**
    Returns the y-component of the vector.
	 
	 @return The y-component of the vector.
  */
  public double getY()
  {
    return body[1];
  }
  
  /**
    Returns the z-component of the vector.
	 
	 @return The z-component of the vector.
  */
  public double getZ()
  {
    return body[2];
  }	 
  
  /**
    Prints the vector to standard out.
	 Mainly for debugging.
  */
  public void dump()
  {
    System.out.print("(" + body[0] + ", " + body[1] + ", " + body[2] + ")");
  }
}
