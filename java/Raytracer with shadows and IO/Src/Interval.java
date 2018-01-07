/**
  A class to model closed intervals of
  real numbers [a, b] where a <= b. Methods
  are provided to set and get the boundaries,
  to determine if a real number is contained
  in the interval, to intersect two intervals,
  and to print the interval information to
  standard out.
  
  @version 1 September 2006
  @author Bob Crawford
*/
public class Interval
{
  private double left, right;
  
  /**
    Creates an interval with the given
	 left and right boundaries. If the 
	 boundaries are out of order, they
	 are swapped.
	 
	 @param l The left boundary of the interval.
	 @param r The right boundary of the interval.
  */
  public Interval(double l, double r)
  {
    if ( r <= l )
	 {
	   left = l;
		right = r;
	 }
	 else
	 {
	   left = r;
		right = l;
	 }
  }
  
  /**
    Creates a interval from the given
	 interval. Essentially a clone.
	 
	 @param i The interval to be copied.
  */
  public Interval(Interval i)
  {
    left = i.left;
	 right = i.right;
  }
  
  /**
    Sets the interval to the given left and
	 right boundaries. If the boundaries are
	 out of order, they are flipped.
	 
	 @param l The new left boundary.
	 @param r The new right boundary.
  */
  public void set(double l, double r)
  {
    if ( r <= l )
	 {
	   left = l;
		right = r;
	 }
	 else
	 {
	   left = r;
		right = l;
	 }
  }	 
  
  /**
    Sets a new left boundary for the interval.
	 If the supplied boundary is too large to
	 be the left boundary, it becomes the new
	 right boundary.
	 
	 @param l The new left boundary.
  */
  public void setLeft(double l)
  {
    if ( l <= right )
	   left = l;
	 else
	 {
	   left = right;
		right = l;
	 }
  }
  
  /**
    Sets a new right boundary for the interval.
	 If the supplied boundary is too small to
	 be the right boundary, it becomes the new
	 left boundary.
	 
	 @param r The new right boundary.
  */
  public void setRight(double r)
  {
    if ( r >= right )
	   right = r;
	 else
	 {
	   right = left;
		left = r;
	 }
  }
  
  /**
    Returns the left boundary of the interval.
	 
	 @return The left boundary of the interval.
  */
  public double getLeft()
  {
    return left;
  }
  
  /**
    Returns the right boundary of the interval.
	 
	 @return The right boundary of the interval.
  */
  public double getRight()
  {
    return right;
  }
  
  /**
    Determines if the given value is contained
	 in the interval.
	 
	 @param t The value to be tested.
	 @return true if the value is in the interval;
	         false if it is not.
  */
  public boolean contains(double t)
  {
    return (left <= t) && (t <= right);
  }
  
  /**
    Returns the intersection of the two given
	 intervals. If the intersection is empty,
	 null is returned.
	 
	 @param i The first interval.
	 @param j The second interval.
	 @return The intersection of the given
	         intervals; null if their
				intersection is empty.
  */
  public Interval intersection(Interval i, Interval j)
  {
    if ( i.contains(j.getLeft()) )
	   return new Interval(j.getLeft(), i.getRight());
	 else if ( i.contains(j.getRight()) )
	   return new Interval(i.getLeft(), j.getRight());
	 else
	   return null; 
  }
  /**
    Prints the interval information to standard out.
	 Mainly for debugging.
  */
  public void dump()
  {
    System.out.print("[" + left + ", " + right + "]");
  }
}