import java.util.*;

public class TraceableList extends Vector
{
  public Traceable getTraceable(int index)
  {
    return ((Traceable)elementAt(index));
  }
}

