import java.awt.*;
import javax.swing.*;

/**
  Introduces lighting. Two spheres.

  @version 1 July 2002
  @author Bob Crawford

*/
public class RayDemo4
{
  JFrame mainFrame = new JFrame("Ray Tracing Demo");

  RayDemo4()
  {
    Toolkit kit = mainFrame.getToolkit();
    Dimension screenSize = kit.getScreenSize();
    mainFrame.setBounds(
      (screenSize.width - 648) / 2, (screenSize.height - 514) / 2,
      648, 514);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setContentPane(new DrawingPanel());
    mainFrame.setBackground(Color.green);
    mainFrame.setVisible(true);
  }

  public static void main(String[] arg)
  {
    RayDemo4 demo = new RayDemo4();
  }
}

