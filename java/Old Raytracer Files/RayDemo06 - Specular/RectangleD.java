/**
  A class supporting rectangles with double values.

  @version 1 June 2002
  @author Bob Crawford

*/

public class RectangleD
{
  double x, y;
  double width, height;

  /**
    Constructs a rectangle with the given upper left corner,
    width, and height.

    @param x, y The coordinates of the upper left corner
    @param width The width of the rectangle
    @param height The height of the rectangle
  */
  RectangleD(double x, double y, double width, double height)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
    Prints the rectangle location and dimensions to the console.
    Primarily for debugging.
  */
  public void dump()
  {
    System.out.println("x = " + x);
    System.out.println("y = " + y);
    System.out.println("width = " + width);
    System.out.println("height = " + height);
  }

}



