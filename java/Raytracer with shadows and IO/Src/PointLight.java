public class PointLight
{
  public Vector3 location = new Vector3( );
  public RGBColor color = new RGBColor( );
  
  public Vector3 getLightVector(Vector3 point)
  {
    return Vector3Util.normalize(Vector3Util.difference(location, point));
  }
}