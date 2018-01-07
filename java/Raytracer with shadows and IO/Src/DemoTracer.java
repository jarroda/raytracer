import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;

public class DemoTracer
{
  static BaseFrame bf;
  static SurfaceList surfaces;
  static ArrayList<PointLight> lightList;
  static Rectangle v;
  static RectangleD w  ;
  static double viewDistance;
  static Vector3 eye;
  static Vector3 center;
  static Vector3 up;
  
  public static void main(String[] arg)
  {
	 // Build the window to show the picture in.
	 // The size will possibly change when we read
	 // in the .ray file.
    bf = new BaseFrame("Demo Tracer", 500, 500);
	 bf.setClientSize(500, 500);
	 JPanel jp = new JPanel();
	 bf.add(jp);

    // Create the list of surfaces.
	 surfaces = new SurfaceList();
	 
	 // Create the list of lights.
	 lightList = new ArrayList<PointLight>();
	 
	 // Read in the .ray file, specified (with no 
	 // extension) as the first command line
	 // argument.
	 readRayFile(arg[0]);
	 
	 // Let's make the frame visible.
	 bf.setVisible(true);

    // Get the graphics context to plot on.
	 Graphics2D g = (Graphics2D)jp.getGraphics();
	 
    // Connect the window and viewport.
    AffineTransform v2w = GraphicsUtil.viewportToWindow(w, v);
	 
    // Set up the viewing matrix and inverse
    Matrix viewing = Viewing.viewingTransform(eye, center, up);
    Matrix inverseViewing = Viewing.inverseViewingTransform(viewing, eye);

    // Well, we will need a tracer.
	 Tracer tracer = new Tracer( );
	 
    // Variables for the super loop
    Ray ray;
    Vector3 viewingPoint;
    Vector3 worldPoint;
    Point2D.Double viewportPoint;
    Point2D.Double windowPoint = new Point2D.Double(0, 0);

	 RGBColor background = new RGBColor(0.5, 0.5, 0.5);
	 HitRecord record = new HitRecord();
	 Vector3 rayDir;
	 RGBColor pixelColor;
	 
    long startTime = System.currentTimeMillis();
	 
    for ( int y = 0; y < v.height; y++ )
      for ( int x = 0; x < v.width; x++ )
      {
        viewportPoint = new Point2D.Double(x, y);
        v2w.transform(viewportPoint, windowPoint);
        viewingPoint = new Vector3(windowPoint.x, windowPoint.y, viewDistance);
        worldPoint = inverseViewing.image(viewingPoint);
		  rayDir = Vector3Util.difference(worldPoint, eye);
        ray = new Ray(eye, rayDir);
		  ray = ray.normalize();
		  pixelColor = tracer.traceARay(ray, surfaces, lightList, eye, background, 0);
		  Util.plot(g, x, y, pixelColor);
      }
		
    long elapsedTime = System.currentTimeMillis() - startTime;
    System.out.println("Elapsed time: " + elapsedTime + " milliseconds.");

  }
  
  public static void readRayFile(String fileName)
  {
    int token;
    TokenReader tr = new TokenReader(fileName + ".ray");

    token = tr.readRayToken();
    while ( token != TokenReader.tokEndScene )
    {
      switch ( token )
      {
        case TokenReader.tokViewport :							// Note that in the data file, the 
			 v = new Rectangle(tr.readInt(), tr.readInt());	// arguments to Viewport  must
          bf.setClientSize(v.width, v.height);						// be in the order width, height.
          break;  // Viewport
			 
        case TokenReader.tokWindow :
          w = new RectangleD(tr.readDouble(), tr.readDouble(),      // Upper left corner,
                                              tr.readDouble(), tr.readDouble());     // width, height
          break;  // Window
			 
        case TokenReader.tokViewing :
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndViewing )
          {
            switch ( token )
            {
              case TokenReader.tokDistance :
                viewDistance = tr.readDouble();
                break;
              case TokenReader.tokEye :
                eye = new Vector3(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokCenter :
                center = new Vector3(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokUp :
                up = new Vector3(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in Viewing");
                System.exit(1);
                break;
            }				
            token = tr.readRayToken();
          }
          break;  // Viewing
			 
        case TokenReader.tokAmbientLight :
          GlobalAmbient.color =
            new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
          break;  // AmbientLight
			 
        case TokenReader.tokPointLight :
          PointLight light = new PointLight();
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndPointLight )
          {
            switch ( token )
            {
              case TokenReader.tokColor :
                light.color =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokLocation :
                light.location =
                  new Vector3(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in PointLight");
                System.exit(1);
                break;
            }
            token = tr.readRayToken();
          }
          lightList.add(light);
          break;  // PointLight
			 
        case TokenReader.tokSphere :
          Sphere sphere = new Sphere(new Vector3(), 1.0f, new RGBColor());
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndSphere )
          {
            switch ( token )
            {
				  case TokenReader.tokCenter :
				    sphere.center = new Vector3(tr.readDouble(), tr.readDouble(), tr.readDouble());
					 break;
				  case TokenReader.tokRadius :
				    sphere.radius = tr.readDouble();
					 break;
              case TokenReader.tokBaseColor :
                sphere.color =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokSpecularColor :
                sphere.specularColor =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShininess :
                sphere.shininess = tr.readDouble();
                break;
              case TokenReader.tokReflectivity:
                sphere.reflectivity = tr.readDouble();
                break;
              case TokenReader.tokTranslate :
                sphere.mm.translate(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokScale :
                sphere.mm.scale(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokRotate :
                sphere.mm.rotateDeg(tr.readDouble(),
                  tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokReflectXY :
                sphere.mm.reflectXY();
                break;
              case TokenReader.tokReflectXZ :
                sphere.mm.reflectXZ();
                break;
              case TokenReader.tokReflectYZ :
                sphere.mm.reflectYZ();
                break;
              case TokenReader.tokReflectOrigin :
                sphere.mm.reflectOrigin();
                break;
              case TokenReader.tokShearX :
                sphere.mm.shearX(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearY :
                sphere.mm.shearY(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearZ :
                sphere.mm.shearZ(tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in Sphere: " + token);
                System.exit(1);
                break;
            }
            token = tr.readRayToken();
          }
          surfaces.add(sphere);
          break;  // Sphere
			 
        case TokenReader.tokCone :
          Cone cone = new Cone(new RGBColor());
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndCone )
          {
            switch ( token )
            {
              case TokenReader.tokBaseColor :
                cone.color =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokSpecularColor :
                cone.specularColor =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShininess :
                cone.shininess = tr.readDouble();
                break;
              case TokenReader.tokReflectivity:
                cone.reflectivity = tr.readDouble();
                break;
              case TokenReader.tokTranslate :
                cone.mm.translate(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokScale :
                cone.mm.scale(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokRotate :
                cone.mm.rotateDeg(tr.readDouble(),
                  tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokReflectXY :
                cone.mm.reflectXY();
                break;
              case TokenReader.tokReflectXZ :
                cone.mm.reflectXZ();
                break;
              case TokenReader.tokReflectYZ :
                cone.mm.reflectYZ();
                break;
              case TokenReader.tokReflectOrigin :
                cone.mm.reflectOrigin();
                break;
              case TokenReader.tokShearX :
                cone.mm.shearX(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearY :
                cone.mm.shearY(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearZ :
                cone.mm.shearZ(tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in Cone");
                System.exit(1);
                break;
            }
            token = tr.readRayToken();
          }
          surfaces.add(cone);
          break;  // Cone
			 
         case TokenReader.tokPlane :
          Plane plane = new Plane(new RGBColor());
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndPlane )
          {
            switch ( token )
            {
              case TokenReader.tokBaseColor :
                plane.color =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokSpecularColor :
                plane.specularColor =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShininess :
                plane.shininess = tr.readDouble();
                break;
              case TokenReader.tokReflectivity:
                plane.reflectivity = tr.readDouble();
                break;
              case TokenReader.tokTranslate :
                plane.mm.translate(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokScale :
                plane.mm.scale(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokRotate :
                plane.mm.rotateDeg(tr.readDouble(),
                  tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokReflectXY :
                plane.mm.reflectXY();
                break;
              case TokenReader.tokReflectXZ :
                plane.mm.reflectXZ();
                break;
              case TokenReader.tokReflectYZ :
                plane.mm.reflectYZ();
                break;
              case TokenReader.tokReflectOrigin :
                plane.mm.reflectOrigin();
                break;
              case TokenReader.tokShearX :
                plane.mm.shearX(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearY :
                plane.mm.shearY(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearZ :
                plane.mm.shearZ(tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in Plane");
                System.exit(1);
                break;
            }
            token = tr.readRayToken();
          }
          surfaces.add(plane);
          break;  // Plane
			 
          case TokenReader.tokCheckerboard :
          Checkerboard checkerboard = new Checkerboard(new RGBColor(), new RGBColor());
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndCheckerboard )
          {
            switch ( token )
            {
              case TokenReader.tokBaseColor :
                checkerboard.color =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokSpecularColor :
                checkerboard.specularColor =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShininess :
                checkerboard.shininess = tr.readDouble();
                break;
              case TokenReader.tokReflectivity:
                checkerboard.reflectivity = tr.readDouble();
                break;
              case TokenReader.tokCheckColor :
                checkerboard.checkColor =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokTranslate :
                checkerboard.mm.translate(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokScale :
                checkerboard.mm.scale(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokRotate :
                checkerboard.mm.rotateDeg(tr.readDouble(),
                  tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokReflectXY :
                checkerboard.mm.reflectXY();
                break;
              case TokenReader.tokReflectXZ :
                checkerboard.mm.reflectXZ();
                break;
              case TokenReader.tokReflectYZ :
                checkerboard.mm.reflectYZ();
                break;
              case TokenReader.tokReflectOrigin :
                checkerboard.mm.reflectOrigin();
                break;
              case TokenReader.tokShearX :
                checkerboard.mm.shearX(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearY :
                checkerboard.mm.shearY(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearZ :
                checkerboard.mm.shearZ(tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in Checkerboard");
                System.exit(1);
                break;
            }
            token = tr.readRayToken();
          }
          surfaces.add(checkerboard);
          break;  // Checkerboard
			 
          case TokenReader.tokCylinder :
          Cylinder cylinder = new Cylinder(new RGBColor());
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndCylinder )
          {
            switch ( token )
            {
              case TokenReader.tokBaseColor :
                cylinder.color =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokSpecularColor :
                cylinder.specularColor =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShininess :
                cylinder.shininess = tr.readDouble();
                break;
              case TokenReader.tokReflectivity:
                cylinder.reflectivity = tr.readDouble();
                break;
              case TokenReader.tokTranslate :
                cylinder.mm.translate(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokScale :
                cylinder.mm.scale(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokRotate :
                cylinder.mm.rotateDeg(tr.readDouble(),
                  tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokReflectXY :
                cylinder.mm.reflectXY();
                break;
              case TokenReader.tokReflectXZ :
                cylinder.mm.reflectXZ();
                break;
              case TokenReader.tokReflectYZ :
                cylinder.mm.reflectYZ();
                break;
              case TokenReader.tokReflectOrigin :
                cylinder.mm.reflectOrigin();
                break;
              case TokenReader.tokShearX :
                cylinder.mm.shearX(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearY :
                cylinder.mm.shearY(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearZ :
                cylinder.mm.shearZ(tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in Cylinder");
                System.exit(1);
                break;
            }
            token = tr.readRayToken();
          }
          surfaces.add(cylinder);
          break;  // Cylinder
		
          case TokenReader.tokDisk :
          Disk disk = new Disk(new RGBColor());
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndDisk )
          {
            switch ( token )
            {
              case TokenReader.tokBaseColor :
                disk.color =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokSpecularColor :
                disk.specularColor =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShininess :
                disk.shininess = tr.readDouble();
                break;
              case TokenReader.tokReflectivity:
                disk.reflectivity = tr.readDouble();
                break;
              case TokenReader.tokTranslate :
                disk.mm.translate(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokScale :
                disk.mm.scale(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokRotate :
                disk.mm.rotateDeg(tr.readDouble(),
                  tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokReflectXY :
                disk.mm.reflectXY();
                break;
              case TokenReader.tokReflectXZ :
                disk.mm.reflectXZ();
                break;
              case TokenReader.tokReflectYZ :
                disk.mm.reflectYZ();
                break;
              case TokenReader.tokReflectOrigin :
                disk.mm.reflectOrigin();
                break;
              case TokenReader.tokShearX :
                disk.mm.shearX(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearY :
                disk.mm.shearY(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearZ :
                disk.mm.shearZ(tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in Disk");
                System.exit(1);
                break;
            }
            token = tr.readRayToken();
          }
          surfaces.add(disk);
          break;  // Disk

          case TokenReader.tokInfiniteCylinder :
          InfiniteCylinder infiniteCylinder = new InfiniteCylinder(new RGBColor());
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndInfiniteCylinder )
          {
            switch ( token )
            {
              case TokenReader.tokBaseColor :
                infiniteCylinder.color =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokSpecularColor :
                infiniteCylinder.specularColor =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShininess :
                infiniteCylinder.shininess = tr.readDouble();
                break;
              case TokenReader.tokReflectivity:
                infiniteCylinder.reflectivity = tr.readDouble();
                break;
              case TokenReader.tokTranslate :
                infiniteCylinder.mm.translate(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokScale :
                infiniteCylinder.mm.scale(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokRotate :
                infiniteCylinder.mm.rotateDeg(tr.readDouble(),
                  tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokReflectXY :
                infiniteCylinder.mm.reflectXY();
                break;
              case TokenReader.tokReflectXZ :
                infiniteCylinder.mm.reflectXZ();
                break;
              case TokenReader.tokReflectYZ :
                infiniteCylinder.mm.reflectYZ();
                break;
              case TokenReader.tokReflectOrigin :
                infiniteCylinder.mm.reflectOrigin();
                break;
              case TokenReader.tokShearX :
                infiniteCylinder.mm.shearX(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearY :
                infiniteCylinder.mm.shearY(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearZ :
                infiniteCylinder.mm.shearZ(tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in InfiniteCylinder");
                System.exit(1);
                break;
            }
            token = tr.readRayToken();
          }
          surfaces.add(infiniteCylinder);
          break;  // InfiniteCylinder

          case TokenReader.tokTriangle :
          Triangle triangle = new Triangle(new Vector3(0, 0, 0), new Vector3(1, 0, 0), new Vector3(0, 1, 0), 
			                                                      new RGBColor());
          token = tr.readRayToken();
          while ( token != TokenReader.tokEndTriangle )
          {
            switch ( token )
            {
				  case TokenReader.tokVertexA :
				    triangle.p0 = new Vector3(tr.readDouble(), tr.readDouble(), tr.readDouble());
				  case TokenReader.tokVertexB :
				    triangle.p1 = new Vector3(tr.readDouble(), tr.readDouble(), tr.readDouble());
				  case TokenReader.tokVertexC :
				    triangle.p2 = new Vector3(tr.readDouble(), tr.readDouble(), tr.readDouble());
              case TokenReader.tokBaseColor :
                triangle.color =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokSpecularColor :
                triangle.specularColor =
                  new RGBColor(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShininess :
                triangle.shininess = tr.readDouble();
                break;
              case TokenReader.tokReflectivity:
                triangle.reflectivity = tr.readDouble();
                break;
              case TokenReader.tokTranslate :
                triangle.mm.translate(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokScale :
                triangle.mm.scale(tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokRotate :
                triangle.mm.rotateDeg(tr.readDouble(),
                  tr.readDouble(), tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokReflectXY :
                triangle.mm.reflectXY();
                break;
              case TokenReader.tokReflectXZ :
                triangle.mm.reflectXZ();
                break;
              case TokenReader.tokReflectYZ :
                triangle.mm.reflectYZ();
                break;
              case TokenReader.tokReflectOrigin :
                triangle.mm.reflectOrigin();
                break;
              case TokenReader.tokShearX :
                triangle.mm.shearX(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearY :
                triangle.mm.shearY(tr.readDouble(), tr.readDouble());
                break;
              case TokenReader.tokShearZ :
                triangle.mm.shearZ(tr.readDouble(), tr.readDouble());
                break;
              default :
                System.out.println("Bad token in Triangle");
                System.exit(1);
                break;
            }
            token = tr.readRayToken();
          }
          surfaces.add(triangle);
          break;  // Triangle
		 
      }
		
      token = tr.readRayToken();
    }
  }
}
