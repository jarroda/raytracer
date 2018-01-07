import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;

public class TestRGBArrayRead
{
  public static void main(String[] arg)
  {
    RGBArray pic = new RGBArray(40, 40);
	 pic.readImage("Test.png");
	 BaseFrame bf = new BaseFrame("RGBArrayRead Test", 500, 500);
	 bf.setClientSize(pic.width, pic.height);
	 bf.center();
	 BufferedImage bi = pic.getImage();
	 ImageIcon ii = new ImageIcon(bi);
	 JPanel jp = new JPanel();
	 jp.setBackground(Color.black);
	 // jp.add(new JLabel(ii)); works just as well (or poorly)
	 JLabel jl = new JLabel(ii);
	 jl.setVerticalAlignment(SwingConstants.TOP);
	 jl.setBackground(Color.orange);
	 jp.add(jl);
	 bf.add(jp);
	 bf.setVisible(true);
  }	 
}
