/**
  A class to represent rays.

  @version 1 June 2002
  @author Bob Crawford

*/

public class Ray
{
  private Vector3 p = new Vector3(0, 0, 0);     // Origin of the ray
  private Vector3 d = new Vector3(0, 0, 1);     // Direction of the ray

  /**
    Initializes a new ray whose origin is point1 and which
    points from point1 to point2.
  */

  Ray(Vector3 point1, Vector3 point2)
  {
    setPoints(point1, point2);
  }

  /**
    Sets the ray so that point1 is the origin and the ray
    points from point1 to point2.

    @param point1 The origin of the desired ray
    @param point2 A point on the positive part of the desired ray
  */
  public void setPoints(Vector3 point1, Vector3 point2)
  {
    p = new Vector3(point1);
    d = Vector3.Difference(point2, point1);
//    d.normalize();
  }

  /**
    Sets the ray so that its origin is point and its
    direction is dir.

    @param point The origin of the desired ray.
    @param dir The direction of the desired ray.
  */
  public void setPointDirection(Vector3 point, Vector3 dir)
  {
    p = new Vector3(point);
    dir = new Vector3(dir);
//    dir.normalize();
  }

  /**
    Returns the point on the ray corresponding to the input parameter.

    @param t The "coordinate" of the desired point
    @return The point on the current ray with the given coordinate
  */
  public Vector3 pointAt(double t)
  {
    return Vector3.Sum(p, Vector3.scalarMultiple(d, t));
  }

  /**
    Returns the origin of the current vector

    @return The origin of the current vector
  */
  public Vector3 getOrigin()
  {
    return p;
  }

  /**
    Returns the direction of the current vector

    @return The direction of the current vector
  */
  public Vector3 getDirection()
  {
    return d;
  }

  /**
    Writes the current ray out to the console.
    Intended as a debugging tool.
  */
  public void dump()
  {
    System.out.print("Origin: ");
    p.dump();
    System.out.print("Direction: ");
    d.dump();
  }

}

