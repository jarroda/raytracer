import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  The class BaseFrame is a kind of convenience class. It allows
  you to specify a title and size for the frame, sets the 
  close operation to exit on close, and centers the frame on
  the screen.
  
  @version 1 September 2006
  @author Bob Crawford
*/
public class BaseFrame extends JFrame 
{
  /**
   The only constructor takes the normally desired arguments.
	
	@param title The title for the frame.
	@param frameWidth The width of the frame.
	@param frameheight The height of the frame.
  */
  public BaseFrame(String title, int frameWidth, int frameHeight) 
  {
    super(title);
	 setSize(frameWidth, frameHeight);
    center();
	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  /**
    Centers the frame on the screen.
  */
  public void center() 
  {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getSize();
    int x = (screenSize.width - frameSize.width) / 2;
    int y = (screenSize.height - frameSize.height) / 2;
    setLocation(x, y);
  }

  /**
    Sets the size of the frame so that the client size
	 (the drawable region) is as specified.
	 
	 @param width The desired client width
	 @param height The desired client height
  */  
  public void setClientSize(int width, int height)
  {
    setVisible(true);
    Insets insets = getInsets();
    setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
  }
}