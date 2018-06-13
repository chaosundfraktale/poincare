import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Tim on 30.10.2016.
 */
public class ImageWriter
    {
        public static void WriteImage(Color[][] colors, File out)
            {
                BufferedImage image = new BufferedImage(colors.length, colors[0].length,BufferedImage.TYPE_INT_RGB);

                for (int i = 0;i<colors.length;i++)
                    {
                        for (int j = 0;j<colors[i].length;j++)
                            {
                                if (colors[i][j]!=null)
                                    {
                                        image.setRGB(i, j, colors[i][j].getRGB());
                                    }
                                else
                                    {
                                        image.setRGB(i, j, Color.WHITE.getRGB());
                                    }
                            }
                    }
                try
                    {
                        ImageIO.write(image, "png", out);
                    }
                catch (IOException e)
                    {
                        e.printStackTrace();
                    }
            }
    }
