import java.util.*;

/**
  A class modeling a list of doubles.

  @version 1 June 2002
  @author Bob Crawford

*/

public class DoubleList extends Vector
{
  public void addD(double d)
  {
    add(new Double(d));
  }

  public void insertPositiveD(double d)
  {
    if ( d <= 0 )
      return;
    for ( int i = size() - 1; i >= 0; i-- )
      if ( getD(i) > d )
        add(i + 1, get(i));
      else
        {
          add(i + 1, new Double(d));
          return;
        }
    addD(d);
  }

  public double getD(int index)
  {
    return ((Double)elementAt(index)).doubleValue();
  }
}


