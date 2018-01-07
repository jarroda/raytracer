import java.util.*;
import java.awt.*;
/**
  A class representing the unit sphere (centered at the origin, radius 1).

  @version 1 June 2002
  @author Bob Crawford

*/

public class Sphere extends Traceable
{
  Sphere()
  {
    super();
  }

  Sphere(Vector3 base, Vector3 specular, double exponent)
  {
    super();
    baseColor = base;
    specularColor = specular;
    specularExponent = exponent;
  }

  /**
    Returns an ordered list of the intersections of
    the current object with the given ray. If the
    ray does not intersect the object, an empty list
    is returned.

    @param r The ray to intersect with the current object
    @return The ordered list of ray-object intersections;
            empty if there are none
  */
  public DoubleList intersections(Ray r)
  {
    Ray localRay = getLocalRay(r);
    return
      Util.solveQuadraticPositive(
        (localRay.getDirection()).magnitudeSquared(),
        2 * Vector3.dotProduct(localRay.getOrigin(), localRay.getDirection()),
        (localRay.getOrigin()).magnitudeSquared() - 1);
  }

  public Vector3 getNormalAt(Vector3 p)
  {
    return mm.transformNormal(mm.inverseImage(p));
  }

  public Vector3 getBaseColorAt(Vector3 p)
  {
    return baseColor;
  }

  public void dump()
  {
    System.out.println("A sphere:");
    super.dump();
  }

}



