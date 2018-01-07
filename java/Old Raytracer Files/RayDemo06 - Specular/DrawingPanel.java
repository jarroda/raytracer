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

    // Set up the lights
    AmbientLight.color = new Vector3(0.2, 0.2, 0.2);
    LightList Lights = new LightList();
    PointLight light1 = new PointLight();
    light1.color = new Vector3(1, 1, 1);
    light1.location = new Vector3(5, 3, 5);
    Lights.add(light1);

    // Now something to look at
    TraceableList Objects = new TraceableList();
    Sphere sphere1 = new Sphere(new Vector3(0.8, 0.8, 0), new Vector3(1, 1, 1), 150);
    sphere1.name = "Yellow sphere";
    sphere1.mm.translate(-0.75, 0, 0);
    Objects.add(sphere1);
    Sphere sphere2 = new Sphere(new Vector3(0.8, 0, 0), new Vector3(1, 1, 1), 150);
    sphere2.name = "Red sphere";
    sphere2.mm.translate(0.75, 0, 0);
    Objects.add(sphere2);


    // backgroundColor
    Color backgroundColor = Color.blue;

    // Variables for the super loop
    Ray ray;
    Vector3 viewingPoint;
    Vector3 worldPoint;
    Point2D.Double viewportPoint;
    Point2D.Double windowPoint = new Point2D.Double(0, 0);
    DoubleList hits;
    Color color;

    long startTime = System.currentTimeMillis();
    for ( int y = 0; y < v.height; y++ )
      for ( int x = 0; x < v.width; x++ )
      {
        viewportPoint = new Point2D.Double(x, y);
        v2w.transform(viewportPoint, windowPoint);
        viewingPoint = new Vector3(windowPoint.x, windowPoint.y, viewDistance);
        worldPoint = viewing.image(viewingPoint);
        ray = new Ray(eye, worldPoint);
        color = Tracer.TraceARay(ray, Objects, Lights, eye);
        g.setColor(color);
        g.drawLine(x, y, x, y);
      }
    long elapsedTime = System.currentTimeMillis() - startTime;
    System.out.println("Elapsed time: " + elapsedTime + " milliseconds.");
  }
}
