import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Tim on 13.06.2016.
 */
public class ImageReader
    {
        public static void read(Pixelfeld pxl, File file)
            {
                try
                    {
                        BufferedImage image = ImageIO.read(file);

                        for (int i = 0;i<pxl.getFeld_klein().length;i++)
                            {
                                for (int j = 0;j<pxl.getFeld_klein()[0].length;j++)
                                    {
                                        pxl.pushMatrix(i,j,new Color(image.getRGB(i,j)));
                                    }
                            }
                    }
                catch (IOException e)
                    {
                        e.printStackTrace();
                    }


            }
        public static int getDimensions(File file)
            {
                try
                    {
                        BufferedImage image = ImageIO.read(file);
                        int width = image.getWidth();
                        int height = image.getHeight();

                        if (width==height)
                            {
                                return height;
                            }
                    }
                catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                return 0;
            }
    }
