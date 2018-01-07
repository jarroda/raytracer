/**
  A class to provide a frame of reference, consisting
  of an origin together with an orthonormal basis - i.e.
  a complete coordinate system.
*/
public class Frame
{
  /**
    The origin of the coordinate system.
  */
  public Vector3 origin;
  /**
    The basis of the coordinate system.
  */
  public OrthonormalBasis basis;
  
  /**
    The lone constructor, which builds the
	 standard coordinate system having the
	 unit vectors in the x, y, and z directions
	 as a basis and the origin at (0, 0, 0).
  */
  public Frame()
  {
    origin = new Vector3();
	 basis = new OrthonormalBasis();
  }
  
  /**
    Dumps the information about the frame to
	 standard out. Mainly for debugging.
  */
  public void dump()
  {
    System.out.print("Origin: ");
	 origin.dump();
	 System.out.println();
	 System.out.println("Orthonormal basis:");
	 basis.dump();
  }
}
