import java.util.*;
import java.awt.*;

/**
  An abstract class which serves as the ancestor of
  those classes which can be traced by the ray tracer.

  @version 1 June 2002
  @author Bob Crawford

*/

public abstract class Traceable
{
  String name = "";
  MatrixPair mm = new MatrixPair();  // Affine transformation for the object -
                                     // the modeling matrix.

  /**
    Converts a given world ray into the local coordinate system
    via the inverse of the modeling matrix.

    @param r The world ray
    @return The given ray expressed in the local coordinate system
  */
  public Ray getLocalRay(Ray r)
  {
    Vector3 worldOrigin = r.getOrigin();
    Vector3 worldPoint = r.pointAt(1);
    Vector3 localOrigin = mm.Inv.image(worldOrigin);
    Vector3 localPoint = mm.Inv.image(worldPoint);

    return new Ray(localOrigin, localPoint);
  }

  /**
    Returns an ordered list of the intersections of
    the current object with the given ray. If the
    ray does not intersect the object, null is returned.

    @param r The ray to intersect with the current object
    @return The ordered list of ray-object intersections;
            null if there are none
  */
  public abstract DoubleList intersections(Ray r);

  public abstract Color getColorAt(Vector3 p);

  public void dump()
  {
    System.out.println("Name: " + name);
  }
}


