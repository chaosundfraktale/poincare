import javax.swing.*;
import java.awt.*;

/**
 * Created by tim on 17.03.16.
 */
public class ColorDrawer
    {
        public static void draw(Color[][] colormatrix, JFrame frame)
            {
                Container contentpane = frame.getContentPane();
                contentpane.getGraphics().clearRect(0,0,contentpane.getWidth(),contentpane.getHeight());
                Graphics graphics = contentpane.getGraphics();
                for (int x = 0; x < contentpane.getWidth(); x++)
                    {
                        for (int y = 0; y < contentpane.getHeight(); y++)
                            {
                                if (colormatrix[x][y] != null)
                                    {
                                        graphics.setColor(colormatrix[x][y]);
                                        graphics.drawRect(x, y, 1, 1);
                                    }
                            }
                    }
            }
    }
