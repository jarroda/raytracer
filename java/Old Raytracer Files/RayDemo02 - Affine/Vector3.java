/**
  A class to manipulate 3-dimensional vectors.

  @version 1 June 2002
  @author Bob Crawford

*/

public class Vector3
{
  public double[] body;

  /**
    Initializes a new Vector3 (0, 0, 0).
  */
  Vector3()
  {
    body = new double[3];
  }

  /**
    Initializes a new Vector3 (x, y, z).

    @param x, y, z The vector coordinates
  */
  Vector3(double x, double y, double z)
  {
    body = new double[3];
    body[0] = x;
    body[1] = y;
    body[2] = z;
  }

  /**
    Initialize a new Vector3 equal to v.

    @param v The vector to be copied
  */
  Vector3(Vector3 v)
  {
    body = new double[3];
    for ( int i = 0; i < 3; i++ )
      body[i] = v.body[i];
  }

  /**
    Returns the magnitude of the current vector.

    @return The magnitude of the current vector
  */
  public double magnitude()
  {
    return Math.sqrt(body[0] * body[0] + body[1] * body[1] + body[2] * body[2]);
  }

  /**
    Returns the magnitude of the input vector.

    @param v The vector whose magnitude is desired.
    @return The magnitude of the input vector.
  */
  public static double magnitude(Vector3 v)
  {
    return Math.sqrt(v.body[0] * v.body[0] +
                     v.body[1] * v.body[1] +
                     v.body[2] * v.body[2]);
  }

  /**
    Returns the square of the magnitude of the vector.

    @return The square of the magnitude of the vector
  */
  public double magnitudeSquared()
  {
    return body[0] * body[0] + body[1] * body[1] + body[2] * body[2];
  }

  /**
    Returns the square of the magnitude of the input vector.

    @param v The vector whose magnitude squared is desired
    @return The square of the magnitude of the input vector
  */
  public static double magnitudeSquared(Vector3 v)
  {
    return v.body[0] * v.body[0] + v.body[1] * v.body[1] + v.body[2] * v.body[2];
  }

  /**
    Normalizes the vector (so that it maintains its direction but
    has length 1.
  */
  public void normalize()
  {
    double m = magnitude();
    for ( int i = 0; i < 3; i++ )
      body[i] = body[i] / m;
  }

  /**
    Returns the normalized version of the input vector.

    @param v The vector whose normalized version is desired.
    @return The normalized version of the input vector
  */
  public static Vector3 normalize(Vector3 v)
  {
    double m = v.magnitude();
    return new Vector3(v.body[0] / m, v.body[1] / m, v.body[2] / m);
  }

  /**
    Returns a normalized version of the current vector.

    @return The current vector, normalized.
  */
  public Vector3 makeNormalized()
  {
    double m = magnitude();
    return new Vector3(body[0] / m, body[1] / m, body[2] / m);
  }

  /**
    Returns the sum of two vectors.

    @param v1 The first vector to be added
    @param v2 The second vector to be added
    @return The sum of the two input vectors
  */
  public static Vector3 Sum(Vector3 v1, Vector3 v2)
  {
    return new Vector3(v1.body[0] + v2.body[0],
                       v1.body[1] + v2.body[1],
                       v1.body[2] + v2.body[2]);
  }

  /**
    Returns the difference of two vectors.

    @param v1 The first vector
    @param v2 The vector to be subtracted
    @return The difference of the two input vectors
  */
  public static Vector3 Difference(Vector3 v1, Vector3 v2)
  {
    return new Vector3(v1.body[0] - v2.body[0],
                       v1.body[1] - v2.body[1],
                       v1.body[2] - v2.body[2]);
  }

  /**
    Returns the scalar multiple of a vector and a scalar.

    @param v The vector
    @param k The scalar to multiply by
    @return The scalar multiple of the input vector and scalar
  */
  public static Vector3 scalarMultiple(Vector3 v, double k)
  {
    return new Vector3(k * v.body[0], k * v.body[1], k * v.body[2]);
  }

  /**
    Returns the term product of the two input vectors. This is
    primarliy useful with color calculations.

    @param v1, v2 The vectors to be multiplied
    @return The term by term product of the two input vectors
  */
  public static Vector3 termMultiple(Vector3 v1, Vector3 v2)
  {
    return new Vector3(v1.body[0] * v2.body[0],
                       v1.body[1] * v2.body[1],
                       v1.body[2] * v2.body[2]);
  }

  /**
    Returns the cross product of the two input vectors in
    the order given.

    @param v1, v2 The vectors to be multiplied
    @return The cross product of the two input vectors
  */
  public static Vector3 crossProduct(Vector3 v1, Vector3 v2)
  {
    return new Vector3(v1.body[1] * v2.body[2] - v1.body[2] * v2.body[1],
                       v1.body[2] * v2.body[0] - v1.body[0] * v2.body[2],
                       v1.body[0] * v2.body[1] - v1.body[1] * v2.body[0]);
  }

  /**
    Returns the dot product of the two input vectors.

    @param v1, v2 The vectors whose dot product is desired
    @return The dot product of the two vectors
  */
  public static double dotProduct(Vector3 v1, Vector3 v2)
  {
    return v1.body[0] * v2.body[0] +
           v1.body[1] * v2.body[1] +
           v1.body[2] * v2.body[2];
  }

  /**
    Writes the current vector out to the console.
    Intended as a debugging tool.
  */
  public void dump()
  {
    System.out.println("(" + body[0] + ", " + body[1] + ", " + body[2] + ")");
  }

  /**
    Writes the input vector out to the console.
    Intended as a debugging tool.

    @param v The vector to output
  */
  public static void dump(Vector3 v)
  {
    System.out.println("(" + v.body[0] + ", " + v.body[1] + ", " + v.body[2] + ")");
  }
}

