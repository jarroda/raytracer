import java.util.*;
import java.awt.*;
/**
  A class representing the standard cone. The base is a circle in the xz-plane,
  centered at the origin, and of radius 1; the cone has height 1 - so its vertex
  is 1 unit up the positive y-axis. The cone has a solid bottom.

  @version 1 July 2002
  @author Bob Crawford

*/

public class Cone extends Traceable
{
  private Color coneColor = Color.white;

  Cone()
  {
    super();
  }

  Cone(Color color)
  {
    super();
    coneColor = color;
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
    DoubleList result = new DoubleList();
    double t;
    Vector3 pt;
    Ray localRay = getLocalRay(r);

    Vector3 p = localRay.getOrigin();
    Vector3 d = localRay.getDirection();

    double a = d.body[0] * d.body[0] + d.body[2] * d.body[2] - d.body[1] * d.body[1];
    double b = 2 * (p.body[0] * d.body[0] + p.body[2] * d.body[2] + d.body[1] - p.body[1] * d.body[1]);
    double c = p.body[0] * p.body[0] + p.body[2] * p.body[2] - p.body[1] * p.body[1] - 1 + 2 * p.body[1];

    DoubleList side = Util.solveQuadraticPositive(a, b, c);
    for ( int i = 0; i < side.size(); i++ )
    {
      t = side.getD(i);
      pt = localRay.pointAt(t);
      if ( (pt.body[1] > 0) & (pt.body[1] <= 1) )
        result.insertPositiveD(t);
    }

    if ( Math.abs(d.body[1]) > Util.nearlyZero )
    {
      t = -p.body[1] / d.body[1];
      pt = localRay.pointAt(t);
      if ( pt.body[0] * pt.body[0] + pt.body[2] * pt.body[2] <= 1 )
        result.insertPositiveD(t);
    }

    return result;
  }

  public Color getColorAt(Vector3 p)
  {
    return coneColor;
  }

}



