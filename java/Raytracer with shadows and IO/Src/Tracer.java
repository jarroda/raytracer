import java.util.*;

public class Tracer
{
  public static RGBColor traceARay(Ray r,	SurfaceList surfaces,	ArrayList<PointLight> lightList,
	                                                         Vector3 eye,	RGBColor background,	int depth)
  {
      HitRecord record = new HitRecord( );
		 
		if ( surfaces.hit(r, 0, 10000, 1, record) )
		{
		  Surface s = record.s;
		  Vector3 point = record.point;
		  
		  RGBColor surfaceColor = s.getColorAt(point);
		  RGBColor specularColor = s.getSpecularColorAt(point);
		  double shine = s.getShininessAt(point);
		  Vector3 n = s.normalAt(point);
		  Vector3 v = Vector3Util.normalize(Vector3Util.difference(eye, point));
		  
		  
		  // Take care of the global ambient contribution.
		  RGBColor color = RGBUtil.termProduct(GlobalAmbient.color, surfaceColor); 
		  
		  // Now loop through the lights for the diffuse and specular contributions.
		  for ( int count = 0; count < lightList.size( ); count++ )
		  {
		    PointLight light = lightList.get(count);
			 
			 if ( notInShadow(point, light, surfaces) )
			 {
			 // Do the diffuse contribution
			 Vector3 l = light.getLightVector(point);
			 RGBColor diffuse = RGBUtil.termProduct(light.color, surfaceColor);
			 double lDotN = Vector3Util.dotProduct(l, n);
			 if ( lDotN < 0 )
			   lDotN = 0;
			 diffuse = RGBUtil.scalarMultiple(lDotN, diffuse);
		    color = RGBUtil.sum(color, diffuse);	
			 
			 // Do the specular contribution
			 Vector3 h = Vector3Util.scalarQuotient(Vector3Util.sum(v, l), 2);
			 h = Vector3Util.normalize(h);
			 double hDotN = Vector3Util.dotProduct(h, n);
			 if ( hDotN < 0 )
			   hDotN = 0;
			 RGBColor specular = RGBUtil.termProduct(light.color, specularColor);
			 specular = RGBUtil.scalarMultiple(Math.pow(hDotN, shine), specular);
	       color = RGBUtil.sum(color, specular);		  		  
			}  //if
			} // for loop
		    color.clamp( );
		    return color;
	   }  // if
		else
		  return background;
  }  // traceARay
  
  public static boolean notInShadow(Vector3 point, PointLight light, SurfaceList surfaces)
  {
    double maxT = Vector3Util.length(Vector3Util.difference(point, light.location));
	 Vector3 l = light.getLightVector(point);
	 Ray shadowRay = new Ray(point, l);
	 HitRecord h = new HitRecord();
	 for ( int i = 0; i < surfaces.size(); i++ )
	 {
	   Surface s = surfaces.get(i);
		boolean shadowed = s.hit(shadowRay, 0.001, maxT, 0.0, h);
		if ( shadowed )
		  return false;
	 }
	 
	 return true;
  }
}