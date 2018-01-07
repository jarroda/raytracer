import javax.imageio.*;

public class FormatNames
{
  public static void main(String[] arg)
  {
    String[] names = ImageIO.getReaderFormatNames();
	 
	 for ( int i = 0; i < names.length; i++ )
	 { 
      System.out.println(names[i]);
	 }
  }
}