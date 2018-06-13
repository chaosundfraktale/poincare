/**
 * Created by Tim on 31.10.2016.
 */
public class Matrix
    {
        public int a11, a12, a21, a22;

        public Matrix(int a11, int a12, int a21, int a22)
            {
                this.a11 = a11;
                this.a12 = a12;
                this.a21 = a21;
                this.a22 = a22;
            }

        public int determinante()
            {
                return a11*a22-a12*a21;
            }
    }
