import java.awt.*;
import javax.swing.*;

/**
  Introduces the object list. Two spheres.

  @version 1 June 2002
  @author Bob Crawford

*/
public class RayDemo3
{
  JFrame mainFrame = new JFrame("Ray Tracing Demo");

  RayDemo3()
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
    RayDemo3 demo = new RayDemo3();
  }
}

