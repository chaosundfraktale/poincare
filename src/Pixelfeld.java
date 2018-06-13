import java.awt.*;
import java.util.Objects;

/**
 * Created by Tim on 14.03.2016.
 */
public class Pixelfeld
    {
        private Color[][] feld_klein;
        private Color[][] feld_gross;

        public Pixelfeld(int length)
            {
                //Kleines Feld initialisieren
                feld_klein = new Color[length][length];
            }

        public void matrixAnwenden(Matrix matrix)
            {
                feld_gross = new Color[matrix.a11 * feld_klein.length + matrix.a12 * feld_klein[0].length][matrix.a21 * feld_klein.length + matrix.a22 * feld_klein[0].length];
                for (int x = 0; x < feld_klein.length; x++)
                    {
                        for (int y = 0; y < feld_klein[x].length; y++)
                            {
                                feld_gross[matrix.a11 * x + matrix.a12 * y][matrix.a21 * x + matrix.a22 * y] = feld_klein[x][y];
                            }
                    }
            }


        public void zusammenfassen()
            {
                feld_klein = new Color[feld_klein.length][feld_klein[0].length];
                for (int x = 0; x < feld_gross.length; x++)
                    {
                        for (int y = 0; y < feld_gross[x].length;y++)
                        {
                            if (Objects.nonNull(feld_gross[x][y]))
                                {
                                    feld_klein[x%feld_klein.length][y%feld_klein[0].length] = feld_gross[x][y];
                                }

                        }
                    }
            }



        public void viereckErstellen(int xvon, int yvon, int xbis, int ybis, Color clr)
            {
                for (int x = xvon; x < xbis; x++)
                    {
                        for (int y = yvon; y < ybis; y++)
                            {
                                feld_klein[x][y] = clr;
                            }
                    }
            }

        public void kreisErstellen(int mittelpunktX, int mittelpunktY, double radius, Color clr)
            {
                for (int x = 0; x < this.feld_klein.length; x++)
                    {
                        for (int y = 0; y < this.feld_klein[x].length; y++)
                            {

                                if (Math.sqrt(Math.pow(mittelpunktX - x, 2) + Math.pow(mittelpunktY - y, 2)) <= radius)
                                    {
                                        feld_klein[x][y] = clr;
                                    }

                            }
                    }
            }

        public void viereckRandErstellen(int xvon, int yvon, int xbis, int ybis, Color clr)
            {
                for (int x = xvon; x <= xbis; x++)
                    {
                        feld_klein[x][yvon] = clr;
                        feld_klein[x][ybis] = clr;
                    }
                for (int y = yvon; y <= ybis; y++)
                    {
                        feld_klein[xvon][y] = clr;
                        feld_klein[xbis][y] = clr;
                    }
            }

        public Color[][] getFeld_klein()
            {
                return feld_klein;
            }

        public Color[][] getFeld_gross()
            {
                return feld_gross;
            }

        public void pushMatrix(int x, int y, Color clr)
            {
                feld_klein[x][y] = clr;
            }
    }
