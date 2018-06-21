import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by tim on 17.03.16.
 */
public class ColorDrawer
    {
        public static void draw(Color[][] colormatrix, JFrame frame)
            {
                Container contentpane = frame.getContentPane();
                contentpane.getGraphics().clearRect(0,0,contentpane.getWidth(),contentpane.getHeight());
                Graphics2D graphics = (Graphics2D)contentpane.getGraphics();

                BufferedImage image = new BufferedImage(colormatrix.length, colormatrix[0].length,BufferedImage.TYPE_INT_RGB);

                for (int x = 0; x < colormatrix.length; x++)
                    {
                        for (int y = 0; y < colormatrix[0].length; y++)
                            {
                                if (colormatrix[x][y]!=null)
                                {
                                    image.setRGB(x, y, colormatrix[x][y].getRGB());
                                }
                                else
                                {
                                    image.setRGB(x, y, Color.WHITE.getRGB());
                                }
                            }
                    }

                    graphics.drawImage(image, null, 0, 0);
            }
    }
