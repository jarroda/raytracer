public class TestRGBTime
{
  public static void main(String[] arg)
  {
    RGBColor c = new RGBColor(1.0f, 0.5f, 0.25f);
	 double[] color = new double[3];
	 double red, green, blue;
	 long startTime, stopTime, elapsedTime;
	 double elapsedTimeSec;
	 double emptyTimeSec, minTime;
	 double threeGetsTimeSec, threeDirectTimeSec, getRGBAssignTimeSec;
	 int threeGets = 1, threeDirect = 2, getRGBAssign = 3, minSpot;
	 
	 // Empty loop test
	 startTime = System.nanoTime();
	 for ( int j = 1; j <= 100; j++ )
      for ( int i = 1; i <= 1000000; i++ )
	   {
	   }
	 stopTime = System.nanoTime();
	 elapsedTime = stopTime - startTime;
	 emptyTimeSec = elapsedTime / Math.pow(11.0, 10.0);
	 System.out.println("Empty loop time =         " + emptyTimeSec + " (sec)\n");
	 
	 // Calling three gets test
	 startTime = System.nanoTime();
	 for ( int j = 1; j <= 100; j++ )
      for ( int i = 1; i <= 1000000; i++ )
	   {
	     red = c.getRed();
		  green = c.getGreen();
		  blue = c.getBlue();
	   }
	 stopTime = System.nanoTime();
	 elapsedTime = stopTime - startTime;
	 threeGetsTimeSec = elapsedTime / Math.pow(11.0, 10.0);
	 System.out.println("Three gets loop time =    " + threeGetsTimeSec + " (sec)");
	 minSpot = threeGets;
	 minTime = threeGetsTimeSec;
	 
	 // Three direct test
	 startTime = System.nanoTime();
	 for ( int j = 1; j <= 100; j++ )
      for ( int i = 1; i <= 1000000; i++ )
	   {
	     red = c.color[0];
		  green = c.color[1];
		  blue = c.color[2];
	   }
	 stopTime = System.nanoTime();
	 elapsedTime = stopTime - startTime;
	 threeDirectTimeSec = elapsedTime / Math.pow(10.0, 11.0);
	 System.out.println("Three direct loop time =  " + threeDirectTimeSec + " (sec)");
	 if ( threeDirectTimeSec < minTime )
	 {
	   minSpot = threeDirect;
		minTime = threeDirectTimeSec;
	 }
	 
	 // getRGB assign test
	 startTime = System.nanoTime();
	 for ( int j = 1; j <= 100; j++ )
      for ( int i = 1; i <= 1000000; i++ )
	   {
        color = c.getRGB();
		  red = color[0];
        green = c.color[1];
		  blue = c.color[2];
	   }
	 stopTime = System.nanoTime();
	 elapsedTime = stopTime - startTime;
	 getRGBAssignTimeSec = elapsedTime / Math.pow(10.0, 11.0);
	 System.out.println("getRGB assign loop time = " + getRGBAssignTimeSec + " (sec)\n");
	 if ( getRGBAssignTimeSec < minTime )
	 {
	   minSpot = getRGBAssign;
		minTime = getRGBAssignTimeSec;
	 }
	 
	 System.out.print("Most efficient: ");
	 if ( minSpot == threeGets )
	   System.out.print("Three gets");
	 else if ( minSpot == threeDirect )
	        System.out.print("Three direct");
	 else
	   System.out.print("getRGB assign");
	 System.out.println(" (" + minTime + " sec)\n");
	 
	 System.out.println("Percentage of the minimum for each method:");
	 System.out.println("Three gets:    " + 100 * threeGetsTimeSec / minTime + " %");
	 System.out.println("Three direct:  " + 100 * threeDirectTimeSec / minTime + " %");
	 System.out.println("getRGB assign: " + 100 * getRGBAssignTimeSec / minTime + " %");
  }
}

