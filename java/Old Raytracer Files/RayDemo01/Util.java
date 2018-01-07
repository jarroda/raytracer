import java.util.*;
import java.awt.*;

/**
  A class of general purpose utility routines.

  @version 1 June 2002
  @author Bob Crawford

*/
public class Util
{
  public static double nearlyZero = 0.5E-10;
  public static double nearlyInfinite = 0.5E10;

  /**
    Dumps the given rectangle to the console.
    Primarily for debugging.

    @param r The rectangle to be dumped
  */
  public static void dumpRectangle(Rectangle r)
  {
    System.out.println("x = " + r.x);
    System.out.println("y = " + r.y);
    System.out.println("width = " + r.width);
    System.out.println("height = " + r.height);
  }

  /**
    Returns a string consisting of n copies of c.

    @param c The character to copy
    @param n The number of times to copy c
    @return A string containing n copies of c
  */
  public static String charCopy(char c, int n)
  {
    StringBuffer sb = new StringBuffer("");
    for ( int i = 0; i < n; i++ )
      sb.append(c);
    return ( sb.toString() );
  }

  /**
    Returns stringToPad padded on the left with copies of padChar to a
    total length of fieldSize. Note that if the length of stringToPad is
    greater than fieldSize, stringToPad is returned.

    @param stringToPad The string to pad
    @param padChar The character to pad with
    @param fieldSize The size to pad to
    @return The padded string
  */
  public static String padLeft(String stringToPad, char padChar, int fieldSize)
  {
    return charCopy(padChar, fieldSize - stringToPad.length()) + stringToPad;
  }

  /**
    Returns stringToPad padded on the right with copies of padChar to a
    total length of fieldSize. Note that if the length of stringToPad is
    greater than fieldSize, stringToPad is returned.

    @param stringToPad The string to pad
    @param padChar The character to pad with
    @param fieldSize The size to pad to
    @return The padded string
  */
  public static String padRight(String stringToPad, char padChar, int fieldSize)
  {
    return stringToPad + charCopy(padChar, fieldSize - stringToPad.length());
  }

  /**
    Returns stringToPad padded on both sides with copies of padChar so that
    it is centered in a field of length of fieldSize. Note that if the length
    of stringToPad is greater than fieldSize, stringToPad is returned.

    @param stringToPad The string to pad
    @param padChar The character to pad with
    @param fieldSize The size to pad to
    @return The padded string
  */
  public static String padCenter(String stringToPad, char padChar, int fieldSize)
  {
    int neededPadding = fieldSize - stringToPad.length();
    int leftPadding = neededPadding / 2;
    int rightPadding = neededPadding - leftPadding;

    return charCopy(padChar, leftPadding) +
           stringToPad +
           charCopy(padChar, rightPadding);
  }

  /**
    Returns a list of doubles containing the solutions to the
    quadratic equation ax^2 + bx + c = 0. Returns an empty if there
    are no real solutions, a list of size one if there is one solution,
    and a list of size two if there are two solutions. The solutions
    are in order, smallest to largest.

    @param a, b, c Coefficients of the quadratic to be solved
    @return A list of doubles containing the solutions
  */
  public static DoubleList solveQuadratic(double a, double b, double c)
  {
    DoubleList v = new DoubleList();

    if ( Math.abs(a) < nearlyZero )            // Linear case
    {
      if ( Math.abs(b) < nearlyZero )          // No solutions
        return v;
      else
      {                                        // One linear solution
        double x1 = -c / b;
        v.addD(x1);
        return v;
      }
    }
    else
    {                                          // The quadratic case
      double d = b * b - 4 * a * c;            // d = discriminant
      if ( d < 0 )                             // Solutions are complex
        return v;
      else if ( d < nearlyZero)
           {
             double x1 = -b / (2 * a);         // One double root
             v.addD(x1);
             return v;
           }
           else
           {
             d = Math.sqrt(d);                 // Two real roots
             double x1 = (-b + d) / (2 * a);
             double x2 = (-b - d) / (2 * a);
             if ( x1 > x2 )
             {
                double t = x1;
                x1 = x2;
                x2 = t;
             }
             v.addD(x1);
             v.addD(x2);
             return v;
           }
    }
  }

  /**
    Returns a list of doubles containing the positive solutions to the
    quadratic equation ax^2 + bx + c = 0. Returns an empty if there
    are no positive real solutions, a list of size one if there is one
    positive solution, and a list of size two if there are two solutions.
    The solutions are in order, smallest to largest.

    @param a, b, c Coefficients of the quadratic to be solved
    @return A list of doubles containing the positive solutions
  */
  public static DoubleList solveQuadraticPositive(double a, double b, double c)
  {
    DoubleList v = new DoubleList();

    if ( Math.abs(a) < nearlyZero )            // Linear case
    {
      if ( Math.abs(b) < nearlyZero )          // No solutions
        return v;
      else
      {                                        // One linear solution
        double x1 = -c / b;
        v.insertPositiveD(x1);
        return v;
      }
    }
    else
    {                                          // The quadratic case
      double d = b * b - 4 * a * c;            // d = discriminant
      if ( d < 0 )                             // Solutions are complex
        return v;
      else if ( d < nearlyZero)
           {
             double x1 = -b / (2 * a);         // One double root
             v.insertPositiveD(x1);
             return v;
           }
           else
           {
             d = Math.sqrt(d);                 // Two real roots
             double x1 = (-b + d) / (2 * a);
             double x2 = (-b - d) / (2 * a);
             if ( x1 > x2 )
             {
                double t = x1;
                x1 = x2;
                x2 = t;
             }
             v.insertPositiveD(x1);
             v.insertPositiveD(x2);
             return v;
           }
    }
  }

}


