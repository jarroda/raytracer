import java.util.*;

public class SurfaceList extends ArrayList<Surface>
{
  public boolean hit(Ray r, double tMin, double tMax, double time, HitRecord record)
  {
    r.normalize();
	 
    if ( isEmpty() )
	   return false;
		
	 boolean foundAHit = false;
	 double t = 0.0;
	 double d2 = Util.nearlyInfinite, newD2;
	 Vector3 diff;
    int spot = -1;
	 	 
	 for ( int i = 0; i < size(); i++ )
	 {
	   boolean result = get(i).hit(r, tMin, tMax, time, record);
		if ( result )
		{
		  if ( !foundAHit )
		  {
		    foundAHit = true;
			 t = record.t;
			 spot = i;
			 diff = Vector3Util.difference(r.origin, record.point);
			 d2 = Vector3Util.lengthSquared(diff);
		  }
		  else
		  {
		    diff = Vector3Util.difference(r.origin, record.point);
			 newD2 = Vector3Util.lengthSquared(diff);
		    if ( newD2 < d2 )
			 {
			   d2 = newD2;
			   t = record.t;
				spot = i;
			 }
		  }
		}  
	 }
	 
	 if ( foundAHit )
	   get(spot).hit(r, tMin, tMax, time, record);
		
	 return foundAHit;
  }
}