package demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

public class Opacity extends JFrame {
    public Opacity() {
        this.setLayout(new FlowLayout());
        this.add(new JButton("test"));
        this.add(new JCheckBox("test"));
        this.add(new JRadioButton("test"));
        this.add(new JProgressBar(0, 100));
        JPanel panel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400, 300);
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.red);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.add(new JLabel("Label"));
        add(panel);
        setSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Window w = new Opacity();
        w.setVisible(true);
        com.sun.awt.AWTUtilities.setWindowOpacity(w, 0.7f);
    }
}

