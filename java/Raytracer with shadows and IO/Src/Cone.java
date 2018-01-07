/*  
  This class models an finite cone. The cone is centered along
  the y-axis and has radius 1. It extends from 0 to 1 along the y-axis.
 */

/**
 *
 * @author Jarrod Alexander
 */
public class Cone extends Surface{
    
    /** Creates a new instance of Cone */
    public Cone(RGBColor color) 
	 {
        this.color = color;
    }
    
    public boolean hit(Ray ray, double tMin, double tMax, double time, HitRecord record) 
	 {
        Ray r = getLocalRay(ray);
		  r = r.normalize();
		  
        double a = (r.direction.body[0] * r.direction.body[0]) + 
		                     (r.direction.body[2] * r.direction.body[2]) - 
									(r.direction.body[1] * r.direction.body[1]);
        double b = (2 * r.origin.body[0] * r.direction.body[0]) + 
		                     (2 * r.origin.body[2] * r.direction.body[2]) - 
									(2 * r.origin.body[1] * r.direction.body[1]);
        double c = (r.origin.body[0] * r.origin.body[0]) + 
		                     (r.origin.body[2] * r.origin.body[2]) - 
									(r.origin.body[1] * r.origin.body[1]);
        
        DoubleList roots = Util.solveQuadraticPositive(a, b, c);
        
        int numRoots = roots.size();
        
        if ( numRoots == 0 )
            return false;
        
        double t = roots.get(0);
        boolean outOfRange = (t < tMin) || (t > tMax);
		  
        if ( outOfRange && (numRoots == 2) ) 
		  {
            t = roots.get(1);
            outOfRange = (t < tMin) || (t > tMax);
        }
        
        if ( outOfRange )
            return false;
        else 
		  {
            record.t = t;
            record.point = r.pointAt(t);
            if ( (record.point.body[1] < 0) || (record.point.body[1] > 1) )
                return false;
            record.s = this;
            record.point = mm.image(record.point);
            return true;
        }
        
    }
    
    public Vector3 normalAt(Vector3 point) 
	 {
	     Vector3 p = mm.inverseImage(point);
        Vector3 rawNormal = new Vector3(p.body[0], 0, p.body[2]);
        Vector3 normal = mm.transformNormal(rawNormal);
        return Vector3Util.normalize(normal);
    }
    
    public boolean shadowHit(Ray ray, double tMin, double tMax, double time) 
	 {
        Ray r = getLocalRay(ray);
        HitRecord record = new HitRecord();
        
        double a = (r.direction.body[0] * r.direction.body[0]) + 
		                     (r.direction.body[2] * r.direction.body[2]) - 
									(r.direction.body[1] * r.direction.body[1]);
        double b = (2 * r.origin.body[0] * r.direction.body[0]) + 
		                     (2 * r.origin.body[2] * r.direction.body[2]) - 
									(2 * r.origin.body[1] * r.direction.body[1]);
        double c = (r.origin.body[0] * r.origin.body[0]) + 
		                     (r.origin.body[2] * r.origin.body[2]) - 
									(r.origin.body[1] * r.origin.body[1]);
        
        DoubleList roots = Util.solveQuadraticPositive(a, b, c);
        
        int numRoots = roots.size();
        
        if ( numRoots == 0 )
            return false;
        
        double t = roots.get(0);
        boolean outOfRange = (t < tMin) || (t > tMax);
		  
        if ( outOfRange && (numRoots == 2) ) 
		  {
            t = roots.get(1);
            outOfRange = (t < tMin) || (t > tMax);
        }
        
        if ( outOfRange )
            return false;
        else 
		  {
            record.t = t;
            record.point = r.pointAt(t);
            if ( (record.point.body[1] < 0) || (record.point.body[1] > 1) )
                return false;
            return true;
        }
    }
    
    public void dump() {
        System.out.println("Cone along y-axis, opens up.");
		  System.out.println("  Radius 1, extends from 0 to 1 along the y-axis.");
        System.out.println();
        System.out.print("Color: ");
        color.dump();
        System.out.println();
    }
}
