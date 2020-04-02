package GUI;

import Utils.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Canvas extends JPanel
{
    private static final int UPDATE_FREQUENCY = 30;
    private static final Color BACKGROUND_COLOR = Color.DARK_GRAY;

    Timer t;
    Controller c;

    public Canvas(int width , int height)
    {
        c = new Controller(width,height);

        t = new Timer(UPDATE_FREQUENCY, ae ->
        {
            c.computeNextFrame();
            repaint();
        });

        t.start();

        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                switch (e.getButton())
                {
                    // left click
                    case MouseEvent.BUTTON1:
                        c.addFoodAt(e.getX(),e.getY());
                        break;
                    // right click
                    case MouseEvent.BUTTON3:
                        c.addPoisonAt(e.getX(),e.getY());
                        break;
                    default:
                        break;
                }
            }
        });

        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {

                switch (e.getKeyCode())
                {
                    // space bar pressed
                    case KeyEvent.VK_SPACE:
                        c.addVehicle();
                        break;
                    // d-key pressed
                    case KeyEvent.VK_D:
                        c.setDebug(c.isDebug() ? false : true);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        c.show(g2d);
    }


}
