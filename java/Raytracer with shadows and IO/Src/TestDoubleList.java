public class TestDoubleList
{
  public static void main(String[] arg)
  {
    DoubleList dl = new DoubleList();
	 
	 dl.insertIfPositive(5.3);
	 dl.insertIfPositive(-0.8);
	 dl.insertIfPositive(3.33);
	 dl.insertIfPositive(8.77);
	 dl.insertIfPositive(1.2);
	 dl.insertIfPositive(0.0);
	 dl.insertIfPositive(123.45);
	 dl.insertIfPositive(99.99);
	 
	 dl.dump();
  }
}