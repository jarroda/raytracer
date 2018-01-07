import java.awt.*;

public class Tracer
{
  public static Color TraceARay(Ray r, TraceableList l)
  {
    DoubleList hits;
    Vector3 baseColor;
    Vector3 color;

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
     {
       baseColor = nearestObject.getBaseColorAt(r.pointAt(nearestHit));

       // Take care of the ambient light
       color = Vector3.termMultiple(baseColor, AmbientLight.color);
     }

     color.clamp();
     return new Color((float)color.body[0], (float)color.body[1], (float)color.body[2]);

  }
}

