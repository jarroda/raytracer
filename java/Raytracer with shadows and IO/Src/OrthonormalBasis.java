/**
  This class models an orthonormal basis - three mutually
  perpendicular unit vectors. Methods are provided to set
  the basis from one or two of the basis vectors, to get
  the individual basis vectors, to translate back and 
  forth between the standard xyz basis and an arbitrary
  basis, and to print the basis vectors to standard out.
*/
public class OrthonormalBasis
{
  private Vector3 u, v, w;
  
  /**
    The lone constructor creates the standard
	 xyz basis.
  */
  public OrthonormalBasis()
  {
    u = Vector3Util.getUnitX();
	 v = Vector3Util.getUnitY();
	 w = Vector3Util.getUnitZ();
  }
  
  /**
    Sets an orthonormal basis given vectors
	 a and b indicating the u and v directions. 
	 u lies in the direction of a and v lies in
	 the plane of a and b.
	 
	 
	 @param a The u direction.
	 @param b With a, defines a plane that 
	          contains v.
  */  
  public void setFromUV(Vector3 a, Vector3 b)
  {
    u = Vector3Util.normalize(a);
	 w = Vector3Util.normalize(Vector3Util.crossProduct(a, b));
	 v = Vector3Util.crossProduct(w, u);
  }
  
  /**
    Sets an orthonormal basis given vectors
	 a and b indicating the v and u directions. 
	 v lies in the direction of a and u lies in
	 the plane of a and b.
	 
	 
	 @param a The v direction.
	 @param b With a, defines a plane that 
	          contains u.
  */
  public void setFromVU(Vector3 a, Vector3 b)
  {
    v = Vector3Util.normalize(a);
	 w = Vector3Util.normalize(Vector3Util.crossProduct(b, a));
	 u = Vector3Util.crossProduct(v, w);
  }
  
  /**
    Sets an orthonormal basis given vectors
	 a and b indicating the v and w directions. 
	 v lies in the direction of a and w lies in
	 the plane of a and b.
	 
	 
	 @param a The v direction.
	 @param b With a, defines a plane that 
	          contains w.
  */
  public void setFromVW(Vector3 a, Vector3 b)
  {
    v = Vector3Util.normalize(a);
	 u = Vector3Util.normalize(Vector3Util.crossProduct(a, b));
	 w = Vector3Util.crossProduct(u, v);
  }
  
  /**
    Sets an orthonormal basis given vectors
	 a and b indicating the w and v directions. 
	 w lies in the direction of a and v lies in
	 the plane of a and b.
	 
	 
	 @param a The w direction.
	 @param b With a, defines a plane that 
	          contains v.
  */
  public void setFromWV(Vector3 a, Vector3 b)
  {
    w = Vector3Util.normalize(a);
	 u = Vector3Util.normalize(Vector3Util.crossProduct(b, a));
	 v = Vector3Util.crossProduct(w, u);
  }
  
  /**
    Sets an orthonormal basis given vectors
	 a and b indicating the u and w directions. 
	 u lies in the direction of a and w lies in
	 the plane of a and b.
	 
	 
	 @param a The u direction.
	 @param b With a, defines a plane that 
	          contains w.
  */
  public void setFromUW(Vector3 a, Vector3 b)
  {
    u = Vector3Util.normalize(a);
	 v = Vector3Util.normalize(Vector3Util.crossProduct(b, a));
	 w = Vector3Util.crossProduct(u, v);
  }
  
  /**
    Sets an orthonormal basis given vectors
	 a and b indicating the w and u directions. 
	 v lies in the direction of a and w lies in
	 the plane of a and b.
	 
	 
	 @param a The w direction.
	 @param b With a, defines a plane that 
	          contains u.
  */
  public void setFromWU(Vector3 a, Vector3 b)
  {
    w = Vector3Util.normalize(a);
	 v = Vector3Util.normalize(Vector3Util.crossProduct(a, b));
	 u = Vector3Util.crossProduct(v, w);
  }
  
  /**
    Sets the basis so that u has the 
	 specified direction.
	 
	 @param a The desired u direction.
  */
  public void setFromU(Vector3 a)
  {
    u = Vector3Util.normalize(a);
	 v = Vector3Util.perp(u);
	 w = Vector3Util.crossProduct(u, v);	
  }
  
  /**
    Sets the basis so that v has the 
	 specified direction.
	 
	 @param a The desired v direction.
  */
  public void setFromV(Vector3 a)
  {
    v = Vector3Util.normalize(a);
	 w = Vector3Util.perp(v);
	 u = Vector3Util.crossProduct(v, w);	
  }
  
  /**
    Sets the basis so that w has the 
	 specified direction.
	 
	 @param a The desired w direction.
  */
  public void setFromW(Vector3 a)
  {
    w = Vector3Util.normalize(a);
    v = Vector3Util.perp(w);		
	 u = Vector3Util.crossProduct(v, w);	
  }

  /**
    Returns the u vector from the basis.
	 
	 @return The u vector from the basis.
  */
  public Vector3 getU()
  {
    return u;
  }
  
  /**
    Returns the v vector from the basis.
	 
	 @return The v vector from the basis.
  */
  public Vector3 getV()
  {
    return v;
  }
  
  /**
    Returns the w vector from the basis.
	 
	 @return The w vector from the basis.
  */
  public Vector3 getW()
  {
    return w;
  }
  
  /**
    Converts the given vector from xyz coordinates
	 to the uvw basis.
	 
	 @param a The xyz coordinates of a vector.
	 @return The same vector as the argument,
	         expressed in the uvw system.
  */
  public Vector3 xyzToUVW(Vector3 a)
  {
    double au = Vector3Util.dotProduct(a, u);
	 double av = Vector3Util.dotProduct(a, v);
	 double aw = Vector3Util.dotProduct(a, w);
	 
	 return new Vector3(au, av, aw);
  }
  
  /**
    Converts the given vector from uvw coordinates
	 to the standard xyz basis.
	 
	 @param a The uvw coordinates of a vector.
	 @return The same vector as the argument,
	         expressed in the standard xyz system.
  */
  public Vector3 uvwToXYZ(Vector3 auvw)
  {
    Vector3 v0 = Vector3Util.scalarMultiple(auvw.body[0], u);
    Vector3 v1 = Vector3Util.scalarMultiple(auvw.body[1], v);
    Vector3 v2 = Vector3Util.scalarMultiple(auvw.body[2], w);
	 Vector3 a = Vector3Util.sum(v0, Vector3Util.sum(v1, v2));
	 
	 return a;
  }
  
  /**
    Prints the orthonormal basis to standard out.
	 Primarily for debugging.
  */
  public void dump()
  {
    System.out.print("u: ");
	 u.dump();
	 System.out.println();
    System.out.print("v: ");
	 v.dump();
	 System.out.println();
    System.out.print("w: ");
	 w.dump();
	 System.out.println();
  }
}