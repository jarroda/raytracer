public class TestUtilSolveQuadratic
{
  public static void main(String[] arg)
  {
    DoubleList dl = Util.solveQuadraticPositive(1, 1, -2);
	 
	 dl.dump();
  }
}