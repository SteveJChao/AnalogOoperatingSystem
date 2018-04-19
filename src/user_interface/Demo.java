package user_interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Demo extends JFrame{
    class Panel extends JPanel {
        Image img;
        public Panel(String s) {
            img = Toolkit.getDefaultToolkit().createImage(s);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(img,20,20, this);
        }
    }

    public Demo() {
        Panel panel = new Panel("red.png");
        this.add(panel);
        this.setSize(200,200);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        new Demo();
    }
}
