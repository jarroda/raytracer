public class Triangle extends Surface
{
  public Vector3 p0, p1, p2;
  
  public Triangle(Vector3 a, Vector3 b, Vector3 c, RGBColor color)
  {
    p0 = a;
	 p1 = b;
	 p2 = c;
	 this.color = color;
  }
  
  public boolean hit(Ray r, double tMin, double tMax, double time, HitRecord record)
  {
    double t;
    double A = p0.body[0] - p1.body[0];
    double B = p0.body[1] - p1.body[1];
    double C = p0.body[2] - p1.body[2];

    double D = p0.body[0] - p2.body[0];
    double E = p0.body[1] - p2.body[1];
    double F = p0.body[2] - p2.body[2];

    double G = r.direction.body[0];
    double H = r.direction.body[1];
    double I = r.direction.body[2];

    double J = p0.body[0] - r.origin.body[0];
    double K = p0.body[1] - r.origin.body[1];
    double L = p0.body[2] - r.origin.body[2];

    double EIHF = E * I - H * F;
    double GFDI = G * F - D * I;
    double DHEG = D * H - E * G;

    double denom = A * EIHF + B * GFDI + C * DHEG;

    double beta = (J * EIHF + K * GFDI + L * DHEG) / denom;

    if ( (beta <= 0.0) || (beta >= 1)  )
      return false;

    double AKJB = A * K - J * B;
    double JCAL = J * C - A * L;
    double BLKC = B * L - K * C;

    double gamma = (I * AKJB + H * JCAL + G * BLKC) / denom;
	 
    if ( (gamma <= 0.0) || (beta + gamma >= 1.0)  )
	   return false;

    t = -(F * AKJB + E * JCAL + D * BLKC) / denom;
  
    if (t >= tMin && t <= tMax)
    {
      record.t = t;
		record.point = mm.image(r.pointAt(t));
		record.s = this;
		       
      return true;
   }
	else
     return false;
  }
  
  public boolean shadowHit(Ray r, double tMin, double tMax, double time)
  {
    return false;
  }
  
    public Vector3 normalAt(Vector3 point)
  {
	 return Vector3Util.polyNormal(p0, p1, p2);
  }
  

  public void dump()
  {
  }      
}