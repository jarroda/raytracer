import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;

public class DrawingPanel extends JPanel
{
  DrawingPanel()
  {
    super(false);
    setBackground(Color.green);
  }

  public void paintComponent(Graphics g)
  {
    // Always be nice to your parents.
    super.paintComponent(g);

    // The viewport-to-window transformation

    // Use the whole client area as the viewport -
    // get it from the bounds of the panel.
    Rectangle v = null;
    v = getBounds(v);
    // Set the world window.
    RectangleD w = new RectangleD(-2.0, 1.5, 4.0, 3.0);
    // Connect the two.
    AffineTransform v2w = GraphicsUtil.viewportToWindow(w, v);

    // Set up the viewing matrix and inverse
    double viewDistance = 5;
    Vector3 eye = new Vector3(0, 0, 5);
    Vector3 center = new Vector3(0, 0, 0);
    Vector3 up = new Vector3(0, 1, 0);
    Matrix viewing = Viewing.viewingTransform(eye, center, up);
    Matrix inverseViewing = Viewing.inverseViewingTransform(viewing, eye);

    // Now something to look at
    Sphere sphere = new Sphere();
    sphere.mm.rotateDeg(45, 0, 0, 1);
    sphere.mm.scale(0.5, 1.5, 1.0);

    // backgroundColor
    Color backgroundColor = Color.blue;

    // Variables for the super loop
    Ray ray;
    Vector3 viewingPoint;
    Vector3 worldPoint;
    Point2D.Double viewportPoint;
    Point2D.Double windowPoint = new Point2D.Double(0, 0);
    DoubleList hits;

    long startTime = System.currentTimeMillis();
    for ( int y = 0; y < v.height; y++ )
      for ( int x = 0; x < v.width; x++ )
      {
        viewportPoint = new Point2D.Double(x, y);
        v2w.transform(viewportPoint, windowPoint);
        viewingPoint = new Vector3(windowPoint.x, windowPoint.y, viewDistance);
        worldPoint = inverseViewing.image(viewingPoint);
        ray = new Ray(eye, worldPoint);
        hits = sphere.intersections(ray);
        if ( hits.isEmpty() )
          g.setColor(backgroundColor);
        else
          g.setColor(Color.yellow);
        g.drawLine(x, y, x, y);
      }
    long elapsedTime = System.currentTimeMillis() - startTime;
    System.out.println("Elapsed time: " + elapsedTime + " milliseconds.");
  }
}
