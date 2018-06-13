import javax.swing.*;
import java.awt.*;

/**
 * Created by Tim on 02.11.2016.
 */
public class pnlgrossFrame extends JFrame
    {
        private mainFrame mainFrame;

        public pnlgrossFrame(String title, mainFrame controller)
            {
                super(title);
                mainFrame = controller;

                FrameDragListener frameDragListener = new FrameDragListener(this);
                addMouseListener(frameDragListener);
                addMouseMotionListener(frameDragListener);

                setUndecorated(true);
            }

        @Override
        public void paint(Graphics g)
            {
                super.paint(g);
                mainFrame.pnl_gross_update();
            }
    }

