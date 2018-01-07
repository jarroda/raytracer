import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;

public class TestRGBArray
{
  public static void main(String[] arg)
  {
    RGBArray pic = new RGBArray(400, 400);
	 for ( int i = 0; i < 400; i++ )
	   pic.setRGB(i, i, 1.0, 0.0, 0.0);
	 for ( int i = 50; i < 100; i++ )
	   for ( int j = 50; j < 100; j++ )
		  pic.setRGB(i, j, 0.0, 0.0, 1.0);
	 BaseFrame bf = new BaseFrame("RGBArray Test", 500, 500);
	 BufferedImage bi = pic.getImage();
	 ImageIcon ii = new ImageIcon(bi);
	 JPanel jp = new JPanel();
	 jp.setBackground(Color.black);
	 jp.add(new JLabel(ii));
	 bf.add(jp);
	 bf.setVisible(true);
	 pic.writePNG("Test.png");
  }	 
}
