import java.awt.*;
import javax.swing.*;

public class GraphicsDemo
{
  JFrame mainFrame = new JFrame("Graphics Demo");

  GraphicsDemo()
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
    GraphicsDemo demo = new GraphicsDemo();
  }
}

