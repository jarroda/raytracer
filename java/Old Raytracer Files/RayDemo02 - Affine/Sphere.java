import java.util.*;

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
}



