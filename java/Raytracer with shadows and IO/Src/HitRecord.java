public class HitRecord
{
  public double t;
  public Vector3 point;
  public Surface s;
  
  public HitRecord()
  {
    t = 0.0;
	 point = new Vector3(0, 0, 0);
	 s = null;
  }
  
  public HitRecord(double t, Vector3 point, Surface s)
  {
    this.t = t;
	 this.point = point;
	 this.s = s;
  }
  
  public void dump()
  {
    System.out.println("t: " + t);
	 System.out.print("Point: ");
	 point.dump();
	 System.out.println();
	 if ( s == null )
	   System.out.println("Surface is null.");
	 else
	 {
	   System.out.println("Surface:");
		s.dump();
	 }
  }
}