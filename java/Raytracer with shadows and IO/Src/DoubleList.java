import java.util.*;

/**
  A class modeling a list of doubles.

  @version 2 September 2006
  @author Bob Crawford

*/

public class DoubleList extends ArrayList<Double>
{
  /**
   Inserts the given double into the list in 
	increasing order if it is positive. Otherwise
	the list is unaltered.
	
	@param d The double to possibly be inserted.
  */
  public void insertIfPositive(double d)
  {
    if ( d <= 0 )
      return;
    add(d);
    for ( int i = size() - 2; i >= 0; i-- )
      if ( get(i) > d )
        set(i + 1, get(i));
      else
        {
          set(i + 1, d);
          return;
        }
    set(0, d);
  }

  /**
   Prints the list out to standard out, one
	element per line. Mainly for debugging.
  */
  public void dump()
  {
    for ( int i = 0; i < size(); i++ )
      System.out.println(get(i));
  }
}


