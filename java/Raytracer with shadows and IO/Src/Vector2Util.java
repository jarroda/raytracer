/**
  A class of static methods to manipulate two-dimensional
  vectors. Methods are provided to negate a vector, to
  form scalar multiples and quotients of a vector, to
  form the sum and difference of two vectors, to compute
  the length and length squared of a  vector, to 
  normalize a vector, to compute the dot product of two
  vectors, to get unit vectors in the x and y-directions,
  to compute the perp of a vector, and to compute the
  perp-dot product of two vectors.
  
  @version 1 September 2006
  @author Bob Crawford
*/
public class Vector2Util
{
  /**
    Returns the negative of the given vector.
	 
	 @param v The vector to be negated.
	 @return The negative of the given vector.
  */
  public static Vector2 negate(Vector2 v)
  {
    return new Vector2(-v.body[0], -v.body[1]);
  }
  
  /**
    Returns the scalar multiple of the given number and
	 vector.
	 
	 @param k The scalar.
	 @param v The vector.
	 @return The given scalar times the given vector.
  */
  public static Vector2 scalarMultiple(double k, Vector2 v)
  {
    return new Vector2(k * v.body[0], k * v.body[1]);
  }

  /**
    Returns the scalar multiple of the given number and
	 vector.
	 
	 @param v The vector.
	 @param k The scalar.
	 @return The given scalar times the given vector.
  */
  public static Vector2 scalarMultiple(Vector2 v, double k)
  {
    return new Vector2(k * v.body[0], k * v.body[1]);
  }

  /**
    Returns the scalar quotient of the given vector divided
	 by the given number.
	 
	 @param v The vector.
	 @param k The scalar.
	 @return The given vector divided by the given scalar.
  */  
  public static Vector2 scalarQuotient(Vector2 v, double k)
  {
    return new Vector2(v.body[0] / k, v.body[1] / k);
  }
  
  /**
    Returns the sum of the two given vectors.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The sum of the two given vectors.
  */
  public static Vector2 sum(Vector2 v, Vector2 w)
  {
    return new Vector2(v.body[0] + w.body[0], v.body[1] + w.body[1]);
  }
  
  /**
    Returns the difference between the two given vectors.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The first vector minus the second vector.
  */
  public static Vector2 difference(Vector2 v, Vector2 w)
  {
    return new Vector2(v.body[0] - w.body[0], v.body[1] - w.body[1]);
  }
  
  /**
    Returns the square of the length of the given vector.
	 
	 @param v The vector whose length squared is desired.
	 @return The square of the length of the given vector.
  */
  public static double lengthSquared(Vector2 v)
  {
    return v.body[0] * v.body[0] + v.body[1] * v.body[1];
  }

  /**
    Returns the length of the given vector.
	 
	 @param v The vector whose length is desired.
	 @return The length of the given vector.
  */
  public static double length(Vector2 v)
  {
    return (double)Math.sqrt(v.body[0] * v.body[0] + v.body[1] * v.body[1]);
  }
  
  /**
    Returns the normalized version of the given vector -
	 a unit length vector that points in the same 
	 direction.
	 
	 @param v The given vector.
	 @return The normalized version of the given vector.
  */
  public static Vector2 normalize(Vector2 v)
  {
    double lenInv = 1.0f / length(v);
	 return new Vector2(v.body[0] * lenInv, v.body[1] * lenInv);
  }
  
  /**
    Returns the dot product of the given vectors.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The dot product of the two given vectors.
  */
  public static double dotProduct(Vector2 v, Vector2 w)
  {
    return v.body[0] * w.body[0] + v.body[1] * w.body[1];
  }
  
  /**
    Returns the unit vector in the x-direction.
	 
	 @return The unit vector in the x-direction.
  */
  public static Vector2 getUnitX()
  {
    return new Vector2(1.0f, 0.0f);
  }
  
  /**
    Returns the unit vector in the y-direction.
	 
	 @return The unit vector in the y-direction.
  */
  public static Vector2 getUnitY()
  {
    return new Vector2(0.0f, 1.0f);
  }	 
  
  /**
    Returns the perp of the given vector - a vector
	 perpendicular to the given vector.
	 
	 @param v The vector whose perp is desired.
	 @return The perp of the given vector.
  */
  public static Vector2 perp(Vector2 v)
  {
    return new Vector2(-v.body[1], v.body[2]);
  }
  
  /**
    Returns the perp dot product of the given vectors -
	 the dot product of the perp of the first vector and
	 the second vector.
	 
	 @param v The first vector.
	 @param w The second vector.
	 @return The perp dot product of the given vectors.
  */
  public static double perpDot(Vector2 v, Vector2 w)
  {
    return dotProduct(perp(v), w);
  }
}