/**
  An abstract class which serves as the ancestor of
  those classes which model point and directional lights.

  @version 1 July 2002
  @author Bob Crawford

*/

public abstract class Light
{
  Vector3 color;

  abstract Vector3 getLightVector(Vector3 point);
}

