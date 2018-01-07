import java.awt.*;
import java.awt.geom.*;

/**
  A class of graphics utility routines.

  @version 1 June 2002
  @author Bob Crawford

*/
public class GraphicsUtil
{
  /**
    Returns a 2D affine transformation which transforms coordinates
    of points in the world window into coordinates of points in
    the screen viewport. It is assumed that both x-axes point to the
    right, the y-axis of the world window points up, and the y-axis of
    the viewport points down.

    @param w A rectangle describing the world window
    @param v A rectangle describing the screen viewport
    @return The window-to-viewport transform

  */
  public static AffineTransform windowToViewport(RectangleD w, Rectangle v)
  {
    double widthRatio = (double)v.width / w.width;
    double heightRatio = (double)v.height / w.height;

    return new AffineTransform(
      widthRatio,
      0,
      0,
      -heightRatio,
      v.x - widthRatio * w.x,
      v.y + heightRatio * w.y);
  }

  /**
    Returns a 2D affine transformation which transforms coordinates
    of points in a screen viewport v into coordinates of points
    in a world window w. It is assumed that both x-axes point to the
    right, the y-axis of the world window points up, and the y-axis of
    the viewport points down.

    @param w A rectangle describing the world window
    @param v A rectangle describing the screen viewport
    @return The window-to-viewport transform

  */
  public static AffineTransform viewportToWindow(RectangleD w, Rectangle v)
  {
    double widthRatio = w.width / (double)v.width;
    double heightRatio = w.height / (double)v.height;

    return new AffineTransform(
      widthRatio,
      0,
      0,
      -heightRatio,
      w.x - widthRatio * v.x,
      w.y + heightRatio * v.y);
  }

  /**
    Simply dumps the transformation matrix (3 x 3) to the console.
    Intended as a debugging tool.

    @praram at The transform to display
  */
  public static void dumpAffineTransform2D(AffineTransform at)
  {
    double[] affineMatrix = new double[6];

    at.getMatrix(affineMatrix);
    int column0Width = Math.max(("" + affineMatrix[0]).length(),
                                ("" + affineMatrix[1]).length()) +
                                2;
    int column1Width = Math.max(("" + affineMatrix[2]).length(),
                                ("" + affineMatrix[3]).length()) +
                                2;
    int column2Width = Math.max(("" + affineMatrix[4]).length(),
                                ("" + affineMatrix[5]).length()) +
                                2;
    System.out.println();
    System.out.println(Util.padCenter("" + affineMatrix[0], ' ', column0Width) +
                       Util.padCenter("" + affineMatrix[2], ' ', column1Width) +
                       Util.padCenter("" + affineMatrix[4], ' ', column2Width));
    System.out.println();
    System.out.println(Util.padCenter("" + affineMatrix[1], ' ', column0Width) +
                       Util.padCenter("" + affineMatrix[3], ' ', column1Width) +
                       Util.padCenter("" + affineMatrix[5], ' ', column2Width));
    System.out.println();
    System.out.println(Util.padCenter("0", ' ', column0Width) +
                       Util.padCenter("0", ' ', column1Width) +
                       Util.padCenter("1", ' ', column2Width));
    System.out.println();
  }

  /**
    Plots the point with the given world endpoints.

    @param g2d A 2D graphics context to draw on
    @param w2v The window to viewport mapping
    @param x, y Coordinates of the world point
  */
  public static void drawWorldPoint2D(Graphics2D g2d,
                                      AffineTransform w2v,
                                      double x, double y)
  {
    Point2D.Double srcPt = new Point2D.Double(x, y);
    Point2D.Double dstPt = new Point2D.Double();
    int ix, iy;

    w2v.transform(srcPt, dstPt);
    ix = (int)Math.round(dstPt.x);
    iy = (int)Math.round(dstPt.y);
    g2d.drawLine(ix, iy, ix, iy);
  }

  /**
    Draws the line with the given world endpoints.

    @param g2d A 2D graphics context to draw on
    @param w2v The window to viewport mapping
    @param x1, y1 World coordinates of one endpoint
    @param x2, y2 World coordinates of the other endpoint
  */
  public static void drawWorldLine2D(Graphics2D g2d,
                                     AffineTransform w2v,
                                     double x1, double y1,
                                     double x2, double y2)
  {
    Point2D pt1 = null, pt2 = null;

    g2d.draw(
      new Line2D.Double(
        w2v.transform(new Point2D.Double(x1, y1), pt1),
        w2v.transform(new Point2D.Double(x2, y2), pt2)));
  }


}

