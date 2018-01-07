public class TestOrthonormalBasis
{
  public static void main(String[] arg)
  {
    OrthonormalBasis onb = new OrthonormalBasis();
	 Vector3 u = new Vector3(1, 2, 3);
	 Vector3 v = new Vector3(-2, 4, 5);
	 Vector3 w = new Vector3(7, 4, 2);
	 
	 onb.setFromUV(u, v);
	 onb.dump();
	 System.out.println();
	 onb.setFromVU(v, u);
	 onb.dump();
	 System.out.println();
	 onb.setFromW(w);
	 onb.dump();
	 System.out.println();
	 Vector3 w1 = Vector3Util.normalize(w);
	 Vector3 w2 = Vector3Util.normalize(onb.getW());
	 w1.dump();
	 System.out.println();
	 w2.dump();
	 System.out.println();
	 System.out.println(Vector3Util.dotProduct(onb.getU(), onb.getV()));
	 System.out.println(Vector3Util.dotProduct(onb.getU(), onb.getW()));
	 System.out.println(Vector3Util.dotProduct(onb.getV(), onb.getW()));
	 System.out.println();
	 Vector3 w2uvw = onb.xyzToUVW(w);
	 w2uvw.dump();
	 System.out.println();
	 Vector3 wOriginal = onb.uvwToXYZ(w2uvw);
	 wOriginal.dump();
  }
}