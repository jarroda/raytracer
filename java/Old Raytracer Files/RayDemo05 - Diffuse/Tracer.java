import java.awt.*;

public class Tracer
{
  public static Color TraceARay(Ray r, TraceableList l, LightList ll, Vector3 eye)
  {
    DoubleList hits;
    Vector3 baseColor;
    Vector3 color;
    Vector3 normal;
    Vector3 lightVector;
    double lDotN;
    Vector3 diffuseContribution;

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
      normal = nearestObject.getNormalAt(r.pointAt(nearestHit));
      baseColor = nearestObject.getBaseColorAt(r.pointAt(nearestHit));
      // Take care of the ambient light
      color = Vector3.termMultiple(baseColor, AmbientLight.color);

      // We run through the light list to compute the diffuse contribution
      for ( int i = 0; i < ll.size(); i++ )
      {
        lightVector = ll.getLight(i).getLightVector(r.pointAt(nearestHit));
        lDotN = Vector3.dotProduct(lightVector, normal);
        if ( lDotN > 0 )
        {
          // Take care of the diffuse light contribution
          diffuseContribution =
            Vector3.scalarMultiple(Vector3.termMultiple(baseColor, ll.getLight(i).color), lDotN);
          color = Vector3.sum(color, diffuseContribution);

          // Take care of the specular light contribution
        }
      }

      color.clamp();
      return new Color((float)color.body[0], (float)color.body[1], (float)color.body[2]);
    }
  }
}

