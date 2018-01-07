/**
  A class of static methods to manipulate three-dimensional
  vectors. Methods are provided to negate a vector, to
  form scalar multiples and quotients of a vector, to
  form the sum and difference of two vectors, to compute
  the length and length squared of a  vector, to 
  normalize a vector, to compute the dot product of two
  vectors, to compute the cross product of two vectors,
  to get the sine and the cosine of the angle between two
  vectors, to get unit vectors in the x, y, and z-directions,
  to get the index of the component with minimum absolute
  value, to compute the perp of a vector, and to compute the
  scalar triple product of three vectors.
  
  @version 1 September 2006
  @author Bob Crawford
*/
public class Vector3Util
{
  /**
    Returns the negative of the given vector.
	 
	 @param v The vector to be negated.
	 @return The negative of the given vector.
  */
  public static Vector3 negate(Vector3 v)
  {
    return new Vector3(-v.body[0], -v.body[1], -v.body[2]);
  }
  
  /**
    Returns the scalar multiple of the given number and
	 vector.
	 
	 @param k The scalar.
	 @param v The vector.
	 @return The given scalar times the given vector.
  */
  public static Vector3 scalarMultiple(double k, Vector3 v)
  {
    return new Vector3(k * v.body[0], k * v.body[1], k * v.body[2]);
  }

  /**
    Returns the scalar multiple of the given number and
	 vector.
	 
	 @param v The vector.
	 @param k The scalar.
	 @return The given scalar times the given vector.
  */
  public static Vector3 scalarMultiple(Vector3 v, double k)
  {
    return new Vector3(k * v.body[0], k * v.body[1], k * v.body[2]);
  }
  
  /**
    Returns the scalar quotient of the given vector divided
	 by the given number.
	 
	 @param v The vector.
	 @param k The scalar.
	 @return The given vector divided by the given scalar.
  */  
  public static Vector3 scalarQuotient(Vector3 v, double k)
  {
    return new Vector3(v.body[0] / k, v.body[1] / k, v.body[2] / k);
  }
  
  /**
    Returns the sum of the two given vectors.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The sum of the two given vectors.
  */
  public static Vector3 sum(Vector3 v, Vector3 w)
  {
    return new Vector3(v.body[0] + w.body[0], v.body[1] + w.body[1], v.body[2] + w.body[2]);
  }
  
  /**
    Returns the difference between the two given vectors.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The first vector minus the second vector.
  */
  public static Vector3 difference(Vector3 v, Vector3 w)
  {
    return new Vector3(v.body[0] - w.body[0], v.body[1] - w.body[1], v.body[2] - w.body[2]);
  }
  
  /**
    Returns the square of the length of the given vector.
	 
	 @param v The vector whose length squared is desired.
	 @return The square of the length of the given vector.
  */
  public static double lengthSquared(Vector3 v)
  {
    return v.body[0] * v.body[0] + v.body[1] * v.body[1] + v.body[2] * v.body[2];
  }

  /**
    Returns the length of the given vector.
	 
	 @param v The vector whose length is desired.
	 @return The length of the given vector.
  */
  public static double length(Vector3 v)
  {
    return (double)Math.sqrt(v.body[0] * v.body[0] + v.body[1] * v.body[1] + v.body[2] * v.body[2]);
  }
  
  /**
    Returns the normalized version of the given vector -
	 a unit length vector that points in the same 
	 direction.
	 
	 @param v The given vector.
	 @return The normalized version of the given vector.
  */
  public static Vector3 normalize(Vector3 v)
  {
    double lenInv = 1.0f / length(v);
	 return new Vector3(v.body[0] * lenInv, v.body[1] * lenInv, v.body[2] * lenInv);
  }
  
  /**
    Returns the dot product of the given vectors.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The dot product of the two given vectors.
  */
  public static double dotProduct(Vector3 v, Vector3 w)
  {
    return v.body[0] * w.body[0] + v.body[1] * w.body[1] + v.body[2] * w.body[2];
  }

  /**
    Returns the cross product of the given vectors.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The cross product of the two given vectors.
  */  
  public static Vector3 crossProduct(Vector3 v, Vector3 w)
  {
    return new Vector3(v.body[1] * w.body[2] - v.body[2] * w.body[1],
	                    v.body[2] * w.body[0] - v.body[0] * w.body[2],
							  v.body[0] * w.body[1] - v.body[1] * w.body[0]);
  }
  
  /**
    Returns the sine of the angle between the
	 given vectors.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The sine of the angle between 
	         the two given vectors.	 
  */
  public double getSine(Vector3 v, Vector3 w)
  {
    return length(crossProduct(v, w)) / ( length(v) * length(w) );
  }
  
  /**
    Returns the cosine of the angle between the
	 given vectors.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The cosine of the angle between 
	         the two given vectors.	 
  */
  public double getCosine(Vector3 v, Vector3 w)
  {
    return dotProduct(v, w) / ( length(v) * length(w) );    
  }
  
  /**
    Returns the unit vector in the x-direction.
	 
	 @return The unit vector in the x-direction.
  */
  public static Vector3 getUnitX()
  {
    return new Vector3(1.0f, 0.0f, 0.0f);
  }
  
  /**
    Returns the unit vector in the y-direction.
	 
	 @return The unit vector in the y-direction.
  */
  public static Vector3 getUnitY()
  {
    return new Vector3(0.0f, 1.0f, 0.0f);
  }	 
  
  /**
    Returns the unit vector in the z-direction.
	 
	 @return The unit vector in the z-direction.
  */
  public static Vector3 getUnitZ()
  {
    return new Vector3(0.0f, 0.0f, 1.0f);
  }
 
  /**
    Returns the index of the component of the
	 given vector which is smallest in 
	 absolute value.
	 
	 @param v The vector to be inspected.
	 @return The index of the component with the
	         minimum absolute value.
  */
  public static int indexOfMinAbsValue(Vector3 v)
  {
    int minSpot = 0;
	 
	 if ( Math.abs(v.body[1]) < Math.abs(v.body[minSpot]) )
	   minSpot = 1;
	 if ( Math.abs(v.body[2]) < Math.abs(v.body[minSpot]) )
	   minSpot = 2;
	 return minSpot;
  } 
  
  /**
    Returns a vector perpendicular to the 
	 given vector.
	 
	 @param v The given vector.
	 @return A vector perpendicular to the 
	 	      given vector.
  */
  public static Vector3 perp(Vector3 v)
  {
    int minSpot = indexOfMinAbsValue(v);
	 if ( minSpot == 0 )
	   return normalize(new Vector3(0, v.body[2], -v.body[1]));
	 else if ( minSpot == 1 )
	   return normalize(new Vector3(v.body[2], 0, -v.body[0]));
	 else
	   return normalize(new Vector3(v.body[1], -v.body[0], 0));
  }
  
  /**
    Returns the scalar triple product of the three given vectors.
	 
	 @param a The first vector.
	 @param b The second vector.
	 @param c The third vector.
	 @return The scalar triple product of the given vectors.
  */
  public static double scalarTripleProduct(Vector3 a, Vector3 b, Vector3 c)
  {
    return dotProduct(a, crossProduct(b, c));
  }
  
  public static Vector3 polyNormal(Vector3 a, Vector3 b, Vector3 c)
  {
    Vector3 bma = difference(b, a);
	 Vector3 cma = difference(c, a);
	 Vector3 nor = crossProduct(bma, cma);
	 return normalize(nor);
  }
}