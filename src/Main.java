import GUI.Canvas;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame
{
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            try {
                Main frame = new Main();
                frame.setVisible(true);
                GUI.Canvas panel = new Canvas(frame.getWidth() - frame.getInsets().left - frame.getInsets().right, frame.getHeight() - frame.getInsets().top - frame.getInsets().bottom);
                frame.setContentPane(panel);
                panel.setFocusable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public Main() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        contentPane = new JPanel();
        setContentPane(contentPane);
        setResizable(false);
    }

}
