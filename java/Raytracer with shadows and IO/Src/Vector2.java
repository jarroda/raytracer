/**
  A class to model two-dimensional vectors.
  Methods are provided to set and get the
  components and to print the vector to
  standard out.
  
  @version 1 September 2006
  @author Bob Crawfordthe
*/
public class Vector2
{
  /**
    An array to hold the components
	 of the vector.
  */
  public double[] body;
  
  /**
    Creates the default zero vector.
  */
  public Vector2()
  {
    body = new double[2];
    body[0] = 0.0f;
	 body[1] = 0.0f;
  }
  
  /**
    Creates a new vector with the given
	 components.
	 
	 @param x The first component of the vector.
	 @param y The second component of the vector.
  */
  public Vector2(double x, double y)
  {
    body = new double[2];
    body[0] = x;
	 body[1] = y;
  }
  
  /**
    Creates a new vector which is a copy
	 of the given vector. Essentially a
	 clone.
	 
	 @param v The vector to be copied.
  */
  public Vector2(Vector2 v)
  {
    body = new double[2];
	 body[0] = v.body[0];
	 body[1] = v.body[1];
  }
  
  /**
    Sets the vector to the given 
	 components.
	 
	 @param x The first coordinate.
	 @param y The second coordinate.
  */
  public void set(double x, double y)
  {
    body[0] = x;
	 body[1] = y;
  }
  
  /**
    Sets the x-coordinate of the vector.
	 
	 @param x The new x-coordinate.
  */
  public void setX(double x)
  {
    body[0] = x;
  }
  
  /**
    Sets the y-coordinate of the vector.
	 
	 @param y The new y-coordinate.
  */
  public void setY(double y)
  {
    body[1] = y;
  }
  
  /**
    Returns the x-coordinate of the vector.
	 
	 @return The x-coordinate of the vector.
  */
  public double getX()
  {
    return body[0];
  }	 	 	
  
  /**
    Returns the y-coordinate of the vector.
	 
	 @return The y-coordinate of the vector.
  */
  public double getY()
  {
    return body[1];
  }	 
  
  /**
    Prints the vector to standard out.
	 Mainly for debugging.
  */
  public void dump()
  {
    System.out.print("(" + body[0] + ", " + body[1] + ")");
  }
}
