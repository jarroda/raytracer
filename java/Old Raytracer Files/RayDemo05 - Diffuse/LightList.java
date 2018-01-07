import java.util.*;

public class LightList extends Vector
{
  public Light getLight(int index)
  {
    return ((Light)elementAt(index));
  }
}

