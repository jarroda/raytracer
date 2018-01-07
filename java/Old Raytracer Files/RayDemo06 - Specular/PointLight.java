/**
  An class which models point lights.

  @version 1 July 2002
  @author Bob Crawford

*/

public class PointLight extends Light
{
  Vector3 location;

  Vector3 getLightVector(Vector3 point)
  {
    return (Vector3.difference(location, point)).makeNormalized();
  }
}


