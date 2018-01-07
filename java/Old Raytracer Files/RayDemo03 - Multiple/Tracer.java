import java.awt.*;

public class Tracer
{
  public static Color TraceARay(Ray r, TraceableList l)
  {
    DoubleList hits;

   double nearestHit = Util.nearlyInfinite;
   Traceable nearestObject = null;

    for ( int i = 0; i < l.size(); i++ )
    {
      hits = (l.getTraceable(i)).intersections(r);
      if ( !hits.isEmpty() )
        if ( hits.getD(0) < nearestHit )
        {
          nearestHit = hits.getD(0);
          nearestObject = l.getTraceable(i);
        }
    }
    if ( nearestObject == null )
      return Color.black;
    else
      return nearestObject.getColorAt(r.pointAt(nearestHit));
  }
}

